package com.icfcc.batch.validator;

import com.icfcc.batch.core.MessageDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CheckResult
{
  private Map checkList = new LinkedHashMap();
  private Map recordCheckResult = null;
  private int totalRecords;
  private int okRecords;
  private int badRecords;

  public void setOmitDuplicateError(boolean status)
  {
    if (status)
      this.recordCheckResult = new HashMap();
  }
  public synchronized void resetStatus()
  {
    for (Iterator it = this.checkList.keySet().iterator(); it.hasNext(); )
    {
      Object key = it.next();
      CheckStatistic msg = (CheckStatistic)this.checkList.get(key);
      //System.out.println(msg.getStatisCode());
      msg.setStaticNumber(0);
    }
    setOmitDuplicateError(true);
  }

  public void setMessageDescriptor(MessageDescriptor descriptor)
  {
    MsgValidator validator = descriptor.getMsgValidator();
    RecordValidator recordValidator = validator.getRecordValidator();
    Map checkerInfoMap = recordValidator.getAllRuleInfo();

    for (Iterator it = checkerInfoMap.keySet().iterator(); it.hasNext(); )
    {
      Object key = it.next();
      String msg = (String)checkerInfoMap.get(key);
      this.checkList.put(key, new CheckStatistic((String)key, msg, 0));
    }
  }

  public Map getCheckerResult()
  {
    return this.checkList;
  }

  public synchronized void addNewError(String checkerCode, String errorMsg)
  {
    Object key = this.recordCheckResult.get(checkerCode);

    if (key == null)
    {
      CheckStatistic checkerResult = (CheckStatistic)this.checkList.get(checkerCode);
      if (checkerResult != null) {
        checkerResult.increaseNumber();
      } else {
        checkerResult = new CheckStatistic(checkerCode, errorMsg, 1);
        this.checkList.put(checkerCode, checkerResult);
      }
      this.recordCheckResult.put(checkerCode, checkerCode);
    }
  }

  public synchronized void addNewError(String checkerCode, String errorMsg, int number)
  {
    if (number < 0)
    {
      return;
    }

    Object key = this.recordCheckResult.get(checkerCode);

    if (key == null)
    {
      CheckStatistic checkerResult = (CheckStatistic)this.checkList.get(checkerCode);

      if (checkerResult != null)
      {
        checkerResult.increaseNumber(number);
      }
      else
      {
        checkerResult = new CheckStatistic(checkerCode, errorMsg, number);
        this.checkList.put(checkerCode, checkerResult);
      }

      this.recordCheckResult.put(checkerCode, checkerCode);
    }
  }

  public int getTotalRecords()
  {
    return this.totalRecords;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getOkRecords() {
    return this.okRecords;
  }

  public void setOkRecords(int okRecords) {
    this.okRecords = okRecords;
  }

  public int getBadRecords() {
    return this.badRecords;
  }

  public void setBadRecords(int badRecords) {
    this.badRecords = badRecords;
  }
}