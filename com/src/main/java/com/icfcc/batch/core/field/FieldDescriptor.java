package com.icfcc.batch.core.field;

public abstract class FieldDescriptor
{
  protected String fieldName;
  protected String fieldType;
  protected int segmentStartPotison;
  protected int segmentEndPosition;
  protected boolean optional;
  protected String ignoreBlank = null;
  protected String hashField = null;
  protected boolean _ignoreBlank = true;
  private String fieldLength;
  private boolean trimBlanks;
  private boolean cancelTrim;
  private int trimoption;

  public String getFieldType()
  {
    return this.fieldType;
  }

  public String getFieldName()
  {
    return this.fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  public int getSegmentStartPotison() {
    return this.segmentStartPotison;
  }

  public void setSegmentStartPotison(int segmentStartPotison) {
    this.segmentStartPotison = segmentStartPotison;
  }

  public int getSegmentEndPosition() {
    return this.segmentEndPosition;
  }

  public void setSegmentEndPosition(int segmentEndPosition) {
    this.segmentEndPosition = segmentEndPosition;
  }

  public boolean isOptional() {
    return this.optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  public abstract String getRealData(String paramString);

  public String getIgnoreBlank()
  {
    return this.ignoreBlank;
  }

  public void setIgnoreBlank(String ignoreBlank) {
    this.ignoreBlank = ignoreBlank;

    if ((ignoreBlank != null) && (ignoreBlank.equalsIgnoreCase("false")))
      this._ignoreBlank = false;
  }

  public void setHashField(String hashField) {
    this.hashField = hashField;
  }

  public boolean getHashField()
  {
    return ((this.hashField != null) && (this.hashField.equalsIgnoreCase("true")));
  }

  public String getFieldLength()
  {
    return this.fieldLength;
  }

  public void setFieldLength(String fieldLength) {
    this.fieldLength = fieldLength;
  }

  public boolean getTrimBlanks() {
    return this.trimBlanks;
  }

  public boolean getCancelTrim() {
    return this.cancelTrim;
  }

  public int getTrimOption() {
    return this.trimoption;
  }

  public void setTrimBlanks(String trimBlanks) {
    if ((trimBlanks == null) || (trimBlanks.equals("false")))
      this.trimBlanks = false;
    else
      this.trimBlanks = true;
  }

  public void setCancelTrim(String cancelTrim)
  {
    if ((cancelTrim == null) || (cancelTrim.equals("false")))
      this.cancelTrim = false;
    else
      this.cancelTrim = true;
  }

  public void setTrimOption(int option)
  {
    this.trimoption = option;
  }
}