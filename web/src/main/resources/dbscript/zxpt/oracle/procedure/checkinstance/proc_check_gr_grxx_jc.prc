create or replace procedure Proc_Check_GR_GRXX_JC(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
is
 tempDate  varchar2(50);
 V_CURSQL VARCHAR2(4000);  
begin
tempDate := dtDate;
IF dtDate='*****'  and strInstCode='*****'
	THEN
V_CURSQL :='select
     autoID
      ,BYSJHKJE
      ,BYYHKJE
      ,BZ
      ,DBFS
      ,DQRQ
      ,DQYQQS
      ,DQYQZE
      ,FSDD
      ,GXSXED
      ,HKPL
      ,HKYS
      ,HKZT
      ,HKZT_24
      ,JRJGDM
      ,JSYHKRQ
      ,KHRQ
      ,LJYQQS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXED
      ,SYHKYS
      ,WJFLZT
      ,WZFYE
      ,XM
      ,XZXH
      ,YE
      ,YLZD
      ,YQ180YSHDKBJ
      ,YQ31_60HDKBJ
      ,YQ61_90HDKBJ
      ,YQ91_180HDKBJ
      ,YWH
      ,YWZL
      ,YWZLXF
      ,ZDFZE
      ,ZGYQQS
      ,ZHYCSJHKRQ
      ,ZHYYZXXTS
      ,ZHZT
      ,ZJHM
      ,ZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from GR_GRXX_JC';

ELSE
   V_CURSQL :='select
     autoID
      ,BYSJHKJE
      ,BYYHKJE
      ,BZ
      ,DBFS
      ,DQRQ
      ,DQYQQS
      ,DQYQZE
      ,FSDD
      ,GXSXED
      ,HKPL
      ,HKYS
      ,HKZT
      ,HKZT_24
      ,JRJGDM
      ,JSYHKRQ
      ,KHRQ
      ,LJYQQS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXED
      ,SYHKYS
      ,WJFLZT
      ,WZFYE
      ,XM
      ,XZXH
      ,YE
      ,YLZD
      ,YQ180YSHDKBJ
      ,YQ31_60HDKBJ
      ,YQ61_90HDKBJ
      ,YQ91_180HDKBJ
      ,YWH
      ,YWZL
      ,YWZLXF
      ,ZDFZE
      ,ZGYQQS
      ,ZHYCSJHKRQ
      ,ZHYYZXXTS
      ,ZHZT
      ,ZJHM
      ,ZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
     from GR_GRXX_JC
     where  to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' ';
END IF;
OPEN rt FOR V_CURSQL;
END;  

