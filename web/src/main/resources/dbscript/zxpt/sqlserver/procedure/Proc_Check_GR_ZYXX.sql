CREATE procedure [dbo].[Proc_Check_GR_ZYXX]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
    ,a.instInfo
    ,b.autoID
    , b.BDWGZQSNF
    , b.DWDZ
    , b.DWDZYZBM
    , b.DWMC
    , b.DWSSHY
    , b.GZZH
    , b.GZZHKHYH
    , b.NSR
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.ZC
    , b.ZW
    , b.ZY
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM   GR_ZYXX as b, GR_GRXX_JC as a
 WHERE a.autoID=b.foreignid ;

else
SELECT a.dtDate
    ,a.instInfo
    ,b.autoID
    , b.BDWGZQSNF
    , b.DWDZ
    , b.DWDZYZBM
    , b.DWMC
    , b.DWSSHY
    , b.GZZH
    , b.GZZHKHYH
    , b.NSR
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.ZC
    , b.ZW
    , b.ZY
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM   GR_ZYXX as b, GR_GRXX_JC as a
 WHERE a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
