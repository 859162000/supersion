package com.icfcc.batch.core.field;

public class NumberFieldDescriptor extends FieldDescriptor
{
  public String getFieldType()
  {
    return "N";
  }

  public String getRealData(String filledData)
  {
    String returnValue = null;
    try {
      returnValue = "" + Integer.parseInt(filledData.trim());
    } catch (Exception e) {
      returnValue = filledData;
    }
    return returnValue;
  }
}