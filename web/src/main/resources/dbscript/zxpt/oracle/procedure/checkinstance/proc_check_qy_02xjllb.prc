create or replace procedure Proc_Check_QY_02XJLLB(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.CHDJS
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY
      ,b.CZGDZC9819
      ,b.CZGDZC9871
      ,b.CZHDCS9851
      ,b.CZHDXJLCXJ
      ,b.CZHDXJLRXJ
      ,b.DTFYJS
      ,b.DYSKDX
      ,b.FPGLLR9845
      ,b.GDZCBFSS
      ,b.GDZCZJ
      ,b.GJGDZC9825
      ,b.GMSPJS9803
      ,b.HLBDDXJDYX
      ,b.JKSSDDXJ
      ,b.JLR
      ,b.JTDZCJZZB
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYSXMDJS
      ,b.JYXYSXMDZJ
      ,b.QDTZSYSSDDXJ
      ,b.QT9897
      ,b.QT9915
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDQTY9799
      ,b.SDDQTY9821
      ,b.SDDQTY9839
      ,b.SDDSFFH
      ,b.SHTZSSDDXJ
      ,b.TZHDCS9833
      ,b.TZHDXJLCXJ
      ,b.TZHDXJLRXJ
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJE9813_1
      ,b.XJJE9813_2
      ,b.XSSPHT9795
      ,b.XSTZSSDDXJ
      ,b.YNNDQD9893
      ,b.YTFYZJ
      ,b.ZFDGXSF
      ,b.ZFDQTY9809
      ,b.ZFDQTY9829
      ,b.ZFDQTY9847
      ,b.ZFGZGY9805
      ,b.ZJE9855_1
      ,b.ZJE9885_2
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02XJLLB b
    where  a.autoid=b.foreignid';

ELSE
   V_CURSQL :=' select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CHDJS
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY
      ,b.CZGDZC9819
      ,b.CZGDZC9871
      ,b.CZHDCS9851
      ,b.CZHDXJLCXJ
      ,b.CZHDXJLRXJ
      ,b.DTFYJS
      ,b.DYSKDX
      ,b.FPGLLR9845
      ,b.GDZCBFSS
      ,b.GDZCZJ
      ,b.GJGDZC9825
      ,b.GMSPJS9803
      ,b.HLBDDXJDYX
      ,b.JKSSDDXJ
      ,b.JLR
      ,b.JTDZCJZZB
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYSXMDJS
      ,b.JYXYSXMDZJ
      ,b.QDTZSYSSDDXJ
      ,b.QT9897
      ,b.QT9915
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDQTY9799
      ,b.SDDQTY9821
      ,b.SDDQTY9839
      ,b.SDDSFFH
      ,b.SHTZSSDDXJ
      ,b.TZHDCS9833
      ,b.TZHDXJLCXJ
      ,b.TZHDXJLRXJ
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJE9813_1
      ,b.XJJE9813_2
      ,b.XSSPHT9795
      ,b.XSTZSSDDXJ
      ,b.YNNDQD9893
      ,b.YTFYZJ
      ,b.ZFDGXSF
      ,b.ZFDQTY9809
      ,b.ZFDQTY9829
      ,b.ZFDQTY9847
      ,b.ZFGZGY9805
      ,b.ZJE9855_1
      ,b.ZJE9885_2
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02XJLLB b
      where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';

END IF;
OPEN rt FOR V_CURSQL;
END; 


