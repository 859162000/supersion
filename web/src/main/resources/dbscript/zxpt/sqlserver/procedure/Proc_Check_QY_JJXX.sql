CREATE procedure [dbo].[Proc_Check_QY_JJXX]
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
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_JJXX as b where a.autoID = b.foreignid;

else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_JJXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
