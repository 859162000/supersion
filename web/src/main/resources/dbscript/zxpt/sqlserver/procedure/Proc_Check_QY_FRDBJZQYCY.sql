CREATE procedure [dbo].[Proc_Check_QY_FRDBJZQYCY]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      ,b.JZCYSZQYMC
      ,b.JZCYXM
      ,b.JZGX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SZQYDDKKBM
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
    FROM  QY_JKRZBGC_JC as a, QY_FRDBJZQYCY as b
    WHERE  a.autoID=b.foreignid
else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      ,b.JZCYSZQYMC
      ,b.JZCYXM
      ,b.JZGX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SZQYDDKKBM
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
    FROM  QY_JKRZBGC_JC as a, QY_FRDBJZQYCY as b
    WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
