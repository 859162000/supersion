package com.icfcc.batch.process;

import java.util.List;
import java.util.Observer;

import com.icfcc.batch.BatchException;

public abstract interface MsgHandler
{
  public abstract List handlerMsg(String msgFile, String badFile, String passFile,int returnWay)
    throws BatchException;

  public abstract void addObserver(Observer paramObserver);
}