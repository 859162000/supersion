CREATE procedure [dbo].[Proc_Check_QY_MYRZ_ZQXX]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.ZQCS
      , b.ZQDQRQ
      , b.ZQJE
      , b.ZQQSRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_ZQXX as b
  WHERE a.autoID = b.foreignid;

else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.ZQCS
      , b.ZQDQRQ
      , b.ZQJE
      , b.ZQQSRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_ZQXX as b
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
