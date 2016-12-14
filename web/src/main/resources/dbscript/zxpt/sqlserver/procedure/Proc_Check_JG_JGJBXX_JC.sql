CREATE procedure [dbo].[Proc_Check_JG_JGJBXX_JC]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT autoID
      ,DEPTCODE
      ,DJZCH
      ,DJZCHLX
      ,DKKBM
      ,JGXYDM
      ,KHH
      ,KHLX
      ,KHXKZHZH
      ,NSRSBHDS
      ,NSRSBHGS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
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
  FROM JG_JGJBXX_JC;

else
SELECT autoID
      ,DEPTCODE
      ,DJZCH
      ,DJZCHLX
      ,DKKBM
      ,JGXYDM
      ,KHH
      ,KHLX
      ,KHXKZHZH
      ,NSRSBHDS
      ,NSRSBHGS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
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
  FROM JG_JGJBXX_JC
  WHERE  dtDate=@dtDate and instInfo=@strInstCode;
