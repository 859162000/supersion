CREATE procedure [dbo].[Proc_Check_QY_SYDWSRZCB]
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
      , b.BRZKXJ
      , b.CZBZSR
      , b.CZBZZC
      , b.DFSDWBZ
      , b.FSDWJK
      , b.JYFP
      , b.JYJY
      , b.JYSR
      , b.JYSRXJ
      , b.JYZC
      , b.JYZCXJ
      , b.JZZCJJ
      , b.QTJYFP
      , b.QTSR
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SHYQNDSYZC
      , b.SJBZSR
      , b.SJSJZC
      , b.SRZJ
      , b.SYJY
      , b.SYSR
      , b.SYSRXJ
      , b.SYZC
      , b.SYZCXJ
      , b.TQZYJJ
      , b.XSSJ_1
      , b.XSSJ_2
      , b.YJSDS
      , b.YQNDJYKS
      , b.YSWZJSR
      , b.YSWZJZC
      , b.ZCSRJY
      , b.ZCZJ
      , b.ZKXJ
      , b.ZKZC
      , b.ZRSYJJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID   
  FROM QY_JKRCWBB_JC as a, QY_SYDWSRZCB as b
  WHERE a.autoID = b.foreignid;

else
SELECT  a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BCJF
      , b.BCZK
      , b.BRZK
      , b.BRZKXJ
      , b.CZBZSR
      , b.CZBZZC
      , b.DFSDWBZ
      , b.FSDWJK
      , b.JYFP
      , b.JYJY
      , b.JYSR
      , b.JYSRXJ
      , b.JYZC
      , b.JYZCXJ
      , b.JZZCJJ
      , b.QTJYFP
      , b.QTSR
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SHYQNDSYZC
      , b.SJBZSR
      , b.SJSJZC
      , b.SRZJ
      , b.SYJY
      , b.SYSR
      , b.SYSRXJ
      , b.SYZC
      , b.SYZCXJ
      , b.TQZYJJ
      , b.XSSJ_1
      , b.XSSJ_2
      , b.YJSDS
      , b.YQNDJYKS
      , b.YSWZJSR
      , b.YSWZJZC
      , b.ZCSRJY
      , b.ZCZJ
      , b.ZKXJ
      , b.ZKZC
      , b.ZRSYJJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID   
  FROM QY_JKRCWBB_JC as a, QY_SYDWSRZCB as b
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
