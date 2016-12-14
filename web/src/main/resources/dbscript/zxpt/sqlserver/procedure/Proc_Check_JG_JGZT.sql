CREATE procedure [dbo].[Proc_Check_JG_JGZT]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.JBHZT
    , b.JGZT
    , b.QTXXGXRQ
    , b.QYGM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.YLZD
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_JGZT as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;

else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.JBHZT
    , b.JGZT
    , b.QTXXGXRQ
    , b.QYGM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.YLZD
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_JGZT as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
