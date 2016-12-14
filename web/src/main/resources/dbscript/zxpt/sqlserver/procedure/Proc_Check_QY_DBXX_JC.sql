CREATE procedure [dbo].[Proc_Check_QY_DBXX_JC]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT  
       autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XDYWZL
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,ZHTBH
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_DBXX_JC;

else
 SELECT  
       autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XDYWZL
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,ZHTBH
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_DBXX_JC where dtDate=@dtDate and instInfo = @strInstCode;
