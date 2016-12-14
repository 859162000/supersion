package com.icfcc.batch.validator;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.BatchException;
import com.icfcc.batch.Constants;
import com.icfcc.batch.compress.CompressException;
import com.icfcc.batch.compress.CompressUtil;
import com.icfcc.batch.config.BatchConfig;
import com.icfcc.batch.core.BatchTask;
import com.icfcc.batch.core.Message;
import com.icfcc.batch.core.MessageDescriptor;
import com.icfcc.batch.core.Segment;
import com.icfcc.batch.core.SegmentDescriptor;
import com.icfcc.batch.core.field.FieldDescriptor;
import com.icfcc.batch.crypt.Cryption;
import com.icfcc.batch.crypt.CryptionFactory;
import com.icfcc.batch.process.MsgHandler;
import com.icfcc.batch.util.LogConfiger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import org.apache.log4j.Category;

public class OffMsgChecker extends Observable
  implements MsgHandler
{
  static Category _log = LogConfiger.getLoger();

  public void handlerMsg()
    throws BatchException
  {
    PrepareConfig config = PrepareConfig.getInstance();
    int cryptMode = config.getCryptMode();
    if (cryptMode == 1)
      perpareProcess();
    else if (cryptMode == 2)
      feedbackProcess();
  }

  private void perpareProcess()
    throws BatchException
  {
    BatchTask task = (BatchTask)BatchContext.getInstance().getProperites(Constants.CURRENT_TASK);

    PrepareConfig config = PrepareConfig.getInstance();

    if (config.isCheckFormat())
    {
      Message msg = (Message)BatchContext.getInstance().getProperites("com.icfcc.batch.core.Message");

      DecimalFormat format = new DecimalFormat("0000000000");

      String version = msg.getVersion();

      int msgtype = BatchConfig.getInstance().getTasktype();

      String key = String.valueOf(msgtype) + "." + version;

      MessageDescriptor descriptor = MessageDescriptor.getInstance(key);

      if (descriptor == null) {
        throw new BatchException(11112, "无法获取当前处理的报文对象的报文描述对象");
      }

      MsgValidator msgValidator = descriptor.getMsgValidator();
      if (msgValidator == null) {
        throw new BatchException(11113, "无法获取当前处理的报文对象的报文检查对象");
      }

      RecordValidator recordValidator = msgValidator.getRecordValidator();
      if (recordValidator == null) {
        throw new BatchException(11114, "无法获取当前处理的报文对象的记录检查对象");
      }

      CheckResult doneresult = (CheckResult)BatchContext.getInstance().getProperites("message_process_result");

      _log.info("开始报文结束处理");
      try
      {
        File file;
        String oldPath = RecordChecker.getOutPath();
        String fileName = RecordChecker.getDoingFileName();

        FileOutputStream badout = null;

        Segment headerSegment = msg.getHeadSegment();

        if (headerSegment == null) {
          throw new BatchException(11112, "无法获取当前报文对象的报文头");
        }

        String msgHeaderResult = (String)BatchContext.getInstance().getProperites("header_checker_result");

        if (msgHeaderResult == null) {
          msgHeaderResult = "";
        }

        int headerRecNumber = -1;
        try {
          headerRecNumber = Integer.parseInt(headerSegment.getFieldValue("TotalRecords").trim());
        }
        catch (NumberFormatException ex1) {
        }
        if (recordValidator.getDealRecordCount() != headerRecNumber) {
          msgHeaderResult = msgHeaderResult + "10";
        }

        int errorRecordNumber = recordValidator.getDealRecordCount() - recordValidator.getOkRecordCount();

        if ((errorRecordNumber > 0) || (msgHeaderResult.length() > 0))
        {
          badout = new FileOutputStream(oldPath + fileName + ".bad");
        }

        String okFileName = oldPath + fileName + "." + msg.getMsgID();
        byte[] buffer = new byte[2048];

        File okoutFile = new File(okFileName);

        if (okoutFile.exists()) {
          okoutFile.delete();
        }

        byte[] header = msg.getHeadSegment().getOrgByte();

        FileOutputStream okout = null;

        if (config.isOutputPassFile())
        {
          okout = new FileOutputStream(okoutFile);
          okout.write(header);
          okout.write("\r\n\r\n".getBytes());
        }

        doneresult.setBadRecords(recordValidator.getDealRecordCount() - recordValidator.getOkRecordCount());

        doneresult.setOkRecords(recordValidator.getOkRecordCount());
        doneresult.setTotalRecords(recordValidator.getDealRecordCount());

        _log.info(" dealed records:" + recordValidator.getDealRecordCount() + " ok records:" + recordValidator.getOkRecordCount());

        File f1 = new File(oldPath);
        int readsize = -1;

        if (f1.isDirectory())
        {
          String[] s = f1.list();

          for (int i = 0; i < s.length; ++i)
          {
            FileInputStream in;
            if (s[i].indexOf("log." + msg.getMsgID()) != -1)
            {
              if (badout != null)
              {
                in = new FileInputStream(oldPath + s[i]);

                while ((readsize = in.read(buffer)) >= 0)
                  badout.write(buffer, 0, readsize);

                in.close();
              }

              new File(oldPath + s[i]).delete();
            }

            if (!(config.isOutputPassFile())) {
              break;
            }

            if (s[i].indexOf("txt." + msg.getMsgID()) != -1)
            {
              in = new FileInputStream(oldPath + s[i]);

              while ((readsize = in.read(buffer)) >= 0) {
                okout.write(buffer, 0, readsize);
              }

              in.close();

              new File(oldPath + s[i]).delete();
            }

          }

          okout.close();
          if (badout != null) {
            badout.close();
          }

        }

        if (config.isOutputPassFile())
        {
          updateSegmentData(msg.getHeadSegment(), "TotalRecords", format.format(recordValidator.getOkRecordCount()), okFileName);
        }

        if (!(config.isCryptFile())) {
          return;
        }

        String outputEncFile = oldPath + fileName + ".enc";
        String sourceFile = oldPath + fileName + ".txt";
        if ((config.isOutputPassFile()) && (recordValidator.getOkRecordCount() > 0))
        {
          compressFile(okFileName, outputEncFile, task);
          file = new File(okFileName);
          file.delete();
        }

        if ((config.isOutputPassFile()) && (recordValidator.getOkRecordCount() == 0))
        {
          file = new File(okFileName);
          file.delete();
        }

        if ((!(config.isOutputPassFile())) && (recordValidator.getDealRecordCount() == recordValidator.getOkRecordCount()))
        {
          compressFile(sourceFile, outputEncFile, task);
        }

        generateSummaryFile(doneresult, oldPath + fileName);
      }
      catch (Exception ex) {
        _log.error("报文预处理后整理出现错误", ex);
      }
    }
    else
    {
      String oldPath = RecordChecker.getOutPath();
      String filePath = LogConfiger.formatFilePath(task.getMsgPath());
      String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
      String outputEncFile = oldPath + fileName + ".enc";
      String sourceFile = filePath;

      compressFile(sourceFile, outputEncFile, task);
    }
  }

  private void feedbackProcess()
    throws BatchException
  {
    BatchTask task = (BatchTask)BatchContext.getInstance().getProperites(Constants.CURRENT_TASK);

    PrepareConfig config = PrepareConfig.getInstance();

    String oldPath = RecordChecker.getOutPath();
    String filePath = LogConfiger.formatFilePath(task.getMsgPath());
    String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
    String outputEncFile = oldPath + fileName + ".txt";
    String sourceFile = filePath;
    deCompressFile(sourceFile, outputEncFile, task);
  }

  public void deCompressFile(String infileName, String outputFileName, BatchTask task)
  {
    File file;
    PrepareConfig config = PrepareConfig.getInstance();
    int keyMode = config.getKeyMode();
    try {
      Cryption cryptor = CryptionFactory.createCryption();
      if (keyMode == 1)
        cryptor.decryptMsg(infileName, infileName + ".tmp", 1);
      else
        cryptor.decryptMsg(infileName, infileName + ".tmp", 1);
        //cryptor.decryptMsg(infileName, infileName + ".tmp", 2);
    }
    catch (Exception ex1)
    {
      _log.error("解密出现错误", ex1);
      file = new File(outputFileName);
      if (file.exists())
        file.delete();

      processException(task);
      return;
    }

    try
    {
      CompressUtil.deCompress(infileName + ".tmp", outputFileName);
    } catch (CompressException ex) {
      _log.error("解压缩出现错误", ex);
      file = new File(infileName + ".tmp");
      file.delete();
      processException(task);
    } finally {
      file = new File(infileName + ".tmp");
      file.delete();
    }
  }

  public void compressFile(String infileName, String outputFileName, BatchTask task)
  {
    File file;
    try
    {
      CompressUtil.compress(infileName, infileName + ".tmp");
    } catch (CompressException ex) {
      _log.error("压缩出现错误", ex);
      file = new File(infileName + ".tmp");
      file.delete();
      //processException(task);
      return;
    }
    try
    {
      Cryption cryptor = CryptionFactory.createCryption();
      cryptor.encryptMsg(infileName + ".tmp", outputFileName, 1);
    }
    catch (Exception ex1) {
      _log.error("加密出现错误", ex1);
      processException(task);
      file = new File(outputFileName);
      if (file.exists())
        file.delete();
    }
    finally {
     file = new File(infileName + ".tmp");
      file.delete();
    }
  }

  public void addObserver(Observer observer)
  {
    super.addObserver(observer);
  }

  private void processException(BatchTask task)
  {
    setChanged();
    //notifyObservers("current_task_abort" + task.getMsgPath());
  }

  public static void generateSummaryFile(CheckResult result, String orginalPath)
  {
    if ((orginalPath == null) || (result == null)) {
      return;
    }

    File file = new File(orginalPath + ".log");

    if (file.exists()) {
      file.delete();
    }

    try
    {
      FileOutputStream okout = new FileOutputStream(file);

      Map checkResult = result.getCheckerResult();

      for (Iterator it = checkResult.keySet().iterator(); it.hasNext(); ) {
        Object checkkey = it.next();
        CheckStatistic check = (CheckStatistic)checkResult.get(checkkey);

        if (check.getStatisCode().trim().length() == 7) {

        }

      }

      okout.close();
    }
    catch (Exception e) {
      _log.error("产生" + orginalPath + "的汇总文件出现错误");
    }
  }

  private void updateSegmentData(Segment segment, String updateField, String updateValue, String fileName)
    throws FileNotFoundException, IOException
  {
    RandomAccessFile file = new RandomAccessFile(fileName, "rw");

    FieldDescriptor fieldDesc = segment.getSegmentDesc().getField(updateField);

    int startPosition = fieldDesc.getSegmentStartPotison();

    file.skipBytes(startPosition);

    file.write(updateValue.getBytes());

    file.close();
  }
  
  public List handlerMsg(String msgFile, String badFile, String passFile,int returnWay){
	  return null;
  }
}
