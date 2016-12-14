package com.icfcc.batch.core.field;

public class AncStringFieldDescriptor extends FieldDescriptor
{
  public String getFieldType()
  {
    return "ANC";
  }

  public String getRealData(String filledData)
  {
    if (this._ignoreBlank)
      return filledData.trim();

    return filledData;
  }
}