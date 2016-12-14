create or replace procedure Proc_Check_QY_QXYWXX(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
V_CURSQL VARCHAR2(4000);  
BEGIN
tempDate := dtDate;
IF dtDate='*****'  and strInstCode='*****'
	THEN
	V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BZ
      ,b.QXLX
      ,b.QXYE
      ,b.QXYEGBRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_QXXX_JC a,QY_QXYWXX b
        where a.autoid=b.foreignid ';
ELSE
   V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BZ
      ,b.QXLX
      ,b.QXYE
      ,b.QXYEGBRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_QXXX_JC a,QY_QXYWXX b
       where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR  V_CURSQL;
END;  