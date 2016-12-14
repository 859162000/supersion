CREATE procedure [dbo].[Proc_Check_QY_XYZYW_JC]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XYZHM
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
  FROM QY_XYZYW_JC;
else
SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XYZHM
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
  FROM QY_XYZYW_JC
  WHERE dtDate=@dtDate and instInfo=@strInstCode;
