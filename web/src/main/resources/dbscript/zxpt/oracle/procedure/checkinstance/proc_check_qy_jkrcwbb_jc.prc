create or replace procedure Proc_Check_QY_JKRCWBB_JC(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
 tempDate  varchar2(50);
V_CURSQL VARCHAR2(4000);  
BEGIN
tempDate := dtDate;
IF dtDate='*****'  and strInstCode='*****'
	THEN
	V_CURSQL :='select
     autoID
      ,BBLX
      ,BBLXXF
      ,BBNF
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SJRYMC
      ,SJSJ
      ,SJSWSMC
      ,XXJLCZLX
      ,XXJLGZBH
      ,XXJLLX
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from QY_JKRCWBB_JC';
ELSE
   V_CURSQL :='select
     autoID
      ,BBLX
      ,BBLXXF
      ,BBNF
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SJRYMC
      ,SJSJ
      ,SJSWSMC
      ,XXJLCZLX
      ,XXJLGZBH
      ,XXJLLX
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from QY_JKRCWBB_JC
     where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' ';
END IF;
OPEN rt FOR  V_CURSQL;
END;  


