create or replace procedure Proc_Check_JG_ZYGLQY(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.DJZCHM
      ,b.DJZCHMLX
      ,b.GLLX
      ,b.GLQYMC
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XXGXRQ
      ,b.YLZD
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from JG_JGJBXX_JC a,JG_ZYGLQY b
  where a.autoid=b.foreignid';

ELSE
   V_CURSQL :='select
      a.dtdate,
      a.instinfo,
      b.autoID
      ,b.DJZCHM
      ,b.DJZCHMLX
      ,b.GLLX
      ,b.GLQYMC
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XXGXRQ
      ,b.YLZD
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from JG_JGJBXX_JC a,JG_ZYGLQY b
  where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR V_CURSQL;
END;  


