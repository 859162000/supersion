create or replace procedure Proc_Check_QY_07ZCFZB(rt out DevelopmentFramework.packageCheck,dtDate varchar2,strInstCode  varchar2)
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
      ,b.CH
      ,b.CQDTFY
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQYFK
      ,b.CQYSK
      ,b.CYZDQTZ
      ,b.DQJK
      ,b.DYSDSFZ
      ,b.DYSDSZC
      ,b.FLDFZHJ
      ,b.FLDZCHJ
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZC
      ,b.GDZCQL
      ,b.HBZJ
      ,b.JKCG
      ,b.JYXJRFZ
      ,b.JYXJRZC
      ,b.KFZC
      ,b.KGCSDJRZC
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.QTFLDFZ
      ,b.QTFLDZC
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SCXSWZC
      ,b.SSZBHGB
      ,b.SY
      ,b.SYZQYHJ
      ,b.TZXFDC
      ,b.WFPLR
      ,b.WXZC
      ,b.YFGL
      ,b.YFLX
      ,b.YFPJ
      ,b.YFZGXZ
      ,b.YFZK_1
      ,b.YFZK_2
      ,b.YFZQ
      ,b.YJFZ
      ,b.YJSF
      ,b.YNNDQDFLDFZ
      ,b.YNNDQDFLDZC
      ,b.YQZC
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.YYGJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
      from QY_JKRCWBB_JC a,QY_07ZCFZB b
        where a.autoid=b.foreignid';

ELSE
   V_CURSQL :='select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CH
      ,b.CQDTFY
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQYFK
      ,b.CQYSK
      ,b.CYZDQTZ
      ,b.DQJK
      ,b.DYSDSFZ
      ,b.DYSDSZC
      ,b.FLDFZHJ
      ,b.FLDZCHJ
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZC
      ,b.GDZCQL
      ,b.HBZJ
      ,b.JKCG
      ,b.JYXJRFZ
      ,b.JYXJRZC
      ,b.KFZC
      ,b.KGCSDJRZC
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.QTFLDFZ
      ,b.QTFLDZC
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SCXSWZC
      ,b.SSZBHGB
      ,b.SY
      ,b.SYZQYHJ
      ,b.TZXFDC
      ,b.WFPLR
      ,b.WXZC
      ,b.YFGL
      ,b.YFLX
      ,b.YFPJ
      ,b.YFZGXZ
      ,b.YFZK_1
      ,b.YFZK_2
      ,b.YFZQ
      ,b.YJFZ
      ,b.YJSF
      ,b.YNNDQDFLDFZ
      ,b.YNNDQDFLDZC
      ,b.YQZC
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.YYGJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
      from QY_JKRCWBB_JC a,QY_07ZCFZB b
       where to_Date('''||tempDate||''', ''yyyy-MM-dd'')=dtDate and  Instinfo= '''||strInstCode||''' and a.autoid=b.foreignid';
END IF;
OPEN rt FOR V_CURSQL;
END;  


