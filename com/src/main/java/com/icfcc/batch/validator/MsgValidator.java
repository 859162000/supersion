package com.icfcc.batch.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Category;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.Constants;
import com.icfcc.batch.config.BatchConfig;
import com.icfcc.batch.core.MessageDescriptor;
import com.icfcc.batch.core.Record;
import com.icfcc.batch.core.Segment;
import com.icfcc.batch.core.field.FieldDescriptor;
import com.icfcc.batch.process.parser.MsgParser;
import com.icfcc.batch.process.parser.MsgParserFactory;
import com.icfcc.batch.process.parser.ParsingException;
import com.icfcc.batch.process.reader.MsgReader;
import com.icfcc.batch.process.reader.MsgReaderFactory;
import com.icfcc.batch.process.reader.ReadingException;
import com.icfcc.batch.util.BadRecordWriter;
import com.icfcc.batch.util.LogConfiger;

public class MsgValidator {
	private static Category _log = LogConfiger
			.getLoger(Constants.SYSTEM_LOG_NAME);

	private String msgVersion;

	private MsgReader _reader;

	private MsgParser _parser;

	private RecordValidator recordValidator;

	private int dealedRecord = 0;

	private int msgFileRecord = 0;

	int passRecord = 0;

	private static Map _instance = new HashMap();

	boolean hasDone = true;

	Map globalVariables = new HashMap();

	FileOutputStream fos = null;

	private static NumberFormat recordsFormat = new DecimalFormat("0000000000");

	private boolean boutputFile = false;

	public String[][] validate(String msgFile, String badFile, String passFile,int returnWay) {
		String[][] dates=null;
		String[][] newDates=null;
		if (this.recordValidator == null) {
			_log.error("数据检查对象为空，无法检查");
			return null;
		}

		this.hasDone = false;

		_log.info("开始检查文件:" + msgFile);

		this.recordValidator.reset();

		this.dealedRecord = 0;

		this.passRecord = 0;
		try {
			checkInit(msgFile, passFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			_log.error("初始化预处理文件失败", ex);

			cleanup(passFile);

			return null;
		}

		BadRecordWriter badRecordWriter = new BadRecordWriter();
		try {
			byte[] headByte = this._reader.getHeaderBytes();

			Segment segment = this._parser.parseHeader(headByte);
			try {
				this.msgFileRecord = Integer.parseInt(segment
						.getFieldValue("TotalRecords"));
			} catch (NumberFormatException ex2) {
				_log.error("报文头部记录数解析错误", ex2);
				cleanup(passFile);

				return null;
			}

			Map sharedDataMap = new HashMap();
			File newFile = new File(msgFile);
			sharedDataMap.put("fileName", newFile.getName());
			sharedDataMap.put("dataYearMonth", newFile.getName().substring(14,
					20));

			SegmentValidator segmentChecker = (SegmentValidator) this.recordValidator
					.getSegmentCheckMap().get(
							segment.getSegmentDesc().getSegmentType());

			List sharedData = segmentChecker.getSharedDataList();

			for (Iterator it = sharedData.iterator(); it.hasNext();) {
				SharedData data = (SharedData) it.next();
				String dataSource = data.getSourceData();
				StringTokenizer token = new StringTokenizer(dataSource, "{}");
				while (token.hasMoreTokens()) {
					String splitString = token.nextToken();
					if (splitString.startsWith("$"))
						splitString = segment.getFieldValue(splitString
								.substring(1));

					sharedDataMap.put(data.getName(), splitString);
				}
			}
			System.out.println("报文记录数:" + this.msgFileRecord);
			_log.info("报文记录数:" + this.msgFileRecord);

			dates=new String[this.msgFileRecord][8];
			this.recordValidator.setSharedData(sharedDataMap);

			if (this.boutputFile) {
				this.fos.write(headByte);
				this.fos.write("\r\n\r\n".getBytes());
			}
			int index=0;
			top: while (true) {
				
				byte[] msgReaded = null;
				Record rec;
				while (true) {
					if (!(this._reader.hasRecord()))
						break top;
					msgReaded = null;
					try {
						msgReaded = this._reader.getNextRecord(false);
					} catch (Exception ex1) {
						_log.error("报文读取错误，，报文无法进行检查", ex1);
						cleanup(passFile);
						return null;
					}

					if ((msgReaded == null) && (!(this._reader.hasRecord()))) {
						_log.info("所有的记录已经被处理完毕，没有获取相关数据");
						break top;
					}

					this.dealedRecord += 1;
					rec = null;

					if ((msgReaded != null) && (msgReaded.length != 0))
						break;
					_log.error("本次没有获取数据,本次处理失败，系统忽略本次错误");
				}

				try {
					rec = this._parser.parseRec(msgReaded);
				} catch (ParsingException ex) {
					_log.error("记录[" + this.dealedRecord + "]解析失败!", ex);
					rec = null;

				}

				String result = null;
				try {
					result = this.recordValidator.validate(rec, returnWay);
					if(result!=null&&!"".equals(result)){
						byte[] recOri = rec.getInitialBytes();
						String mainDate=new String(recOri);
						dates[index][0]=result;
						dates[index][1]=mainDate;
						dates[index][2]=mainDate.substring(5, 19);   //金融机构代码
						dates[index][3]=mainDate.substring(22, 62);  //业务号
						dates[index][4]=mainDate.substring(126, 134);//最近一次还款日期
						dates[index][5]=mainDate.substring(266, 296);//姓名
						dates[index][6]=mainDate.substring(296, 297);//证件类型
						dates[index][7]=mainDate.substring(297, 315);//证件号码
						index++;
					}
					
				} catch (Exception ex3) {
					ex3.printStackTrace();
					_log.error("检查记录[" + this.dealedRecord + "]失败", ex3);
					result = "发生异常";
				}

				if (result.length() > 0) {
					_log.info("记录[" + this.dealedRecord + "]处理失败!" + result);
					if (rec == null)
						rec = new Record(msgReaded);

					badRecordWriter.write(rec, result, badFile);
				} else {
					this.passRecord += 1;

					if (this.boutputFile) {
						this.fos.write(msgReaded);
						this.fos.write("\r\n".getBytes());
					}

				}
			}
			newDates=new String[index][8];
			for(int i=0;i<index;i++){
				newDates[i][0]=dates[i][0];
				newDates[i][1]=dates[i][1];
				newDates[i][2]=dates[i][2];
				newDates[i][3]=dates[i][3];
				newDates[i][4]=dates[i][4];
				newDates[i][5]=dates[i][5];
				newDates[i][6]=dates[i][6];
				newDates[i][7]=dates[i][7];
			}

			String headerResult = segmentChecker.validate(segment, returnWay);

			if (headerResult.length() > 0) {
				_log.info("记录头检查失败,无法创建上报文件:" + headerResult);
				cleanup(passFile);
				this.hasDone = true;

				return null;
			}

			cleanup(passFile);

			if ((this.boutputFile) && (this.passRecord > 0)) {
				String updateValue = recordsFormat.format(this.passRecord);
				updateSegmentData(segment, "TotalRecords", updateValue,
						passFile);
			}

		} catch (Exception e) {
			_log.error("文件检查失败", e);
			return null;
		}
		_log.info("检查文件结束:" + msgFile);

		if ((this.boutputFile) && (this.passRecord > 0)) {

		}

		//return ((this.dealedRecord == this.passRecord) ? "": "文件预处理不通过,请查看错误日志文件");
		return newDates;
	}

	private void cleanup(String passFile) {
		if (this.boutputFile) {
			try {
				this.fos.close();

				if (this.passRecord == 0) {
					File okFile = new File(passFile);
					okFile.delete();
				}
			} catch (Exception e) {
			}

		}

		this.hasDone = true;
	}

	private void checkInit(String msgFile, String passFile)
			throws ReadingException, ValidatorException, Exception {
		BatchContext context = BatchContext.getInstance();
		context.addPropetiry("currentMsg", MessageDescriptor
				.getInstance(this.msgVersion));

		String outPutPassFile = null;

		if ((outPutPassFile != null) && (outPutPassFile.equals("false"))) {
			this.boutputFile = false;
		} else {
			this.boutputFile = true;
		}
		int msgtype = BatchConfig.getInstance().getTasktype();

		this._reader = MsgReaderFactory.getInstance(msgtype, this.msgVersion);
		if (null == this._reader) {
			_log.error("无法初始化Reader对象，本次任务处理结束");
			throw new ValidatorException(1207, "无法初始化Reader对象");
		}

		this._reader.setMsgPath(msgFile);

		this._parser = MsgParserFactory.getInstance(msgtype, this.msgVersion);
		if (null == this._parser) {
			_log.error("无法初始化parser对象，本次任务处理结束");
			throw new ValidatorException(1208, "无法初始化parser对象");
		}

		if (this.boutputFile)
			try {
				if (this.fos != null) {
					this.fos.close();
				}

				File okFile = new File(passFile);
				if (okFile.exists()) {
					boolean test = okFile.delete();
					if (!(test))
						_log.error("无法删除文件");

				}

				okFile.createNewFile();
				this.fos = new FileOutputStream(okFile, true);
			} catch (FileNotFoundException noFileEx) {
				throw new ValidatorException(1209, "无法创建目标文件");
			} catch (IOException ex) {
				throw new ValidatorException(1210, "文件IO发生异常");
			}
	}

	public static MsgValidator getInstance(String strVersion) {
		Object validator = _instance.get(strVersion);
		if (validator == null) {
			validator = new MsgValidator();
			_instance.put(strVersion, validator);
		}
		return ((MsgValidator) validator);
	}

	public RecordValidator getRecordValidator() {
		return this.recordValidator;
	}

	public void setRecordValidator(RecordValidator recordValidator) {
		this.recordValidator = recordValidator;
	}

	public String getMsgVersion() {
		return this.msgVersion;
	}

	public void setMsgVersion(String msgVersion) {
		this.msgVersion = msgVersion;
	}

	public int getCheckDoneRecordsNumber() {
		return this.dealedRecord;
	}

	public int getFileRecordNumber() {
		return this.msgFileRecord;
	}

	public boolean isHasDone() {
		return this.hasDone;
	}

	private void updateSegmentData(Segment segment, String updateField,
			String updateValue, String fileName) throws FileNotFoundException,
			IOException {
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");

		FieldDescriptor fieldDesc = segment.getSegmentDesc().getField(
				updateField);
		int startPosition = fieldDesc.getSegmentStartPotison();

		file.skipBytes(startPosition);

		file.write(updateValue.getBytes());

		file.close();
	}
}
