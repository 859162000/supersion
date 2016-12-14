create or replace procedure Proc_Check_QY_JJXX(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_DKYW_JC a,QY_JJXX b
    where  a.autoid=b.foreignid';
ELSE
   V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BZ
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_DKYW_JC a,QY_JJXX b
      where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR  V_CURSQL;
END;  


