CREATE procedure [dbo].[Proc_Check_QY_HTJEXX]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKHTJE
      ,b.KYYE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTJEXX as b where a.autoID = b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKHTJE
      ,b.KYYE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTJEXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
