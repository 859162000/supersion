create or replace procedure Proc_Check_QY_07LRJLRFPB(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
 tempDate  varchar2(50);
 V_CURSQL VARCHAR2(4000);  
begin
tempDate := dtDate;
IF dtDate='*****'  and strInstCode='*****'
	THEN
	V_CURSQL :='select
    a.dtdate,
    a.instinfo,
    b.autoID
      ,b.CWFY
      ,b.DLYQYHHYQYDTZSY
      ,b.FLDZCSS
      ,b.GLFY
      ,b.GYJZBDJSY
      ,b.JBMGSY
      ,b.JLR
      ,b.LRZE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDSFY
      ,b.TZJSY
      ,b.XSFY
      ,b.XSMGSY
      ,b.YYCB
      ,b.YYLR
      ,b.YYSJJFJ
      ,b.YYSR
      ,b.YYWSR
      ,b.YYWZC
      ,b.ZCJZSS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_JKRCWBB_JC a,QY_07LRJLRFPB b
  where a.autoid=b.foreignid';

ELSE
   V_CURSQL :='select
    a.dtdate,
    a.instinfo,
    b.autoID
      ,b.CWFY
      ,b.DLYQYHHYQYDTZSY
      ,b.FLDZCSS
      ,b.GLFY
      ,b.GYJZBDJSY
      ,b.JBMGSY
      ,b.JLR
      ,b.LRZE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDSFY
      ,b.TZJSY
      ,b.XSFY
      ,b.XSMGSY
      ,b.YYCB
      ,b.YYLR
      ,b.YYSJJFJ
      ,b.YYSR
      ,b.YYWSR
      ,b.YYWZC
      ,b.ZCJZSS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_JKRCWBB_JC a,QY_07LRJLRFPB b
  where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR V_CURSQL;
END;  


