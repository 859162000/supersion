create or replace procedure Proc_Check_QY_SYDWSRZCB(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.BRZKXJ
      ,b.CZBZSR
      ,b.CZBZZC
      ,b.DFSDWBZ
      ,b.FSDWJK
      ,b.JYFP
      ,b.JYJY
      ,b.JYSR
      ,b.JYSRXJ
      ,b.JYZC
      ,b.JYZCXJ
      ,b.JZZCJJ
      ,b.QTJYFP
      ,b.QTSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SHYQNDSYZC
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRZJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYSRXJ
      ,b.SYZC
      ,b.SYZCXJ
      ,b.TQZYJJ
      ,b.XSSJ_1
      ,b.XSSJ_2
      ,b.YJSDS
      ,b.YQNDJYKS
      ,b.YSWZJSR
      ,b.YSWZJZC
      ,b.ZCSRJY
      ,b.ZCZJ
      ,b.ZKXJ
      ,b.ZKZC
      ,b.ZRSYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWSRZCB b
    where a.autoid=b.foreignid';
ELSE
   V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.BRZKXJ
      ,b.CZBZSR
      ,b.CZBZZC
      ,b.DFSDWBZ
      ,b.FSDWJK
      ,b.JYFP
      ,b.JYJY
      ,b.JYSR
      ,b.JYSRXJ
      ,b.JYZC
      ,b.JYZCXJ
      ,b.JZZCJJ
      ,b.QTJYFP
      ,b.QTSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SHYQNDSYZC
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRZJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYSRXJ
      ,b.SYZC
      ,b.SYZCXJ
      ,b.TQZYJJ
      ,b.XSSJ_1
      ,b.XSSJ_2
      ,b.YJSDS
      ,b.YQNDJYKS
      ,b.YSWZJSR
      ,b.YSWZJZC
      ,b.ZCSRJY
      ,b.ZCZJ
      ,b.ZKXJ
      ,b.ZKZC
      ,b.ZRSYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWSRZCB b
    where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR  V_CURSQL;
END;  


