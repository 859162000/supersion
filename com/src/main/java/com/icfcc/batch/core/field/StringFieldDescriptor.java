package com.icfcc.batch.core.field;

public class StringFieldDescriptor extends FieldDescriptor
{
  public String getFieldType()
  {
    return "AN";
  }

  public String getRealData(String filledData)
  {
    if (this._ignoreBlank)
      return filledData.trim();

    return filledData;
  }
}