CREATE procedure [dbo].[Proc_Check_QY_BLXDZCCL_JC]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,DKKBM
      ,GSZCDJH
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XZXH
      ,YWBH
      ,YWFSRQ
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BLXDZCCL_JC;

else
SELECT autoID
      ,DKKBM
      ,GSZCDJH
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XZXH
      ,YWBH
      ,YWFSRQ
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BLXDZCCL_JC where dtDate=@dtDate and instInfo = @strInstCode;
