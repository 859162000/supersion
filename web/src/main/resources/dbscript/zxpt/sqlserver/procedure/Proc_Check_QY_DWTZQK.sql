CREATE procedure [dbo].[Proc_Check_QY_DWTZQK]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BTZDWDKKBM
      , b.BTZDWMC
      , b.BZ1
      , b.BZ2
      , b.BZ3
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.TZJE1
      , b.TZJE2
      , b.TZJE3
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
 
    FROM  QY_YHCDHP_JC as a, QY_DWTZQK as b
    WHERE a.autoID=b.foreignid;

else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BTZDWDKKBM
      , b.BTZDWMC
      , b.BZ1
      , b.BZ2
      , b.BZ3
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.TZJE1
      , b.TZJE2
      , b.TZJE3
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
 
    FROM  QY_YHCDHP_JC as a, QY_DWTZQK as b
    WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
