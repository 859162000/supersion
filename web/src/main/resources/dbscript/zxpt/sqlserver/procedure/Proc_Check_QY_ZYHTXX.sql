CREATE procedure [dbo].[Proc_Check_QY_ZYHTXX]
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
      ,b.CZRDDKKBM
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZYBZ
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWJZBZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZYHTXX as b where a.autoID = b.foreignid;

else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CZRDDKKBM
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZYBZ
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWJZBZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZYHTXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
