CREATE procedure [dbo].[Proc_Check_QY_SYDWZCFZB]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT  a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BCJF
      , b.BCZK
      , b.BRZK
      , b.CBJF
      , b.CCP
      , b.CL
      , b.CZBZSR
      , b.DFSDWBZ
      , b.DWTZ
      , b.FSDWJK
      , b.FZBLZJ
      , b.FZHJ
      , b.GDJJ
      , b.GDZC
      , b.JJKX
      , b.JYJY
      , b.JYSR
      , b.JYZC
      , b.JZCHJ
      , b.JZZCJJ
      , b.QTSR
      , b.QTYFK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJBZSR
      , b.SJSJZC
      , b.SRHJ
      , b.SYJJ
      , b.SYJY
      , b.SYSR
      , b.SYZC
      , b.TZJJ
      , b.WXZC
      , b.XJ
      , b.XSSJ
      , b.YBJJ
      , b.YFPJ
      , b.YFZK
      , b.YFZK_1
      , b.YHCK
      , b.YJCZZHK
      , b.YJSJ
      , b.YJYFK
      , b.YSPJ
      , b.YSZK
      , b.YSZK_1
      , b.ZCBLZJ
      , b.ZCHJ
      , b.ZCHJ_1
      , b.ZKZC
      , b.ZYJJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_SYDWZCFZB as b
  WHERE a.autoID = b.foreignid;

else
SELECT  a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BCJF
      , b.BCZK
      , b.BRZK
      , b.CBJF
      , b.CCP
      , b.CL
      , b.CZBZSR
      , b.DFSDWBZ
      , b.DWTZ
      , b.FSDWJK
      , b.FZBLZJ
      , b.FZHJ
      , b.GDJJ
      , b.GDZC
      , b.JJKX
      , b.JYJY
      , b.JYSR
      , b.JYZC
      , b.JZCHJ
      , b.JZZCJJ
      , b.QTSR
      , b.QTYFK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJBZSR
      , b.SJSJZC
      , b.SRHJ
      , b.SYJJ
      , b.SYJY
      , b.SYSR
      , b.SYZC
      , b.TZJJ
      , b.WXZC
      , b.XJ
      , b.XSSJ
      , b.YBJJ
      , b.YFPJ
      , b.YFZK
      , b.YFZK_1
      , b.YHCK
      , b.YJCZZHK
      , b.YJSJ
      , b.YJYFK
      , b.YSPJ
      , b.YSZK
      , b.YSZK_1
      , b.ZCBLZJ
      , b.ZCHJ
      , b.ZCHJ_1
      , b.ZKZC
      , b.ZYJJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_SYDWZCFZB as b
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
