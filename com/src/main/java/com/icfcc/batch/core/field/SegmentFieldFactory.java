package com.icfcc.batch.core.field;

public class SegmentFieldFactory
{
  public static FieldDescriptor getFieldDescriptor(String fieldType)
  {
    if (fieldType.equals("N"))
      return new NumberFieldDescriptor();
    if (fieldType.equals("AN"))
      return new StringFieldDescriptor();
    if (fieldType.equals("ANC"))
      return new AncStringFieldDescriptor();

    return null;
  }
}