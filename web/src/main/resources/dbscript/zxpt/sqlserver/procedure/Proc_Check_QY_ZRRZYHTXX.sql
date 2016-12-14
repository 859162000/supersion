--自然人质押合同信息段
CREATE procedure [dbo].[Proc_Check_QY_ZRRZYHTXX]
(
	@dtDate varchar(10), 
	@strInstCode varchar(20)
)
as
if @dtDate='*****' and @strInstCode='*****'
 select
    a.dtdate,
    a.instinfo,
   b.autoID
      ,b.BZ_1
      ,b.BZ_2
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_DBXX_JC a,QY_ZRRZYHTXX b
  where a.autoID = b.foreignid;
else
  select
    a.dtdate,
    a.instinfo,
   b.autoID
      ,b.BZ_1
      ,b.BZ_2
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_DBXX_JC a,QY_ZRRZYHTXX b
  where dtDate=@dtDate and instInfo=@strInstCode and a.autoID=b.foreignid;
