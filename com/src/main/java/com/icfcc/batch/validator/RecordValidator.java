// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   RecordValidator.java

package com.icfcc.batch.validator;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.Constants;
import com.icfcc.batch.core.*;
import com.icfcc.batch.util.LogConfiger;
import com.icfcc.batch.validator.checker.FieldChecker;
import java.text.DecimalFormat;
import java.util.*;
import org.apache.log4j.Category;

// Referenced classes of package com.icfcc.batch.validator:
//            SegmentValidator, CheckResult

public class RecordValidator extends Observable
{
    public class InternalVars
    {

        public String getCurrentDate()
        {
            return currentDate;
        }

        public void setCurrentDate(String currentDate)
        {
            this.currentDate = currentDate;
        }

        public int getDealedRecord()
        {
            return dealedRecord;
        }

        public void setDealedRecord(int dealedRecord)
        {
            this.dealedRecord = dealedRecord;
        }

        public void setFileName(String file)
        {
            fileName = file;
        }

        public String getFileName()
        {
            return fileName;
        }

        private String currentDate;
        private int dealedRecord;
        private String fileName;

        public InternalVars()
        {
            currentDate = RecordValidator.currentDate();
            dealedRecord = 0;
            fileName = null;
        }
    }


    public RecordValidator()
    {
        recordRule = new ArrayList();
        segmentCheckMap = new HashMap();
        internalVars = new InternalVars();
        dealRecordCount = 0;
        okRecordCount = 0;
        exptecedCount = 0;
        checkerMap = null;
        format = new DecimalFormat("###");
    }

    public Map getAllRuleInfo()
    {
        checkerMap = new LinkedHashMap();
        SegmentValidator headSegment = (SegmentValidator)segmentCheckMap.get("Head");
        List headerChecker = headSegment.getCheckList();
        FieldChecker fieldChecker;
        for(Iterator ckit = headerChecker.iterator(); ckit.hasNext(); checkerMap.put(fieldChecker.getErrorCode(), fieldChecker.getErrorMsg()))
            fieldChecker = (FieldChecker)ckit.next();

        FieldChecker recordRuleChecker;
        for(Iterator it = recordRule.iterator(); it.hasNext(); checkerMap.put(recordRuleChecker.getErrorCode(), recordRuleChecker.getErrorMsg()))
            recordRuleChecker = (FieldChecker)it.next();

        for(Iterator keyit = segmentCheckMap.keySet().iterator(); keyit.hasNext();)
        {
            Object key = keyit.next();
            SegmentValidator segment = (SegmentValidator)segmentCheckMap.get(key);
            List segmentChecker = segment.getCheckList();
            Iterator ckit = segmentChecker.iterator();
            while(ckit.hasNext()) 
            {
                fieldChecker = (FieldChecker)ckit.next();
                checkerMap.put(fieldChecker.getErrorCode(), fieldChecker.getErrorMsg());
            }
        }

        return checkerMap;
    }

    public String validate(Record record, int returnWay)
    {
        CheckResult doneresult = (CheckResult)BatchContext.getInstance().getProperites("message_process_result");
        doneresult.setOmitDuplicateError(true);
        StringBuffer result = new StringBuffer();
        if(record == null)
        {
            if((returnWay & 1) == 1)
                result.append("0000056");
            if((returnWay & 2) == 2)
            	result.append("{在当前记录中存在长度不符合接口规范规定的段:错误代码0000056}");
            doneresult.addNewError("0000056", "在当前记录中存在长度不符合接口规范规定的段:错误代码0000056");
            synchronized(this)
            {
                dealRecordCount++;
                internalVars.setDealedRecord(dealRecordCount);
            }
            return result.toString();
        }
        if((returnWay & 4) == 4)
        {
            synchronized(this)
            {
                okRecordCount++;
                dealRecordCount++;
                internalVars.setDealedRecord(dealRecordCount);
            }
            return "";
        }
        List segments = record.getAllSegment();
        Iterator it = segments.iterator();
        StringBuffer identifier=new StringBuffer();
        do
        {
            if(!it.hasNext())
                break;
            Segment segment = (Segment)it.next();
            SegmentValidator segmentChecker = (SegmentValidator)segmentCheckMap.get(segment.getSegmentDesc().getSegmentType());
            identifier.append(segment.getIdentifier());
            if(segmentChecker != null)
            {
                String checkResult = segmentChecker.validate(segment, returnWay);
                
                if(!checkResult.equals("")){                	
                    result.append(checkResult);
                    result.append(identifier.toString()).append("#OOOOOOOOOO#");
                    System.out.println(identifier.toString());
                }
            }
        } while(true);
        it = recordRule.iterator();
        do
        {
            if(!it.hasNext())
                break;
            FieldChecker recordRuleChecker = (FieldChecker)it.next();
            
            String checkResult = null;
            try
            {
                checkResult = recordRuleChecker.validate(record, returnWay);
                
            }
            catch(Exception ex)
            {
                if((returnWay & 1) == 1)
                    result.append(recordRuleChecker.getErrorCode());
                if((returnWay & 2) == 2){
                    result.append(recordRuleChecker.getErrorMsg());
                }
            }
            if(checkResult != null && !checkResult.equals(""))
            {
                if((returnWay & 1) == 1)
                    result.append(recordRuleChecker.getErrorCode());
                if((returnWay & 2) == 2)
                {
                    result.append("{");
                    result.append(checkResult);
                    result.append("}");
                }
                doneresult.addNewError(recordRuleChecker.getErrorCode(), recordRuleChecker.getErrorMsg());
            }
        } while(true);
        if(result.toString().trim().length() == 0)
            synchronized(this)
            {
                okRecordCount++;
            }
        synchronized(this)
        {
            dealRecordCount++;
            internalVars.setDealedRecord(dealRecordCount);
        }
        notifyObserver();
        return result.toString().trim();
    }

    public void addSegmentValidator(String segmentType, SegmentValidator validator)
    {
        segmentCheckMap.put(segmentType, validator);
    }

    public void addRecordChecker(FieldChecker checker)
    {
        recordRule.add(checker);
    }

    public Map getSegmentCheckMap()
    {
        return segmentCheckMap;
    }

    public int getDealRecordCount()
    {
        return dealRecordCount;
    }

    public void reset()
    {
        dealRecordCount = 0;
        okRecordCount = 0;
        exptecedCount = 0;
        internalVars.setDealedRecord(dealRecordCount);
    }

    private void notifyObserver()
    {
        if(exptecedCount > 0)
        {
            String percent = format.format((100 * dealRecordCount) / exptecedCount);
            setChanged();
            notifyObservers(percent);
        }
    }

    public String getInternalVar(String internalName)
    {
        if(internalName.equals("systemdate"))
            return internalVars.getCurrentDate();
        if(internalName.equals("checkedrecords"))
            return internalVars.getDealedRecord() + "";
        if(internalName.equals("currentYear"))
            return internalVars.getCurrentDate().substring(0, 4);
        if(sharedData != null)
            return (String)sharedData.get(internalName);
        else
            return null;
    }

    private static String currentDate()
    {
      Date date = new Date();

      Calendar gc = new GregorianCalendar();
      gc.setTime(date);

      String yy = "" + gc.get(1);
      String mm = "" + ((gc.get(2) + 1 > 9) ? "" + (gc.get(2) + 1) : new StringBuffer().append("0").append(gc.get(2) + 1).toString());

      String dd = "" + ((gc.get(5) > 9) ? "" + gc.get(5) : new StringBuffer().append("0").append(gc.get(5)).toString());

      return yy + mm + dd;
    }


    public Map getSharedData()
    {
        return sharedData;
    }

    public void setSharedData(Map sharedData)
    {
        this.sharedData = sharedData;
    }

    public int getOkRecordCount()
    {
        return okRecordCount;
    }

    public int getExptecedCount()
    {
        return exptecedCount;
    }

    public void setExptecedCount(int exptecedCount)
    {
        this.exptecedCount = exptecedCount;
    }

    private static Category _log;
    List recordRule;
    Map segmentCheckMap;
    InternalVars internalVars;
    int dealRecordCount;
    int okRecordCount;
    int exptecedCount;
    Map sharedData;
    Map checkerMap;
    DecimalFormat format;

    static 
    {
        _log = LogConfiger.getLoger(Constants.SYSTEM_LOG_NAME);
    }

}
