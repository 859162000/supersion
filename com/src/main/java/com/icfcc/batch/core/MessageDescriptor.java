package com.icfcc.batch.core;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.Constants;
import com.icfcc.batch.core.field.FieldDescriptor;
import com.icfcc.batch.core.field.SegmentFieldFactory;
import com.icfcc.batch.process.reader.ReadingException;
import com.icfcc.batch.util.LogWriter;
import com.icfcc.batch.validator.MsgValidator;
import com.icfcc.batch.validator.RecordValidator;
import com.icfcc.batch.validator.SegmentValidator;
import com.icfcc.batch.validator.SharedData;
import com.icfcc.batch.validator.checker.CheckerFactory;
import com.icfcc.batch.validator.checker.FieldChecker;
import com.icfcc.foundation.configuration.XMLConfiguration;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageDescriptor
{
  private static String configFileName;
  private static HashMap _instance = new HashMap();
  private static boolean initialized = false;
  private String version;
  private int headerLength;
  private HashMap segmentMap = new HashMap();
  private List segmentList = new ArrayList();
  private MsgValidator msgValidator;
  private String charset;
  private String readerVersion;
  private int tasktype;

  public static void refresh()
  {
    initialized = false;
    try {
      init();
    }
    catch (ReadingException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static MessageDescriptor getInstance(String forVersion)
  {
    return ((MessageDescriptor)_instance.get(forVersion));
  }

  public static void init()
    throws ReadingException
  {
    StringBuffer baseMessage;
    if (initialized) {
      return;
    }

    XMLConfiguration config = new XMLConfiguration();
    int messageCount = 0;

    String homeDir = BatchContext.getInstance().getBaseHome();
    String configurationFile = null;

    if (configFileName != null) {
      configurationFile = configFileName;
    }
    else {
      configurationFile = homeDir + Constants.SYSTEM_MESSAGE_DESCRIPTOR_CONFIG_NAME;
    }

    try
    {
      config.loadConfiguration(configurationFile);

      baseMessage = new StringBuffer("messageList.message");

      StringBuffer messageKey = new StringBuffer(baseMessage.toString());
      messageKey.append("[@version]");
      messageCount = config.getKeyCount(messageKey.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
      LogWriter.getInstance().writeSystemFatalLog("无法读取message.xml配置文件，无法解析文件", e);
      throw new ReadingException(1101, "无法读取message.xml配置文件，无法解析文件");
    }

    for (int j = 0; j < messageCount; )
    {
      try
      {
        StringBuffer msgBaseString = new StringBuffer(baseMessage.toString());

        msgBaseString.append("(");
        msgBaseString.append(j);
        msgBaseString.append(")");

        StringBuffer strTasktype = new StringBuffer(msgBaseString.toString());

        strTasktype.append("[@tasktype]");

        StringBuffer strVersion = new StringBuffer(msgBaseString.toString());

        strVersion.append("[@version]");

        StringBuffer headLength = new StringBuffer(msgBaseString.toString());

        headLength.append("[@headerLength]");

        StringBuffer readerVersion = new StringBuffer(msgBaseString.toString());

        readerVersion.append("[@readerVersion]");

        StringBuffer charset = new StringBuffer(msgBaseString.toString());
        charset.append("[@charset]");

        MessageDescriptor message = new MessageDescriptor();
        message.setTasktype(config.getInt(strTasktype.toString()));
        message.setVersion(config.getString(strVersion.toString()));
        message.setHeaderLength(Integer.parseInt(config.getString(headLength.toString())));

        message.setCharset(config.getString(charset.toString()));
        message.setReaderVersion(config.getString(readerVersion.toString()));

        MsgValidator validator = MsgValidator.getInstance(message.getVersion());

        RecordValidator recordValidator = new RecordValidator();

        StringBuffer baseSegmentString = new StringBuffer(msgBaseString.toString());

        baseSegmentString.append(".segment");
        StringBuffer segmentKey = new StringBuffer(baseSegmentString.toString());

        segmentKey.append("[@name]");
        int keyCount = config.getKeyCount(segmentKey.toString());
        for (int i = 0; i < keyCount; ++i)
        {
          StringBuffer segmentBaseString = new StringBuffer(baseSegmentString.toString());

          segmentBaseString.append("(");
          segmentBaseString.append(i);
          segmentBaseString.append(")");

          SegmentDescriptor segmentDesc = getSegment(segmentBaseString, config);

          StringBuffer baseField = new StringBuffer(segmentBaseString.toString());

          baseField.append(".field");

          StringBuffer fieldKey = new StringBuffer(baseField.toString());
          fieldKey.append("[@name]");

          int fieldCount = config.getKeyCount(fieldKey.toString());
          for (int k = 0; k < fieldCount; ++k)
          {
            StringBuffer baseFieldString = new StringBuffer(baseField.toString());

            baseFieldString.append("(");
            baseFieldString.append(k);
            baseFieldString.append(")");

            FieldDescriptor field = getField(baseFieldString, config);

            segmentDesc.addFieldDescriptor(field);
          }

          message.addSegmentDescriptor(segmentDesc.getSegmentType(), segmentDesc);

          SegmentValidator segmentValidator = new SegmentValidator();

          StringBuffer baseRuleString = new StringBuffer(segmentBaseString.toString());

          baseRuleString.append(".checkrules(0).rule");
          StringBuffer rulesNameString = new StringBuffer(baseRuleString.toString());

          rulesNameString.append("[@name]");

          int ruleCount = config.getKeyCount(rulesNameString.toString());

          for (int iRuleCount = 0; iRuleCount < ruleCount; ++iRuleCount)
          {
            StringBuffer baseRule = new StringBuffer(baseRuleString.toString());

            baseRule.append("(");
            baseRule.append(iRuleCount);
            baseRule.append(")");
            FieldChecker checker = getFieldChecker(baseRule, config, recordValidator);

            addPreChecker(checker, baseRule, config, recordValidator);

            segmentValidator.addCheckRule(checker);
          }

          StringBuffer baseSharedDataString = new StringBuffer(segmentBaseString.toString());

          baseSharedDataString.append(".dataSource(0).sharedData");
          StringBuffer sharedDataNameString = new StringBuffer(baseSharedDataString.toString());

          sharedDataNameString.append("[@name]");

          int sharedDataCount = config.getKeyCount(sharedDataNameString.toString());

          for (int iDataCount = 0; iDataCount < sharedDataCount; 
            ++iDataCount)
          {
            StringBuffer baseRule = new StringBuffer(baseSharedDataString.toString());

            baseRule.append("(");
            baseRule.append(iDataCount);
            baseRule.append(")");
            SharedData sourceData = getSharedData(baseRule, config);
            segmentValidator.addSharedData(sourceData);
          }

          recordValidator.addSegmentValidator(segmentDesc.getSegmentType(), segmentValidator);
        }

        StringBuffer recordBaseRule = new StringBuffer(msgBaseString.toString());

        recordBaseRule.append(".checkrules(0).rule");
        StringBuffer recordRuleKey = new StringBuffer(recordBaseRule.toString());

        recordRuleKey.append("[@name]");

        int recordRuleInt = config.getKeyCount(recordRuleKey.toString());

        for (int recordRuleNumber = 0; recordRuleNumber < recordRuleInt; 
          ++recordRuleNumber)
        {
          StringBuffer recordRuleString = new StringBuffer(recordBaseRule.toString());

          recordRuleString.append("(");
          recordRuleString.append(recordRuleNumber);
          recordRuleString.append(")");

          FieldChecker recordChecker = getFieldChecker(recordRuleString, config, recordValidator);

          addPreChecker(recordChecker, recordRuleString, config, recordValidator);

          if (recordChecker != null) {
            recordValidator.addRecordChecker(recordChecker);
          }

        }

        validator.setRecordValidator(recordValidator);
        validator.setMsgVersion(message.getVersion());
        message.setMsgValidator(validator);

        String key = message.getTasktype() + "." + message.getVersion();
        _instance.put(key, message);
      }
      catch (Exception le) {
        LogWriter.getInstance().writeSystemErrorLog("从配置文件中读取配置信息出现错误", le);

        throw new ReadingException(1102, "从配置文件中读取配置信息出现错误");
      }
      ++j;
    }

    if (_instance.size() == 0) {
      LogWriter.getInstance().writeSystemErrorLog("报文版本信息或者报文版本信息不存在", null);
      throw new ReadingException(1102, "报文版本信息或者报文版本信息不存在");
    }

    initialized = true;
  }

  private static void addPreChecker(FieldChecker checker, StringBuffer baseRule, XMLConfiguration config, RecordValidator validator)
    throws Exception
  {
    StringBuffer preRuleBase = new StringBuffer(baseRule.toString());
    preRuleBase.append(".precondition(0).rule");
    StringBuffer preRuleBaseRuleName = new StringBuffer(preRuleBase.toString());

    preRuleBaseRuleName.append("[@name]");

    int preRulekeyCount = 0;
    try {
      preRulekeyCount = config.getKeyCount(preRuleBaseRuleName.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    for (int ruleKeyCount = 0; ruleKeyCount < preRulekeyCount; ++ruleKeyCount) {
      StringBuffer preRuleName = new StringBuffer(preRuleBase.toString());
      preRuleName.append("(");
      preRuleName.append(ruleKeyCount);
      preRuleName.append(")");
      FieldChecker preChecker = getFieldChecker(preRuleName, config, validator);

      if (preChecker != null)
        checker.addPreChecker(preChecker);
    }
  }

  private static SegmentDescriptor getSegment(StringBuffer segmentBaseString, XMLConfiguration config)
    throws Exception
  {
    StringBuffer segmentType = new StringBuffer(segmentBaseString.toString());
    segmentType.append("[@name]");

    StringBuffer segmentLength = new StringBuffer(segmentBaseString.toString());

    segmentLength.append("[@segmentLength]");

    StringBuffer segmentOptional = new StringBuffer(segmentBaseString.toString());

    segmentOptional.append("[@opitional]");

    StringBuffer segmentUnique = new StringBuffer(segmentBaseString.toString());

    segmentUnique.append("[@unique]");

    SegmentDescriptor segmentDesc = new SegmentDescriptor();
    segmentDesc.setSegmentType(config.getString(segmentType.toString()));
    segmentDesc.setSegmentLength(Integer.parseInt(config.getString(segmentLength.toString())));

    String opitional = config.getString(segmentOptional.toString());
    if ((opitional == null) || (opitional.equals("false"))) {
      segmentDesc.setOpitioal(false);
    }
    else {
      segmentDesc.setOpitioal(true);
    }

    String unique = config.getString(segmentUnique.toString());
    if ((unique == null) || (unique.equals("false"))) {
      segmentDesc.setUnique(false);
    }
    else
      segmentDesc.setUnique(true);

    return segmentDesc;
  }

  private static FieldDescriptor getField(StringBuffer baseFieldString, XMLConfiguration config)
    throws Exception
  {
    StringBuffer fieldName = new StringBuffer(baseFieldString.toString());
    fieldName.append("[@name]");
    StringBuffer fieldType = new StringBuffer(baseFieldString.toString());
    fieldType.append("[@type]");
    StringBuffer fieldStartPosition = new StringBuffer(baseFieldString.toString());

    fieldStartPosition.append("[@startPosition]");
    StringBuffer fieldEndPosition = new StringBuffer(baseFieldString.toString());

    fieldEndPosition.append("[@endPosition]");

    StringBuffer fieldOpitioal = new StringBuffer(baseFieldString.toString());

    fieldOpitioal.append("[@opitional]");

    StringBuffer ignoreBlank = new StringBuffer(baseFieldString.toString());

    ignoreBlank.append("[@ignoreBlank]");

    StringBuffer hashField = new StringBuffer(baseFieldString.toString());

    hashField.append("[@hashField]");

    StringBuffer fieldLength = new StringBuffer(baseFieldString.toString());

    fieldLength.append("[@length]");

    StringBuffer trimOption = new StringBuffer(baseFieldString.toString());

    trimOption.append("[@trimoption]");

    String fieldTypeDefined = config.getString(fieldType.toString());
    FieldDescriptor field = SegmentFieldFactory.getFieldDescriptor(fieldTypeDefined);

    if (field == null) {
      return null;
    }

    int startPostionDefined = -1;
    int endPostionDefined = -1;

    String strStartPosition = config.getString(fieldStartPosition.toString());
    if (strStartPosition != null) {
      startPostionDefined = Integer.parseInt(strStartPosition);
    }

    String strEndPosition = config.getString(fieldEndPosition.toString());
    if (strEndPosition != null) {
      endPostionDefined = Integer.parseInt(strEndPosition);
    }

    String fieldNameDefined = config.getString(fieldName.toString());
    String fieldOpitionalDefined = config.getString(fieldOpitioal.toString());

    field.setFieldName(fieldNameDefined);
    field.setSegmentEndPosition(endPostionDefined);
    field.setSegmentStartPotison(startPostionDefined);

    if ((fieldOpitionalDefined == null) || (fieldOpitionalDefined.equals("false")))
    {
      field.setOptional(false);
    }
    else {
      field.setOptional(true);
    }

    field.setIgnoreBlank(config.getString(ignoreBlank.toString()));
    field.setHashField(config.getString(hashField.toString()));
    field.setFieldLength(config.getString(fieldLength.toString()));

    field.setTrimOption(config.getInt(trimOption.toString()));
    return field;
  }

  private static FieldChecker getFieldChecker(StringBuffer baseRuleString, XMLConfiguration config, RecordValidator validator)
    throws Exception
  {
    StringBuffer checkType = new StringBuffer(baseRuleString.toString());
    checkType.append("[@checkType]");

    StringBuffer parameterRange = new StringBuffer(baseRuleString.toString());
    parameterRange.append("[@parameterRange]");

    StringBuffer checkedFieldName = new StringBuffer(baseRuleString.toString());

    checkedFieldName.append("[@checkedFieldName]");

    StringBuffer compareWay = new StringBuffer(baseRuleString.toString());
    compareWay.append("[@compareWay]");

    StringBuffer reverseCheck = new StringBuffer(baseRuleString.toString());
    reverseCheck.append("[@reverseCheck]");

    StringBuffer dataList = new StringBuffer(baseRuleString.toString());
    dataList.append("[@dataList]");

    StringBuffer checkedStartPosition = new StringBuffer(baseRuleString.toString());

    checkedStartPosition.append("[@checkedStartPosition]");

    StringBuffer checkedEndPostion = new StringBuffer(baseRuleString.toString());

    checkedEndPostion.append("[@checkedEndPostion]");

    StringBuffer dataStartValue = new StringBuffer(baseRuleString.toString());
    dataStartValue.append("[@dataStartValue]");

    StringBuffer dataEndValue = new StringBuffer(baseRuleString.toString());
    dataEndValue.append("[@dataEndValue]");

    StringBuffer errorCode = new StringBuffer(baseRuleString.toString());
    errorCode.append("[@errorCode]");

    StringBuffer dataEndLevel = new StringBuffer(baseRuleString.toString());
    dataEndLevel.append("[@errorLevel]");

    StringBuffer iterateLength = new StringBuffer(baseRuleString.toString());
    iterateLength.append("[@iterateLength]");

    StringBuffer orderInList = new StringBuffer(baseRuleString.toString());
    orderInList.append("[@orderInList]");

    StringBuffer errorMsg = new StringBuffer(baseRuleString.toString());
    errorMsg.append("[@errorMsg]");

    StringBuffer strictLevel = new StringBuffer(baseRuleString.toString());
    strictLevel.append("[@strictLevel]");

    StringBuffer checkLevel = new StringBuffer(baseRuleString.toString());
    checkLevel.append("[@checkLevel]");

    FieldChecker checker = CheckerFactory.createChecker(config.getString(checkType.toString()), validator);

    if (checker == null) {
      LogWriter.getInstance().writeSystemInfoLog("Null config found:" + config.getString(checkType.toString()));

      return null;
    }

    String startPosition = config.getString(checkedStartPosition.toString());
    if ((startPosition != null) && (startPosition.length() > 0)) {
      checker.setCheckedStartPosition(Integer.parseInt(startPosition));
    }

    String endPostion = config.getString(checkedEndPostion.toString());
    if ((endPostion != null) && (endPostion.length() > 0)) {
      checker.setCheckedEndPostion(Integer.parseInt(endPostion));
    }

    String reverse = config.getString(reverseCheck.toString());
    if ((reverse == null) || (!(reverse.equalsIgnoreCase("true")))) {
      checker.setReverseCheck(false);
    }
    else {
      checker.setReverseCheck(true);
    }

    checker.setCheckedFieldName(config.getString(checkedFieldName.toString()));
    checker.setDataEndValue(config.getString(dataEndValue.toString()));
    checker.setDataList(config.getString(dataList.toString()));
    checker.setDataStartValue(config.getString(dataStartValue.toString()));
    checker.setParameterRange(config.getString(parameterRange.toString()));
    checker.setCompareWay(config.getString(compareWay.toString()));
    checker.setErrorCode(config.getString(errorCode.toString()));
    checker.setErrorLevel(config.getString(dataEndLevel.toString()));
    checker.setIterateLength(config.getString(iterateLength.toString()));
    checker.setOrderInList(config.getString(orderInList.toString()));
    checker.setStrictLevel(config.getString(strictLevel.toString()));
    checker.setErrorMsg(config.getString(errorMsg.toString()));
    checker.setCheckLevel(config.getString(checkLevel.toString()));

    return checker;
  }

  private static SharedData getSharedData(StringBuffer baseDataString, XMLConfiguration config)
    throws Exception
  {
    StringBuffer dataName = new StringBuffer(baseDataString.toString());
    dataName.append("[@name]");

    StringBuffer dataType = new StringBuffer(baseDataString.toString());
    dataType.append("[@type]");

    StringBuffer range = new StringBuffer(baseDataString.toString());
    range.append("[@range]");

    StringBuffer sourceData = new StringBuffer(baseDataString.toString());
    sourceData.append("[@sourceData]");

    SharedData shareData = new SharedData();

    shareData.setName(config.getString(dataName.toString()));
    shareData.setRange(config.getString(range.toString()));
    shareData.setSourceData(config.getString(sourceData.toString()));
    shareData.setType(config.getString(dataType.toString()));
    return shareData;
  }

  public String getVersion()
  {
    return this.version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public void addSegmentDescriptor(String segmentKey, SegmentDescriptor segment)
  {
    this.segmentMap.put(segmentKey, segment);
    this.segmentList.add(segment);
  }

  public SegmentDescriptor getSegmentDesc(String segmentType)
  {
    SegmentDescriptor descriptor = (SegmentDescriptor)this.segmentMap.get(segmentType);

    return descriptor;
  }

  public int getHeaderLength()
  {
    return this.headerLength;
  }

  public void setHeaderLength(int headerLength)
  {
    this.headerLength = headerLength;
  }

  public List getSegmentList() {
    return this.segmentList;
  }

  public SegmentDescriptor getHeadDesc() {
    return ((SegmentDescriptor)this.segmentMap.get("Head"));
  }

  public MsgValidator getMsgValidator() {
    return this.msgValidator;
  }

  public void setMsgValidator(MsgValidator msgValidator) {
    this.msgValidator = msgValidator;
  }

  public String getCharset() {
    return this.charset;
  }

  public void setCharset(String charset) {
    this.charset = charset;
  }

  public String getReaderVersion() {
    return this.readerVersion;
  }

  public void setReaderVersion(String readerVersion) {
    this.readerVersion = readerVersion;
  }

  public static String getConfigFileName() {
    return configFileName;
  }

  public static void setConfigFileName(String configFile) {
    configFileName = configFile; }

  public int getTasktype() {
    return this.tasktype; }

  public void setTasktype(int tasktype) {
    this.tasktype = tasktype;
  }
}
