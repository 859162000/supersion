CREATE procedure [dbo].[Proc_Check_QY_BHYWXX]
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
      ,b.BHDQR
      ,b.BHJE
      ,b.BHKLRQ
      ,b.BHYE
      ,b.BHZL
      ,b.BHZT
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.YEFSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID  
FROM QY_BHYW_JC as a,QY_BHYWXX as b  
where a.autoID=b.foreignid;

else
SELECT 
       a.dtDate
      ,a.instInfo
      ,b.autoID
      ,b.BHDQR
      ,b.BHJE
      ,b.BHKLRQ
      ,b.BHYE
      ,b.BHZL
      ,b.BHZT
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.YEFSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID  
FROM QY_BHYW_JC as a,QY_BHYWXX as b  where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.FOREIGNID;
