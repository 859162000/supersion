CREATE procedure [dbo].[Proc_Check_QY_BLXZXX]
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
      ,b.BLCPLX
      ,b.BLYWZT
      ,b.BZ
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.XZJE
      ,b.XZRQ
      ,b.XZYE
      ,b.YEBHRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLXZXX as b 
 where a.autoID=b.foreignid;

else
	SELECT
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BLCPLX
      ,b.BLYWZT
      ,b.BZ
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.XZJE
      ,b.XZRQ
      ,b.XZYE
      ,b.YEBHRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLXZXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
