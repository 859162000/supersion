package com.icfcc.batch.validator;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.core.Segment;
import com.icfcc.batch.core.SegmentDescriptor;
import com.icfcc.batch.validator.checker.FieldChecker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SegmentValidator
{
  SegmentDescriptor segmentDesc;
  List checkList;
  List sharedDataList;

  public SegmentValidator()
  {
    this.segmentDesc = null;

    this.checkList = new ArrayList();

    this.sharedDataList = new ArrayList();
  }

  public void addCheckRule(FieldChecker checker)
  {
    this.checkList.add(checker);
  }

  public void addSharedData(SharedData data)
  {
    this.sharedDataList.add(data);
  }

  public String validate(Segment segment, int resultWay)
  {
    StringBuffer checkResultBuffer = new StringBuffer();
    CheckResult checkResult = (CheckResult)BatchContext.getInstance().getProperites("message_process_result");

    for (Iterator it = this.checkList.iterator(); it.hasNext(); )
    {
      FieldChecker checker = (FieldChecker)it.next();

      String result = null;
      try
      {
        result = checker.validate(segment, resultWay);
      }
      catch (Exception e)
      {
        if (resultWay == 1) {
          result = checker.getErrorCode();
        }
        else {
          result = checker.getErrorMsg();
        }

      }

      if (result.length() > 0)
      {
        checkResult.addNewError(checker.getErrorCode(), checker.getErrorMsg());

        if (resultWay == 1) {
          checkResultBuffer.append(result);
        }
        else {
        	checker.getCheckedStartPosition();
        	//checkResultBuffer.append(checker.getCheckedFieldName()).append("{####}");
        	checkResultBuffer.append(segment.getIdentifier()).append("#OOOO#").append(checker.getErrorCode()).append("#OOOO#");
            checkResultBuffer.append("{");
            checkResultBuffer.append(result);
            checkResultBuffer.append("}").append("#OOOOOOOO#");
        }

      }

    }

    return checkResultBuffer.toString().trim();
  }

  public List getSharedDataList()
  {
    return this.sharedDataList;
  }

  public SegmentDescriptor getSegmentDesc()
  {
    return this.segmentDesc;
  }

  public List getCheckList()
  {
    return this.checkList;
  }
}