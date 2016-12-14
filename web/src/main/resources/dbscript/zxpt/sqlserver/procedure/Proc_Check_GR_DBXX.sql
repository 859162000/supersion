CREATE procedure [dbo].[Proc_Check_GR_DBXX]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.DBJE
    , b.DBZT
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XM
    , b.ZJHM
    , b.ZJLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_DBXX as b, GR_GRXX_JC as a
 WHERE  a.autoID=b.foreignid;
else
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.DBJE
    , b.DBZT
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XM
    , b.ZJHM
    , b.ZJLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_DBXX as b, GR_GRXX_JC as a
 WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
