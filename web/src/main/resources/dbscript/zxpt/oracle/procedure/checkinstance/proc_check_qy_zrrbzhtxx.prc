create or replace procedure Proc_Check_QY_ZRRBZHTXX(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
 tempDate  varchar2(50);
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
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_DBXX_JC a,QY_ZRRBZHTXX b
  where a.autoid=b.foreignid';
ELSE
   V_CURSQL :='select
    a.dtdate,
    a.instinfo,
    b.autoID
      ,b.BZ
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_DBXX_JC a,QY_ZRRBZHTXX b
  where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR  V_CURSQL;
END;  


