CREATE procedure [dbo].[Proc_Check_QY_YHCDHP_JC]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,CDXYHM
      ,HPHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
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
  FROM QY_YHCDHP_JC;

else
SELECT autoID
      ,CDXYHM
      ,HPHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
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
  FROM QY_YHCDHP_JC
  WHERE  dtDate=@dtDate and instInfo=@strInstCode;
