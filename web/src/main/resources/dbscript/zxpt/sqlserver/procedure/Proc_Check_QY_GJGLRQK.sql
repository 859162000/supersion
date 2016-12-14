CREATE procedure [dbo].[Proc_Check_QY_GJGLRQK]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT 
  a.dtDate,
a.instInfo,
b.autoID, 
b.GGRYCSRQ,
b.GGRYGZJL, 
b.GGRYLB,
b.GGRYXM, 
b.GGRYZGXL, 
b.RPTCheckType, 
b.RPTFeedbackType,
b.RPTSendType, 
b.XB, 
b.ZJHM,
b.ZJLX, 
b.extend1, 
b. extend2, 
b. extend3, 
b.extend4, 
b.extend5, 
b.FOREIGNID
 FROM QY_JKRZBGC_JC as a,QY_GJGLRQK as b where a.autoID = b.foreignid;
else
SELECT 
  a.dtDate,
a.instInfo,
b.autoID, 
b.GGRYCSRQ,
b.GGRYGZJL, 
b.GGRYLB,
b.GGRYXM, 
b.GGRYZGXL, 
b.RPTCheckType, 
b.RPTFeedbackType,
b.RPTSendType, 
b.XB, 
b.ZJHM,
b.ZJLX, 
b.extend1, 
b. extend2, 
b. extend3, 
b.extend4, 
b.extend5, 
b.FOREIGNID
 FROM QY_JKRZBGC_JC as a,QY_GJGLRQK as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;