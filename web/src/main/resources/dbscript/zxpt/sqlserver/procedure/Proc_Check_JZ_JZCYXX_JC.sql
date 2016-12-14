CREATE procedure [dbo].[Proc_Check_JZ_JZCYXX_JC]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JZ_JZCYXX_JC;

else
SELECT autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JZ_JZCYXX_JC
  WHERE  dtDate=@dtDate and instInfo=@strInstCode;
