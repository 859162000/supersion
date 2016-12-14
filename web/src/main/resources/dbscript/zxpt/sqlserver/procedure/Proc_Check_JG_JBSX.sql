CREATE procedure [dbo].[Proc_Check_JG_JBSX]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CLRQ
    , b.CURRENCY
    , b.JGYWMC
    , b.JGZWMC
    , b.JJHYFL
    , b.JJLX
    , b.JKRGB
    , b.JYYWFW
    , b.QTXXGXRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XZQH
    , b.YLZD
    , b.ZCDJDZ
    , b.ZCZB
    , b.ZSDQR
    , b.ZZJGLBXF
    , b.ZZJGLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  FROM JG_JBSX as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;

else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CLRQ
    , b.CURRENCY
    , b.JGYWMC
    , b.JGZWMC
    , b.JJHYFL
    , b.JJLX
    , b.JKRGB
    , b.JYYWFW
    , b.QTXXGXRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XZQH
    , b.YLZD
    , b.ZCDJDZ
    , b.ZCZB
    , b.ZSDQR
    , b.ZZJGLBXF
    , b.ZZJGLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  FROM JG_JBSX as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
