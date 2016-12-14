create or replace procedure Proc_Check_QY_SYDWZCFZB(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.CBJF
      ,b.CCP
      ,b.CL
      ,b.CZBZSR
      ,b.DFSDWBZ
      ,b.DWTZ
      ,b.FSDWJK
      ,b.FZBLZJ
      ,b.FZHJ
      ,b.GDJJ
      ,b.GDZC
      ,b.JJKX
      ,b.JYJY
      ,b.JYSR
      ,b.JYZC
      ,b.JZCHJ
      ,b.JZZCJJ
      ,b.QTSR
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRHJ
      ,b.SYJJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYZC
      ,b.TZJJ
      ,b.WXZC
      ,b.XJ
      ,b.XSSJ
      ,b.YBJJ
      ,b.YFPJ
      ,b.YFZK
      ,b.YFZK_1
      ,b.YHCK
      ,b.YJCZZHK
      ,b.YJSJ
      ,b.YJYFK
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.ZCBLZJ
      ,b.ZCHJ
      ,b.ZCHJ_1
      ,b.ZKZC
      ,b.ZYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWZCFZB b
    where  a.autoid=b.foreignid ';
ELSE
   V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.CBJF
      ,b.CCP
      ,b.CL
      ,b.CZBZSR
      ,b.DFSDWBZ
      ,b.DWTZ
      ,b.FSDWJK
      ,b.FZBLZJ
      ,b.FZHJ
      ,b.GDJJ
      ,b.GDZC
      ,b.JJKX
      ,b.JYJY
      ,b.JYSR
      ,b.JYZC
      ,b.JZCHJ
      ,b.JZZCJJ
      ,b.QTSR
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRHJ
      ,b.SYJJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYZC
      ,b.TZJJ
      ,b.WXZC
      ,b.XJ
      ,b.XSSJ
      ,b.YBJJ
      ,b.YFPJ
      ,b.YFZK
      ,b.YFZK_1
      ,b.YHCK
      ,b.YJCZZHK
      ,b.YJSJ
      ,b.YJYFK
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.ZCBLZJ
      ,b.ZCHJ
      ,b.ZCHJ_1
      ,b.ZKZC
      ,b.ZYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWZCFZB b
   where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR  V_CURSQL;
END;  


