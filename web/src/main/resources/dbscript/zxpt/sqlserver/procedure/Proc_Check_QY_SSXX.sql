CREATE procedure [dbo].[Proc_Check_QY_SSXX]
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
      ,b.BQSJLLSH
      ,b.BQSYY
      ,b.BZ
      ,b.PJZXJE
      ,b.PJZXRQ
      ,b.QSRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZXJG
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_SSXX as b where a.autoID = b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BQSJLLSH
      ,b.BQSYY
      ,b.BZ
      ,b.PJZXJE
      ,b.PJZXRQ
      ,b.QSRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZXJG
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_SSXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
