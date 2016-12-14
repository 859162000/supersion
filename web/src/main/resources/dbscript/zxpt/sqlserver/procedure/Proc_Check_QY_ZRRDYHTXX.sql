CREATE procedure [dbo].[Proc_Check_QY_ZRRDYHTXX]
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
      ,b.BZ_1
      ,b.BZ_2
      ,b.DJJG
      ,b.DJRQ
      ,b.DYHTBH
      ,b.DYJE
      ,b.DYRMC
      ,b.DYWPGJZ
      ,b.DYWSM
      ,b.DYWZL
      ,b.DYXH
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.PGJGMC
      ,b.PGJGZZJGDM
      ,b.PGRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZRRDYHTXX as b where a.autoID = b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ_1
      ,b.BZ_2
      ,b.DJJG
      ,b.DJRQ
      ,b.DYHTBH
      ,b.DYJE
      ,b.DYRMC
      ,b.DYWPGJZ
      ,b.DYWSM
      ,b.DYWZL
      ,b.DYXH
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.PGJGMC
      ,b.PGJGZZJGDM
      ,b.PGRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZRRDYHTXX as b where a.dtDate = @dtDate and a.instInfo = @strInstCode and 
  a.autoID = b.foreignid;
