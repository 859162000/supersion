CREATE procedure [dbo].[Proc_Check_GR_GRSFXX]
(
	@dtDate varchar(10),
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
SELECT a.dtDate
		,a.instInfo
	   ,b.autoID
      , b.CSRQ
      , b.DWDH
      , b.DZYX
      , b.HJDZ
      , b.HYZK
      , b.POGZDW
      , b.POLXDH
      , b.POXM
      , b.POZJHM
      , b.POZJLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJHM
      , b.TXDZ
      , b.TXDZYZBM
      , b.XB
      , b.ZGXL
      , b.ZGXW
      , b.ZZDH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM  GR_GRXX_JC as a, GR_GRSFXX as b where a.autoID=b.foreignid;
else
SELECT a.dtDate
		,a.instInfo
	   ,b.autoID
      , b.CSRQ
      , b.DWDH
      , b.DZYX
      , b.HJDZ
      , b.HYZK
      , b.POGZDW
      , b.POLXDH
      , b.POXM
      , b.POZJHM
      , b.POZJLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJHM
      , b.TXDZ
      , b.TXDZYZBM
      , b.XB
      , b.ZGXL
      , b.ZGXW
      , b.ZZDH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM  GR_GRXX_JC as a, GR_GRSFXX as b where a.dtDate=@dtDate and a.instInfo=@strInstCode and a.autoID=b.foreignid;
