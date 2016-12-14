CREATE procedure [dbo].[Proc_Check_QY_07LRJLRFPB]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.CWFY
      , b.DLYQYHHYQYDTZSY
      , b.FLDZCSS
      , b.GLFY
      , b.GYJZBDJSY
      , b.JBMGSY
      , b.JLR
      , b.LRZE
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SDSFY
      , b.TZJSY
      , b.XSFY
      , b.XSMGSY
      , b.YYCB
      , b.YYLR
      , b.YYSJJFJ
      , b.YYSR
      , b.YYWSR
      , b.YYWZC
      , b.ZCJZSS
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_07LRJLRFPB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.CWFY
      , b.DLYQYHHYQYDTZSY
      , b.FLDZCSS
      , b.GLFY
      , b.GYJZBDJSY
      , b.JBMGSY
      , b.JLR
      , b.LRZE
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SDSFY
      , b.TZJSY
      , b.XSFY
      , b.XSMGSY
      , b.YYCB
      , b.YYLR
      , b.YYSJJFJ
      , b.YYSR
      , b.YYWSR
      , b.YYWZC
      , b.ZCJZSS
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_07LRJLRFPB as b
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
