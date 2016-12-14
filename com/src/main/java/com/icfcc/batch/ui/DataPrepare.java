package com.icfcc.batch.ui;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.Constants;
import com.icfcc.batch.core.BatchTask;
import com.icfcc.batch.core.TaskRequest;
import com.icfcc.batch.process.TaskHandler;
import com.icfcc.batch.util.LogConfiger;
import com.icfcc.batch.validator.CheckResult;
import com.icfcc.batch.validator.CheckStatistic;
import com.icfcc.batch.validator.OffMsgChecker;
import com.icfcc.batch.validator.PrepareConfig;
import com.icfcc.batch.validator.PrepareFileStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;

public class DataPrepare extends JFrame
  implements Observer
{
  JScrollPane jScrollPane1 = new JScrollPane();
  String[] columns = { "文件名", "文件大小", "处理状态", "处理结果" };
  String[] words = { "file1", "file2", "file3", "file4", "file5", "file6", "file7" };
  JPanel jPanel1 = new JPanel();
  static int cryptMode = 1;
  FileDataModel model;
  JTable jTable1;
  JButton startCmd = new JButton();
  JButton openFileCmd = new JButton();
  JPanel jPanel2 = new JPanel();
  DealProgressBar progressBar = new DealProgressBar();
  JLabel jLabel1 = new JLabel();
  JButton stopCmd = new JButton();
  JButton openDirCmd = new JButton();
  JButton setDestDir = new JButton();
  int totalAcceptFiles = 0;
  int totalFiles = 0;
  boolean bforceStop = false;
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel lblOutputDirectory = new JLabel();
  JLabel lblOutputLabelTip = new JLabel();
  boolean bdoneFiles = false;
  static PrepareConfig config = PrepareConfig.getInstance();

  public DataPrepare()
  {
    try
    {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception
  {
    getContentPane().setLayout(this.borderLayout1);
    this.startCmd.setFont(new Font("Dialog", 0, 14));
    this.startCmd.setAlignmentX(0F);
    this.startCmd.setToolTipText("");
    this.startCmd.setActionCommand("startProcess");
    this.startCmd.setBorderPainted(true);
    this.startCmd.setEnabled(false);

    this.startCmd.setText("开始处理");
    this.startCmd.addActionListener(new DataPrepare_startCmd_actionAdapter(this));

    this.openFileCmd.setFont(new Font("Dialog", 0, 14));
    this.openFileCmd.setToolTipText("");
    this.openFileCmd.setActionCommand("openFile");
    this.openFileCmd.setSelectedIcon(null);
    this.openFileCmd.setText("选择文件");
    this.openFileCmd.addActionListener(new DataPrepare_openFileCmd_actionAdapter(this));

    this.jLabel1.setRequestFocusEnabled(true);
    this.jLabel1.setFont(new Font("Dialog", 0, 14));
    this.jLabel1.setText("当前文件处理进度：");
    this.stopCmd.setFont(new Font("Dialog", 0, 14));
    this.stopCmd.setToolTipText("");
    this.stopCmd.setActionCommand("stopProcess");
    this.stopCmd.setText("停止处理");
    this.stopCmd.setEnabled(false);
    this.stopCmd.addActionListener(new DataPrepare_stopCmd_actionAdapter(this));
    this.openDirCmd.setFont(new Font("Dialog", 0, 14));
    this.openDirCmd.setToolTipText("");
    this.openDirCmd.setAction(null);
    this.openDirCmd.setActionCommand("openDir");
    this.openDirCmd.setText("选择目录");
    this.openDirCmd.addActionListener(new DataPrepare_openDirCmd_actionAdapter(this));

    this.setDestDir.setFont(new Font("Dialog", 0, 14));
    this.setDestDir.setActionCommand("setDest");
    this.setDestDir.setText("输出目录");
    this.setDestDir.addActionListener(new DataPrepare_setDestDir_actionAdapter(this));

    this.setDestDir.setEnabled(false);
    this.progressBar.setPreferredSize(new Dimension(200, 16));
    this.progressBar.setToolTipText("");
    this.lblOutputDirectory.setText("原路径");
    this.lblOutputDirectory.setForeground(new Color(255, 0, 0));

    this.lblOutputDirectory.setFont(new Font("Dialog", 0, 14));
    this.lblOutputLabelTip.setRequestFocusEnabled(true);
    this.lblOutputLabelTip.setText("输出路径:");
    this.lblOutputLabelTip.setFont(new Font("Dialog", 0, 14));

    getContentPane().add(this.jScrollPane1, "Center");
    this.jScrollPane1.getViewport().add(this.jTable1, null);
    getContentPane().add(this.jPanel1, "South");
    this.jPanel1.add(this.openDirCmd, null);
    this.jPanel1.add(this.openFileCmd, null);
    this.jPanel1.add(this.setDestDir, null);
    this.jPanel1.add(this.startCmd, null);
    getContentPane().add(this.jPanel2, "North");
    this.progressBar.addChangeListener(new process_data_progress_event(this));
    this.jPanel2.add(this.lblOutputLabelTip, null);
    this.jPanel2.add(this.lblOutputDirectory, null);
    this.jPanel2.add(this.jLabel1, null);
    this.jPanel2.add(this.progressBar, null);
    this.jPanel1.add(this.stopCmd, null);

    setDefaultCloseOperation(3);

    this.progressBar.setMinimum(0);
    this.progressBar.setMaximum(100);
    this.progressBar.setStringPainted(true);
    BatchContext.getInstance().addPropetiry("global_ui_object", this);

    init();
  }

  void init()
  {
    this.model = new FileDataModel();
    this.jTable1 = new JTable(this.model);
    this.jScrollPane1.getViewport().add(this.jTable1, null);
    this.jTable1.addMouseListener(new JTable_MouseClicked_Adapter(this));
  }

  public void setFrameToCenter()
  {
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dimension = getSize();

    setBounds((size.width - dimension.width) / 2, (size.height - dimension.height) / 2, dimension.width, dimension.height);
  }

  public static void main(String[] args)
  {
    DataPrepare frame = new DataPrepare();
    frame.setSize(640, 480);
    frame.setFrameToCenter();

    cryptMode = config.getCryptMode();

    boolean isCheckFileName = config.isCheckFileName();
    boolean isCheckFormat = config.isCheckFormat();
    boolean isCryptFile = config.isCryptFile();
    int keyMode = config.getKeyMode();
    if (cryptMode == 2)
      if (keyMode == 1)
        frame.setTitle("商业银行解密解压");
      else
        frame.setTitle("征信服务中心解密解压");

    else if ((cryptMode == 1) && (isCheckFileName) && (isCheckFormat) && (isCryptFile))
    {
      frame.setTitle("报文预处理子系统");
    }
    else { frame.setTitle("报文预处理子系统(压缩加密不校验)");
    }

    frame.show();
  }

  void openFileCmd_actionPerformed(ActionEvent e)
  {
    this.totalFiles = 0;
    this.totalAcceptFiles = 0;

    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setMultiSelectionEnabled(true);
    jFileChooser.addChoosableFileFilter(new CheckFileFilter());
    int state = jFileChooser.showOpenDialog(this);

    File[] file = jFileChooser.getSelectedFiles();

    if ((file != null) && (state == 0))
    {
      this.model.removeAllRow();
      TaskRequest.getInstance().removeAllTask();
      CheckFileFilter filter = new CheckFileFilter();

      for (int i = 0; i < file.length; ++i)
      {
        File selectedFile = file[i];

        String prefix = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

        if (cryptMode == 1) {
          if (prefix != null) if (!(prefix.equalsIgnoreCase("txt")));

        }
        else if (cryptMode == 2) {
          if (prefix != null) if (!(prefix.equalsIgnoreCase("enc")));

        }
        else
        {
          this.totalFiles += 1;

          if (filter.accept(selectedFile)) {
            this.totalAcceptFiles += 1;
            addFiletoTable(selectedFile, PrepareFileStatus.WAIT);
          } else {
            addFiletoTable(selectedFile, PrepareFileStatus.INVALID_NAME);
          }

          String fileName = LogConfiger.formatFilePath(file[0].getAbsolutePath());

          this.lblOutputDirectory.setText("原路径");
          BatchContext.getInstance().addPropetiry("prepare_output_dir", fileName.substring(0, fileName.lastIndexOf("/")));
        }

      }

      CheckResult summary = new CheckResult();
      summary.resetStatus();
      BatchContext.getInstance().addPropetiry("message_process_result_summary", summary);

      showMessage(this.totalFiles, this.totalAcceptFiles);

      this.startCmd.setEnabled(false);
      this.setDestDir.setEnabled(false);
    }

    this.jTable1.resize(this.jTable1.getPreferredScrollableViewportSize());
  }

  void addFiletoTable(File file, PrepareFileStatus status)
  {
    if ((cryptMode == 1) && (config.isCheckFormat())) {
      this.model.addDataRow(new String[] { file.getAbsolutePath(), "" + file.length(), status.getDesc(), "", "", "" });
    }
    else
      this.model.addDataRow(new String[] { file.getAbsolutePath(), "" + file.length(), status.getDesc() });
  }

  void progress_changed_performed(ChangeEvent e)
  {
  }

  void startCmd_actionPerformed(ActionEvent e)
  {
    if (JOptionPane.showOptionDialog(this, "您确定现在开始处理吗？", "询问", 2, 3, null, null, null) == 0)
    {
      setButtonStatus(false);
      this.setDestDir.setEnabled(false);
      this.bdoneFiles = false;
      this.bforceStop = false;
      addTaskFromTable();
      TaskHandler.getInstance().startHandler(this);
    }
  }

  void addTaskFromTable()
  {
    List tasks = this.model.getAllTaskData();
    TaskRequest.getInstance().removeAllTask();
    for (Iterator it = tasks.iterator(); it.hasNext(); )
      TaskRequest.getInstance().addTask(it.next().toString());
  }

  void stopCmd_actionPerformed(ActionEvent e)
  {
    if (JOptionPane.showOptionDialog(this, "您确定中断处理吗？", "询问", 2, 3, null, null, null) == 0)
    {
      TaskHandler.getInstance().forceStop();
      setButtonStatus(true);
      this.startCmd.setEnabled(true);
      this.bforceStop = true;
    }
  }

  void setButtonStatus(boolean status) {
    this.openFileCmd.setEnabled(status);
    this.startCmd.setEnabled(status);
    this.openDirCmd.setEnabled(status);
    this.stopCmd.setEnabled(false);
  }

  public void update(Observable ob, Object arg)
  {
    String fileName;
    this.progressBar.setValue(0);

    String dealingStatus = (String)arg;
    if (dealingStatus.equals("all_task_have_done"))
    {
      TaskHandler.getInstance().forceStop();

      if (this.bdoneFiles) {
        outputSummaryFile();
      }

      setButtonStatus(true);
      if (!(this.bforceStop))
        this.startCmd.setEnabled(false);

      return;
    }

    if (dealingStatus.startsWith("current_task_done"))
    {
      fileName = LogConfiger.formatFilePath(dealingStatus.substring("current_task_done".length()));

      int row = this.model.getFileInfoRowNumber(fileName);

      String curStatus = (String)this.model.getValueAt(row, 2);

      if (curStatus.trim().indexOf(PrepareFileStatus.DOING.getDesc()) == -1) {
        return;
      }

      this.model.setValueAt(PrepareFileStatus.DONE.getDesc(), row, 2);

      CheckResult doneresult = (CheckResult)BatchContext.getInstance().getProperites("message_process_result");

      if (doneresult != null)
      {
        this.model.setValueAt(doneresult.getTotalRecords() + "", row, 3);
        this.model.setValueAt(doneresult.getOkRecords() + "", row, 4);
        this.model.setValueAt(doneresult.getBadRecords() + "", row, 5);
      }

      this.jTable1.repaint();

      updateProcessSummary(doneresult);
      this.bdoneFiles = true;

      return;
    }

    if (dealingStatus.startsWith("current_task_wait")) {
      fileName = LogConfiger.formatFilePath(dealingStatus.substring("current_task_wait".length()));

      setTableStatusValue(fileName, PrepareFileStatus.WAIT.getDesc());
      return;
    }

    if (dealingStatus.startsWith("current_task_doing")) {
      fileName = LogConfiger.formatFilePath(dealingStatus.substring("current_task_doing".length()));

      setTableStatusValue(fileName, PrepareFileStatus.DOING.getDesc());
      return;
    }

    if (dealingStatus.startsWith("current_task_abort")) {
      fileName = LogConfiger.formatFilePath(dealingStatus.substring("current_task_abort".length()));

      setTableStatusValue(fileName, PrepareFileStatus.ABORT.getDesc());
      return;
    }

    BatchTask task = (BatchTask)BatchContext.getInstance().getProperites(Constants.CURRENT_TASK);

    if (task != null)
      setTableStatusValue(task.getMsgPath(), (String)arg);
  }

  private void setTableStatusValue(String fileName, String status)
  {
    String newfileName = LogConfiger.formatFilePath(fileName);
    int row = this.model.getFileInfoRowNumber(newfileName);
    this.model.setValueAt(status, row, 2);
    this.jTable1.repaint();
  }

  void outputSummaryFile() {
    PrepareConfig config = PrepareConfig.getInstance();

    if (config.isCheckFormat())
    {
      CheckResult summary = (CheckResult)BatchContext.getInstance().getProperites("message_process_result_summary");

      String outputDir = (String)BatchContext.getInstance().getProperites("prepare_output_dir");

      if (outputDir.startsWith("no_user_defined_dir")) {
        outputDir = outputDir.substring("no_user_defined_dir".length());
      }

      outputDir = LogConfiger.formatFilePath(outputDir) + "/";
      OffMsgChecker.generateSummaryFile(summary, outputDir + "all");
    }
  }

  void openDirCmd_actionPerformed(ActionEvent e)
  {
    this.totalAcceptFiles = 0;
    this.totalFiles = 0;
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setFileSelectionMode(1);
    jFileChooser.addChoosableFileFilter(new CheckFileFilter());
    int state = jFileChooser.showOpenDialog(this);
    File file = jFileChooser.getSelectedFile();

    if ((file != null) && (state == 0))
    {
      this.model.removeAllRow();
      addFileInPath(file);
      this.jTable1.revalidate();
      this.jTable1.repaint();
      this.lblOutputDirectory.setText("原路径");

      CheckResult summary = new CheckResult();
      summary.resetStatus();
      BatchContext.getInstance().addPropetiry("message_process_result_summary", summary);

      showMessage(this.totalFiles, this.totalAcceptFiles);

      this.startCmd.setEnabled(false);
      this.setDestDir.setEnabled(false);
      BatchContext.getInstance().addPropetiry("prepare_output_dir", "no_user_defined_dir" + file.getAbsolutePath());
    }
  }

  void resetProcessSummary()
  {
    CheckResult processSummary = new CheckResult();
    BatchContext.getInstance().addPropetiry("message_process_result_summary", processSummary);
  }

  void updateProcessSummary(CheckResult processResult)
  {
    PrepareConfig config = PrepareConfig.getInstance();
    if ((processResult == null) || (!(config.isCheckFormat()))) {
      return;
    }

    CheckResult summary = (CheckResult)BatchContext.getInstance().getProperites("message_process_result_summary");

    summary.setBadRecords(summary.getBadRecords() + processResult.getBadRecords());

    summary.setOkRecords(summary.getOkRecords() + processResult.getOkRecords());

    summary.setTotalRecords(summary.getTotalRecords() + processResult.getTotalRecords());

    Map result = processResult.getCheckerResult();
    Map summaryResult = summary.getCheckerResult();

    for (Iterator it = result.keySet().iterator(); it.hasNext(); )
    {
      Object key = it.next();
      CheckStatistic statis = (CheckStatistic)result.get(key);

      CheckStatistic summaryStatistic = (CheckStatistic)summaryResult.get(key);

      if (summaryStatistic == null) {
        summary.addNewError(statis.getStatisCode(), statis.getStatisMsg(), statis.getStaticNumber());
      }
      else
        summaryStatistic.increaseNumber(statis.getStaticNumber());
    }
  }

  void addFileInPath(File filePath)
  {
    if (filePath == null) {
      return;
    }

    if (!(filePath.isDirectory())) {
      return;
    }

    File[] files = filePath.listFiles();

    CheckFileFilter filter = new CheckFileFilter();
    for (int i = 0; i < files.length; ++i)
    {
      if (files[i].isFile())
      {
        String prefix = files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1);

        if (cryptMode == 1) {
          if (prefix != null) if (!(prefix.equalsIgnoreCase("txt")));

        }
        else if (cryptMode == 2) {
          if (prefix != null) if (!(prefix.equalsIgnoreCase("enc")));

        }
        else
        {
          this.totalFiles += 1;
          if (filter.accept(files[i])) {
            addFiletoTable(files[i], PrepareFileStatus.WAIT);
            this.totalAcceptFiles += 1;
          } else {
            addFiletoTable(files[i], PrepareFileStatus.INVALID_NAME);
          }
        }
      } else {
        addFileInPath(files[i]);
      }
    }
  }

  void showMessage(int totalFiles, int totalAcceptFiles)
  {
    StringBuffer description = new StringBuffer();
    description.append("文件选择信息：");
    description.append("\r\n");
    description.append("共加入文件：");
    description.append(totalFiles);
    description.append("\r\n");

    JOptionPane.showMessageDialog(this, description.toString(), "提示", 1);
  }

  void setDestDir_actionPerformed(ActionEvent e)
  {
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setFileSelectionMode(1);
    int state = jFileChooser.showOpenDialog(this);
    File file = jFileChooser.getSelectedFile();
    if ((file != null) && (state == 0))
    {
      if (file.exists())
      {
        BatchContext.getInstance().addPropetiry("prepare_output_dir", file.getAbsolutePath());

        this.lblOutputDirectory.setText(file.getAbsolutePath());
      }
      else
      {
        JOptionPane.showMessageDialog(this, "您选择的目录不存在，请重新选择", "提示", 1);
      }
    }
  }

  void jTable_mouse_clicked(MouseEvent e)
  {
    if (cryptMode != 1)
      return;

    if ((e.getButton() == 1) && (e.getClickCount() == 2)) {
      DetailInformation detail = new DetailInformation(this, "详细信息", true);
      int row = this.jTable1.getSelectedRow();
      if (row != -1) {
        detail.setDisplayText(this.model.getRowData(row));
      }

      detail.show();
    }
  }

  public DealProgressBar getProgressBar()
  {
    return this.progressBar;
  }
}
