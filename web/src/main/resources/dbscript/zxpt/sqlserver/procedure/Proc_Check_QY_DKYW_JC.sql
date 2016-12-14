CREATE procedure [dbo].[Proc_Check_QY_DKYW_JC]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,DKHTHM
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKYW_JC;

else
SELECT autoID
      ,DKHTHM
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKYW_JC where dtDate=@dtDate and instInfo = @strInstCode;
