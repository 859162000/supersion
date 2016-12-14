CREATE procedure [dbo].[Proc_Check_QY_XYZYWXX]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.BZ
      , b.BZJBL
      , b.DBBZ
      , b.DKBZ
      , b.DKKBM
      , b.JKRMC
      , b.KZJE
      , b.KZRQ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.WJFL
      , b.XYZFKQX
      , b.XYZYE
      , b.XYZYXQ
      , b.XYZZT
      , b.XYZZXRQ
      , b.YEBGRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_XYZYW_JC as a, QY_XYZYWXX as b
  WHERE a.autoID = b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.BZ
      , b.BZJBL
      , b.DBBZ
      , b.DKBZ
      , b.DKKBM
      , b.JKRMC
      , b.KZJE
      , b.KZRQ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.WJFL
      , b.XYZFKQX
      , b.XYZYE
      , b.XYZYXQ
      , b.XYZZT
      , b.XYZZXRQ
      , b.YEBGRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_XYZYW_JC as a, QY_XYZYWXX as b
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
