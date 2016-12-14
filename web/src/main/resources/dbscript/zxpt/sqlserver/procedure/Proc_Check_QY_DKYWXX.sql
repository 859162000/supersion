CREATE procedure [dbo].[Proc_Check_QY_DKYWXX]
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
      ,b.DKJE
      ,b.DKKBM
      ,b.DKRQ
      ,b.DKYE
      ,b.DKZL
      ,b.HKFS
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.YEFSRQ
      ,b.YYWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKYWXX as b where  a.autoID=b.foreignid;

else
SELECT
	  a.dtDate
      ,a.instInfo  
	  ,b.autoID
      ,b.BZ
      ,b.DKJE
      ,b.DKKBM
      ,b.DKRQ
      ,b.DKYE
      ,b.DKZL
      ,b.HKFS
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.YEFSRQ
      ,b.YYWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKYWXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
