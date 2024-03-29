create or replace procedure Proc_Check_JZ_JZCYXX_JC(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
 tempDate  varchar2(50);
 V_CURSQL VARCHAR2(4000);  
begin
tempDate := dtDate;
IF dtDate='*****'  and strInstCode='*****'
	THEN
	V_CURSQL :='select
     autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from JZ_JZCYXX_JC';

ELSE
   V_CURSQL :='select
     autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from JZ_JZCYXX_JC
     where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' ';

END IF;
OPEN rt FOR V_CURSQL;
END;  


