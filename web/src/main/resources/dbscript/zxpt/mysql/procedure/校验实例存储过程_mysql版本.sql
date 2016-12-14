DELIMITER $$
drop procedure if exists Proc_Check_GR_DBXX;$$
drop procedure if exists Proc_Check_GR_GRSFXX;$$
drop procedure if exists Proc_Check_GR_GRXX_JC;$$
drop procedure if exists Proc_Check_GR_JYBSBG;$$
drop procedure if exists Proc_Check_GR_JZDZ;$$
drop procedure if exists Proc_Check_GR_TSJY;$$
drop procedure if exists Proc_Check_GR_ZYXX;$$
drop procedure if exists Proc_Check_JG_GGJZYGXR;$$
drop procedure if exists Proc_Check_JG_JBSX;$$
drop procedure if exists Proc_Check_JG_JGJBXX_JC;$$
drop procedure if exists Proc_Check_JG_JGZT;$$
drop procedure if exists Proc_Check_JG_LLXX;$$
drop procedure if exists Proc_Check_JG_SJJG;$$
drop procedure if exists Proc_Check_JG_ZYGD;$$
drop procedure if exists Proc_Check_JG_ZYGLQY;$$
drop procedure if exists Proc_Check_JZ_JZCYXX_JC;$$
drop procedure if exists Proc_Check_QY_02LRJLRFPB;$$
drop procedure if exists Proc_Check_QY_02XJLLB;$$
drop procedure if exists Proc_Check_QY_02ZCFZB;$$
drop procedure if exists Proc_Check_QY_07LRJLRFPB;$$
drop procedure if exists Proc_Check_QY_07XJLLB;$$
drop procedure if exists Proc_Check_QY_07ZCFZB;$$
drop procedure if exists Proc_Check_QY_BHYWXX;$$
drop procedure if exists Proc_Check_QY_BHYW_BS;$$
drop procedure if exists Proc_Check_QY_BHYW_JC;$$
drop procedure if exists Proc_Check_QY_BLXDZCCL_BS;$$
drop procedure if exists Proc_Check_QY_BLXDZCCL_JC;$$
drop procedure if exists Proc_Check_QY_BLXZXX;$$
drop procedure if exists Proc_Check_QY_BLYW_BS;$$
drop procedure if exists Proc_Check_QY_BLYW_JC;$$
drop procedure if exists Proc_Check_QY_CWBLXFS;$$
drop procedure if exists Proc_Check_QY_CZZBGC;$$
drop procedure if exists Proc_Check_QY_DBHTXX;$$
drop procedure if exists Proc_Check_QY_DBXX_BS;$$
drop procedure if exists Proc_Check_QY_DBXX_JC;$$
drop procedure if exists Proc_Check_QY_DKXX_BS;$$
drop procedure if exists Proc_Check_QY_DKXX_JC;$$
drop procedure if exists Proc_Check_QY_DKYWXX;$$
drop procedure if exists Proc_Check_QY_DKYW_BS;$$
drop procedure if exists Proc_Check_QY_DKYW_JC;$$
drop procedure if exists Proc_Check_QY_DKYW_ZQXX;$$
drop procedure if exists Proc_Check_QY_DSXX;$$
drop procedure if exists Proc_Check_QY_DWTZQK;$$
drop procedure if exists Proc_Check_QY_DYHTXX;$$
drop procedure if exists Proc_Check_QY_FRDBJZQYCY;$$
drop procedure if exists Proc_Check_QY_GKSX_BS;$$
drop procedure if exists Proc_Check_QY_GKSX_JC;$$
drop procedure if exists Proc_Check_QY_GPXX;$$
drop procedure if exists Proc_Check_QY_HKXX;$$
drop procedure if exists Proc_Check_QY_HPYWXX;$$
drop procedure if exists Proc_Check_QY_HTJEXX;$$
drop procedure if exists Proc_Check_QY_HTXX;$$
drop procedure if exists Proc_Check_QY_JJXX;$$
drop procedure if exists Proc_Check_QY_JKRCWBB_BS;$$
drop procedure if exists Proc_Check_QY_JKRCWBB_JC;$$
drop procedure if exists Proc_Check_QY_JKRGKXX;$$
drop procedure if exists Proc_Check_QY_JKRGK_BS;$$
drop procedure if exists Proc_Check_QY_JKRGK_JC;$$
drop procedure if exists Proc_Check_QY_JKRGZ_BS;$$
drop procedure if exists Proc_Check_QY_JKRGZ_JC;$$
drop procedure if exists Proc_Check_QY_JKRZBGC_BS;$$
drop procedure if exists Proc_Check_QY_JKRZBGC_JC;$$
drop procedure if exists Proc_Check_QY_JKRZCZBQK;$$
drop procedure if exists Proc_Check_QY_JTGSXX;$$
drop procedure if exists Proc_Check_QY_MYRZ_BS;$$
drop procedure if exists Proc_Check_QY_MYRZ_JC;$$
drop procedure if exists Proc_Check_QY_MYRZ_ZQXX;$$
drop procedure if exists Proc_Check_QY_PJTX_BS;$$
drop procedure if exists Proc_Check_QY_PJTX_JC;$$
drop procedure if exists Proc_Check_QY_QXXX_BS;$$
drop procedure if exists Proc_Check_QY_QXXX_JC;$$
drop procedure if exists Proc_Check_QY_QXYWXX;$$
drop procedure if exists Proc_Check_QY_RZXYJEXX;$$
drop procedure if exists Proc_Check_QY_RZXYXX;$$
drop procedure if exists Proc_Check_QY_RZYWHKXX;$$
drop procedure if exists Proc_Check_QY_RZYWXX;$$
drop procedure if exists Proc_Check_QY_SSXX;$$
drop procedure if exists Proc_Check_QY_SXYWXX;$$
drop procedure if exists Proc_Check_QY_SYDWSRZCB;$$
drop procedure if exists Proc_Check_QY_SYDWZCFZB;$$
drop procedure if exists Proc_Check_QY_TXYWXX;$$
drop procedure if exists Proc_Check_QY_XYZYWXX;$$
drop procedure if exists Proc_Check_QY_XYZYW_BS;$$
drop procedure if exists Proc_Check_QY_XYZYW_JC;$$
drop procedure if exists Proc_Check_QY_YHCDHP_BS;$$
drop procedure if exists Proc_Check_QY_YHCDHP_JC;$$
drop procedure if exists Proc_Check_QY_ZRRBZHTXX;$$
drop procedure if exists Proc_Check_QY_ZRRDYHTXX;$$
drop procedure if exists Proc_Check_QY_ZRRZYHTXX;$$
drop procedure if exists Proc_Check_QY_ZYHTXX;$$

create procedure Proc_Check_JG_JGJBXX_JC
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DEPTCODE
      ,DJZCH
      ,DJZCHLX
      ,DKKBM
      ,JGXYDM
      ,KHH
      ,KHLX
      ,KHXKZHZH
      ,NSRSBHDS
      ,NSRSBHGS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JG_JGJBXX_JC;
else
SELECT autoID
      ,DEPTCODE
      ,DJZCH
      ,DJZCHLX
      ,DKKBM
      ,JGXYDM
      ,KHH
      ,KHLX
      ,KHXKZHZH
      ,NSRSBHDS
      ,NSRSBHGS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JG_JGJBXX_JC
  WHERE  dtDate=dtDate and instInfo=strInstCode;
END IF;
END$$

create procedure Proc_Check_QY_BHYW_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,BHHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BHYW_JC;
else
SELECT autoID
      ,BHHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BHYW_JC where dtDate=dtDate and instInfo = strInstCode;
  End IF;
END$$

create procedure Proc_Check_JZ_JZCYXX_JC
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JZ_JZCYXX_JC;
else

SELECT autoID
      ,JZCYXM
      ,JZCYZJLX
      ,JZGX
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXGXRQ
      ,XZXH
      ,YLZD
      ,ZJHM_1
      ,ZJHM_2
      ,ZYGXRXM
      ,ZYGXRZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM JZ_JZCYXX_JC
  WHERE  dtDate=dtDate and instInfo=strInstCode;
  END IF;
END$$

create procedure Proc_Check_GR_GRXX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,BYSJHKJE
      ,BYYHKJE
      ,BZ
      ,DBFS
      ,DQRQ
      ,DQYQQS
      ,DQYQZE
      ,FSDD
      ,GXSXED
      ,HKPL
      ,HKYS
      ,HKZT
      ,HKZT_24
      ,JRJGDM
      ,JSYHKRQ
      ,KHRQ
      ,LJYQQS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXED
      ,SYHKYS
      ,WJFLZT
      ,WZFYE
      ,XM
      ,XZXH
      ,YE
      ,YLZD
      ,YQ180YSHDKBJ
      ,YQ31_60HDKBJ
      ,YQ61_90HDKBJ
      ,YQ91_180HDKBJ
      ,YWH
      ,YWZL
      ,YWZLXF
      ,ZDFZE
      ,ZGYQQS
      ,ZHYCSJHKRQ
      ,ZHYYZXXTS
      ,ZHZT
      ,ZJHM
      ,ZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM GR_GRXX_JC;
else
SELECT autoID
      ,BYSJHKJE
      ,BYYHKJE
      ,BZ
      ,DBFS
      ,DQRQ
      ,DQYQQS
      ,DQYQZE
      ,FSDD
      ,GXSXED
      ,HKPL
      ,HKYS
      ,HKZT
      ,HKZT_24
      ,JRJGDM
      ,JSYHKRQ
      ,KHRQ
      ,LJYQQS
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXED
      ,SYHKYS
      ,WJFLZT
      ,WZFYE
      ,XM
      ,XZXH
      ,YE
      ,YLZD
      ,YQ180YSHDKBJ
      ,YQ31_60HDKBJ
      ,YQ61_90HDKBJ
      ,YQ91_180HDKBJ
      ,YWH
      ,YWZL
      ,YWZLXF
      ,ZDFZE
      ,ZGYQQS
      ,ZHYCSJHKRQ
      ,ZHYYZXXTS
      ,ZHZT
      ,ZJHM
      ,ZJLX
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM GR_GRXX_JC where dtDate=dtDate and instInfo=strInstCode;
  END IF;
END$$

create procedure Proc_Check_QY_BLXDZCCL_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,GSZCDJH
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XZXH
      ,YWBH
      ,YWFSRQ
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BLXDZCCL_JC;
else
SELECT autoID
      ,DKKBM
      ,GSZCDJH
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XZXH
      ,YWBH
      ,YWFSRQ
      ,ZZJGDM
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_BLXDZCCL_JC where dtDate=dtDate and instInfo = strInstCode;
  END IF;
END$$

create procedure Proc_Check_QY_DBXX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  
       autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XDYWZL
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,ZHTBH
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_DBXX_JC;
else
 SELECT  
       autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XDYWZL
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,ZHTBH
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_DBXX_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$


create procedure Proc_Check_QY_BLYW_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,BLXYBH
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_BLYW_JC;
else
SELECT autoID
      ,BLXYBH
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_BLYW_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$

create procedure Proc_Check_QY_DKXX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKYWHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKXX_JC;
else
SELECT autoID
      ,DKYWHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKXX_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$

create procedure Proc_Check_QY_GKSX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_GKSX_JC;
else
SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_GKSX_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$

create procedure Proc_Check_QY_JKRCWBB_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,BBLX
      ,BBLXXF
      ,BBNF
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SJRYMC
      ,SJSJ
      ,SJSWSMC
      ,XXJLCZLX
      ,XXJLGZBH
      ,XXJLLX
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_JKRCWBB_JC;
else
SELECT autoID
      ,BBLX
      ,BBLXXF
      ,BBNF
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SJRYMC
      ,SJSJ
      ,SJSWSMC
      ,XXJLCZLX
      ,XXJLGZBH
      ,XXJLLX
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_JKRCWBB_JC
  WHERE dtDate=dtDate and instInfo=strInstCode;
  END IF;
END$$


create procedure Proc_Check_QY_DKYW_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKHTHM
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKYW_JC;
else

SELECT autoID
      ,DKHTHM
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_DKYW_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$


create procedure Proc_Check_QY_JKRGK_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_JKRGK_JC;
else
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
 FROM QY_JKRGK_JC where dtDate=dtDate and instInfo = strInstCode;
 
END IF;
END$$


create procedure Proc_Check_QY_JKRGZ_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_JKRGZ_JC;
else
SELECT autoID
      ,DKKBM
      ,JKRMC
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_JKRGZ_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$


create procedure Proc_Check_QY_JKRZBGC_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_JKRZBGC_JC;
else

SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_JKRZBGC_JC
  WHERE dtDate=dtDate and instInfo=strInstCode;
  END IF;
END$$


create procedure Proc_Check_QY_MYRZ_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,RZXYBH
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_MYRZ_JC;
else

SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,RZXYBH
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_MYRZ_JC
  WHERE dtDate=dtDate and instInfo=strInstCode;
  END IF;
END$$

create procedure Proc_Check_QY_PJTX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,JRJGDM
      ,PJNBBH
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_PJTX_JC;
else
SELECT autoID
      ,JRJGDM
      ,PJNBBH
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_PJTX_JC where dtDate=dtDate and instInfo = strInstCode;
  END IF;
END$$


create procedure Proc_Check_QY_QXXX_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_QXXX_JC;
else
SELECT autoID
      ,DKKBM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser

 FROM QY_QXXX_JC where dtDate=dtDate and instInfo = strInstCode;
 END IF;
END$$

create procedure Proc_Check_QY_XYZYW_JC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XYZHM
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_XYZYW_JC;
else

SELECT autoID
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XYZHM
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_XYZYW_JC
  WHERE dtDate=dtDate and instInfo=strInstCode;
  
END IF;
END$$

create procedure Proc_Check_QY_YHCDHP_JC
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT autoID
      ,CDXYHM
      ,HPHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_YHCDHP_JC;
else

SELECT autoID
      ,CDXYHM
      ,HPHM
      ,JRJGDM
      ,RPTCheckType
      ,RPTFeedbackType
      ,RPTSendType
      ,RPTSubmitStatus
      ,RPTVerifyType
      ,SXXYHM
      ,XXJLCZLX
      ,XXJLGZBH
      ,XZXH
      ,YWFSRQ
      ,dtDate
      ,extend1
      ,extend2
      ,extend3
      ,extend4
      ,extend5
      ,lastUpdateDate
      ,instInfo
      ,operationUser
  FROM QY_YHCDHP_JC
  WHERE  dtDate=dtDate and instInfo=strInstCode;
  
END IF;
END$$

create procedure Proc_Check_QY_YHCDHP_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	a.dtDate
	,a.instInfo
      ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
  FROM  QY_YHCDHP_JC as a,QY_YHCDHP_BS as b 
  WHERE a.autoID=b.foreignid;
else
SELECT 
	a.dtDate
	,a.instInfo
      ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
  FROM  QY_YHCDHP_JC as a,QY_YHCDHP_BS as b 
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_XYZYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
  WHERE a.autoID=b.foreignid;
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
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_ZYHTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CZRDDKKBM
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZYBZ
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWJZBZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZYHTXX as b where a.autoID = b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CZRDDKKBM
      ,b.CZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZYBZ
      ,b.ZYHTBH
      ,b.ZYJE
      ,b.ZYWJZ
      ,b.ZYWJZBZ
      ,b.ZYWZL
      ,b.ZYXH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_ZYHTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$


create procedure Proc_Check_QY_ZRRZYHTXX
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
  WHERE a.autoID=b.foreignid;
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
  where dtDate=dtDate and instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$


create procedure Proc_Check_QY_ZRRDYHTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
 FROM QY_DBXX_JC as a,QY_ZRRDYHTXX as b  WHERE a.autoID=b.foreignid;
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
 FROM QY_DBXX_JC as a,QY_ZRRDYHTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
END IF;
END$$

create procedure Proc_Check_QY_ZRRBZHTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
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
 FROM QY_DBXX_JC as a,QY_ZRRBZHTXX as b  WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
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
 FROM QY_DBXX_JC as a,QY_ZRRBZHTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_XYZYW_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_XYZYW_JC as a, QY_XYZYW_BS as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_XYZYW_JC as a, QY_XYZYW_BS as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_TXYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.CDDQR
      ,b.CDRHDDKKBM
      ,b.CDRHMC
      ,b.DKKBM
      ,b.PJZL
      ,b.PJZT
      ,b.PMJE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.TXJE
      ,b.TXR
      ,b.TXSQRMC
      ,b.WJFL
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_PJTX_JC as a,QY_TXYWXX as b  WHERE a.autoID=b.foreignid;
else
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.CDDQR
      ,b.CDRHDDKKBM
      ,b.CDRHMC
      ,b.DKKBM
      ,b.PJZL
      ,b.PJZT
      ,b.PMJE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.TXJE
      ,b.TXR
      ,b.TXSQRMC
      ,b.WJFL
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_PJTX_JC as a,QY_TXYWXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_SYDWZCFZB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
  WHERE a.autoID=b.foreignid;
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
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_SYDWSRZCB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then

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
  WHERE a.autoID=b.foreignid;
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
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_SXYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	   a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXED
      ,b.SXEDZXSXRQ
      ,b.SXEDZXYY
      ,b.SXSXQSRQ
      ,b.SXZZRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_GKSX_JC as a,QY_SXYWXX as b  WHERE a.autoID=b.foreignid;
else

SELECT
	   a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXED
      ,b.SXEDZXSXRQ
      ,b.SXEDZXYY
      ,b.SXSXQSRQ
      ,b.SXZZRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_GKSX_JC as a,QY_SXYWXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_SSXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BQSJLLSH
      ,b.BQSYY
      ,b.BZ
      ,b.PJZXJE
      ,b.PJZXRQ
      ,b.QSRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZXJG
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_SSXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BQSJLLSH
      ,b.BQSYY
      ,b.BZ
      ,b.PJZXJE
      ,b.PJZXRQ
      ,b.QSRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZXJG
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_SSXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_RZYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZJE
      , b.RZYE
      , b.RZYWBH
      , b.RZYWFSRQ
      , b.RZYWJSRQ
      , b.RZYWZL
      , b.SJFL
      , b.WJFL
      , b.ZQBZ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZYWXX as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZJE
      , b.RZYE
      , b.RZYWBH
      , b.RZYWFSRQ
      , b.RZYWJSRQ
      , b.RZYWZL
      , b.SJFL
      , b.WJFL
      , b.ZQBZ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZYWXX as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_RZYWHKXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.HKCS
      , b.HKFS
      , b.HKJE
      , b.HKRQ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZYWHKXX as b
  WHERE a.autoID=b.foreignid;
else

SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.HKCS
      , b.HKFS
      , b.HKJE
      , b.HKRQ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZYWHKXX as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_RZXYXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.DBBZ
      , b.JKRMC
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SXXYHM
      , b.XYSXRQ
      , b.XYYXZT
      , b.XYZZRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZXYXX as b
  WHERE a.autoID=b.foreignid;
else

SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.DBBZ
      , b.JKRMC
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SXXYHM
      , b.XYSXRQ
      , b.XYYXZT
      , b.XYZZRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZXYXX as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_RZXYJEXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZXYJE
      , b.RZXYYE
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZXYJEXX as b
  WHERE a.autoID=b.foreignid;
else

SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZXYJE
      , b.RZXYYE
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_RZXYJEXX as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_QXYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.QXLX
      ,b.QXYE
      ,b.QXYEGBRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_QXXX_JC as a,QY_QXYWXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.QXLX
      ,b.QXYE
      ,b.QXYEGBRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_QXXX_JC as a,QY_QXYWXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_QXXX_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_QXXX_JC as a,QY_QXXX_BS as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_QXXX_JC as a,QY_QXXX_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_PJTX_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_PJTX_JC as a,QY_PJTX_BS as b WHERE a.autoID=b.foreignid;
else
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_PJTX_JC as a,QY_PJTX_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$


create procedure Proc_Check_QY_MYRZ_ZQXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.ZQCS
      , b.ZQDQRQ
      , b.ZQJE
      , b.ZQQSRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_ZQXX as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZYWBH
      , b.ZQCS
      , b.ZQDQRQ
      , b.ZQJE
      , b.ZQQSRQ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_ZQXX as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_MYRZ_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_BS as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_MYRZ_JC as a, QY_MYRZ_BS as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_JTGSXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJGSDDKKBM
      , b.SJGSMC
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  
    FROM  QY_YHCDHP_JC as a, QY_JTGSXX as b
    WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SJGSDDKKBM
      , b.SJGSMC
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  
    FROM  QY_YHCDHP_JC as a, QY_JTGSXX as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	
END IF;
END$$

create procedure Proc_Check_QY_JKRZCZBQK
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.ZCZJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
    FROM  QY_YHCDHP_JC as a, QY_JKRZCZBQK as b
    WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.ZCZJ
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
    FROM  QY_YHCDHP_JC as a, QY_JKRZCZBQK as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	END IF;
END$$

create procedure Proc_Check_QY_JKRZBGC_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRZBGC_JC as a, QY_JKRZBGC_BS as b
  WHERE a.autoID=b.foreignid;
else

SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRZBGC_JC as a, QY_JKRZBGC_BS as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_JKRGZ_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_JKRGZ_BS as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_JKRGZ_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_JKRGKXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CYRS
      ,b.DJZCH
      ,b.DJZCLX
      ,b.DSDJZHM
      ,b.GSDJZHM
      ,b.HYFL
      ,b.JCKQBZ
      ,b.JKRCLNF
      ,b.JKRCZHM
      ,b.JKREMAILDZ
      ,b.JKRGB
      ,b.JKRLXDH
      ,b.JKRTXDZ
      ,b.JKRTZ
      ,b.JKRWWMC
      ,b.JKRWZ
      ,b.JKRXZ
      ,b.JKRZCDZ
      ,b.JKRZWMC
      ,b.JTKHBZ
      ,b.JYCDMJ
      ,b.JYCDSYQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSGSBZ
      ,b.XZQH
      ,b.YYZZDQRQ
      ,b.YZBM
      ,b.ZCDJRQ
      ,b.ZYCPQK
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_JKRGKXX as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CYRS
      ,b.DJZCH
      ,b.DJZCLX
      ,b.DSDJZHM
      ,b.GSDJZHM
      ,b.HYFL
      ,b.JCKQBZ
      ,b.JKRCLNF
      ,b.JKRCZHM
      ,b.JKREMAILDZ
      ,b.JKRGB
      ,b.JKRLXDH
      ,b.JKRTXDZ
      ,b.JKRTZ
      ,b.JKRWWMC
      ,b.JKRWZ
      ,b.JKRXZ
      ,b.JKRZCDZ
      ,b.JKRZWMC
      ,b.JTKHBZ
      ,b.JYCDMJ
      ,b.JYCDSYQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSGSBZ
      ,b.XZQH
      ,b.YYZZDQRQ
      ,b.YZBM
      ,b.ZCDJRQ
      ,b.ZYCPQK
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_JKRGKXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_JKRGK_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID

 FROM QY_JKRGK_JC as a,QY_JKRGK_BS as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID

 FROM QY_JKRGK_JC as a,QY_JKRGK_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_DKYW_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_DKYW_BS as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_DKYW_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_JKRCWBB_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_JKRCWBB_BS as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BGLX
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.YWBSH
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_JKRCWBB_BS as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_JJXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_JJXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKJJJE
      ,b.DKJJYE
      ,b.DKTX
      ,b.DKXS
      ,b.DKXZ
      ,b.DKYWZL
      ,b.DKZL
      ,b.JJBH
      ,b.JJFKDQR
      ,b.JJFKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_JJXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_HTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DBBZ
      ,b.HTSXRQ
      ,b.HTYXZT
      ,b.HTZZRQ
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXXYHM
      ,b.YTBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTXX as b where a.autoID = b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DBBZ
      ,b.HTSXRQ
      ,b.HTYXZT
      ,b.HTZZRQ
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXXYHM
      ,b.YTBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_HTJEXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKHTJE
      ,b.KYYE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTJEXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.DKHTJE
      ,b.KYYE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HTJEXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_HPYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.BZJBL
      , b.CPRMC
      , b.DBBZ
      , b.DKBZ
      , b.DKKBM
      , b.HPCDR
      , b.HPDQR
      , b.HPFKRQ
      , b.HPJE
      , b.HPZT
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.WJFL
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  
    FROM  QY_YHCDHP_JC as a, QY_HPYWXX as b
    WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.BZJBL
      , b.CPRMC
      , b.DBBZ
      , b.DKBZ
      , b.DKKBM
      , b.HPCDR
      , b.HPDQR
      , b.HPFKRQ
      , b.HPJE
      , b.HPZT
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.WJFL
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  
    FROM  QY_YHCDHP_JC as a, QY_HPYWXX as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	END IF;
END$$

create procedure Proc_Check_QY_HKXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.HKCS
      ,b.HKFS
      ,b.HKJE
      ,b.HKRQ
      ,b.JJBH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HKXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.HKCS
      ,b.HKFS
      ,b.HKJE
      ,b.HKRQ
      ,b.JJBH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_HKXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_GPXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.GPDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_GPXX as b WHERE a.autoID=b.foreignid;
 else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.GPDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_GPXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
END IF;
END$$


create procedure Proc_Check_QY_GKSX_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	   a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_GKSX_JC as a,QY_GKSX_BS as b WHERE a.autoID=b.foreignid;
else

SELECT
	   a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_GKSX_JC as a,QY_GKSX_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_FRDBJZQYCY
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      ,b.JZCYSZQYMC
      ,b.JZCYXM
      ,b.JZGX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SZQYDDKKBM
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
    FROM  QY_JKRZBGC_JC as a, QY_FRDBJZQYCY as b
    WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      ,b.JZCYSZQYMC
      ,b.JZCYXM
      ,b.JZGX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SZQYDDKKBM
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  
    FROM  QY_JKRZBGC_JC as a, QY_FRDBJZQYCY as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	END IF;
END$$

create procedure Proc_Check_QY_DYHTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DJJG
      ,b.DJRQ
      ,b.DYBZ
      ,b.DYHTBM
      ,b.DYJE
      ,b.DYRDKKBM
      ,b.DYRMC
      ,b.DYWPGJZ
      ,b.DYWSM
      ,b.DYWZL
      ,b.DYXH
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.PGBZ
      ,b.PGJGMC
      ,b.PGJGZZJGDM
      ,b.PGRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_DYHTXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DJJG
      ,b.DJRQ
      ,b.DYBZ
      ,b.DYHTBM
      ,b.DYJE
      ,b.DYRDKKBM
      ,b.DYRMC
      ,b.DYWPGJZ
      ,b.DYWSM
      ,b.DYWZL
      ,b.DYXH
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.PGBZ
      ,b.PGJGMC
      ,b.PGJGZZJGDM
      ,b.PGRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_DYHTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_DWTZQK
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BTZDWDKKBM
      , b.BTZDWMC
      , b.BZ1
      , b.BZ2
      , b.BZ3
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.TZJE1
      , b.TZJE2
      , b.TZJE3
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
 
    FROM  QY_YHCDHP_JC as a, QY_DWTZQK as b
    WHERE a.autoID=b.foreignid;
else

SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BTZDWDKKBM
      , b.BTZDWMC
      , b.BZ1
      , b.BZ2
      , b.BZ3
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.TZJE1
      , b.TZJE2
      , b.TZJE3
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
 
    FROM  QY_YHCDHP_JC as a, QY_DWTZQK as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	END IF;
END$$

create procedure Proc_Check_QY_DSXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DSMS
      ,b.DSXXJLLSH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_DSXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.DSMS
      ,b.DSXXJLLSH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGZ_JC as a,QY_DSXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_DKYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	  a.dtDate
      ,a.instInfo  
	  ,b.autoID
      ,b.BZ
      ,b.DKJE
      ,b.DKKBM
      ,b.DKRQ
      ,b.DKYE
      ,b.DKZL
      ,b.HKFS
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.YEFSRQ
      ,b.YYWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKYWXX as b WHERE a.autoID=b.foreignid;
else

SELECT
	  a.dtDate
      ,a.instInfo  
	  ,b.autoID
      ,b.BZ
      ,b.DKJE
      ,b.DKKBM
      ,b.DKRQ
      ,b.DKYE
      ,b.DKZL
      ,b.HKFS
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.YEFSRQ
      ,b.YYWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKYWXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_DKYW_ZQXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.JJBH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZQCS
      ,b.ZQDQRQ
      ,b.ZQJE
      ,b.ZQQSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_DKYW_ZQXX as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.JJBH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZQCS
      ,b.ZQDQRQ
      ,b.ZQJE
      ,b.ZQQSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKYW_JC as a,QY_DKYW_ZQXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_DKXX_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	  a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKXX_BS as b WHERE a.autoID=b.foreignid;
else
SELECT
	  a.dtDate
      ,a.instInfo 
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DKXX_JC as a,QY_DKXX_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_BLYW_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLYW_BS as b WHERE a.autoID=b.foreignid;
else
	SELECT
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLYW_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_QY_BLXZXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BLCPLX
      ,b.BLYWZT
      ,b.BZ
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.XZJE
      ,b.XZRQ
      ,b.XZYE
      ,b.YEBHRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLXZXX as b WHERE a.autoID=b.foreignid;
else
	SELECT
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BLCPLX
      ,b.BLYWZT
      ,b.BZ
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJFL
      ,b.WJFL
      ,b.XZJE
      ,b.XZRQ
      ,b.XZYE
      ,b.YEBHRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_BLYW_JC as a,QY_BLXZXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$


create procedure Proc_Check_QY_DBXX_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID

 FROM QY_DBXX_JC as a,QY_DBXX_BS as b WHERE a.autoID=b.foreignid;
else
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID

 FROM QY_DBXX_JC as a,QY_DBXX_BS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_DBHTXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRDDKKBM
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_DBHTXX as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BZ
      ,b.BZDBXS
      ,b.BZHTBH
      ,b.BZJE
      ,b.BZRDDKKBM
      ,b.BZRMC
      ,b.HTQDRQ
      ,b.HTYXZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_DBXX_JC as a,QY_DBHTXX as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$


create procedure Proc_Check_QY_CZZBGC
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.CZF
      , b.CZFDKKBM
      , b.CZJE
      , b.DJZCH
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.ZJHM
      , b.ZJLX
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
    FROM  QY_YHCDHP_JC as a, QY_CZZBGC as b
    WHERE a.autoID=b.foreignid;
else

SELECT a.dtDate
	  , a.instInfo
      , b.autoID
      , b.BZ
      , b.CZF
      , b.CZFDKKBM
      , b.CZJE
      , b.DJZCH
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.ZJHM
      , b.ZJLX
      , b.ZZJGDM
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
    FROM  QY_YHCDHP_JC as a, QY_CZZBGC as b
    WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
	END IF;
END$$

create procedure Proc_Check_QY_CWBLXFS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CWBLXFS
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_CWBLXFS as b WHERE a.autoID=b.foreignid;
else

SELECT 
	   a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.CWBLXFS
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
 FROM QY_JKRGK_JC as a,QY_CWBLXFS as b where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_BLXDZCCL_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
    a.dtDate
    ,a.instInfo
    ,b.autoID
    ,b.BGLX
    ,b.RPTCheckType
    ,b.RPTFeedbackType
    ,b.RPTSendType
    ,b.YWBSH
    ,b.extend1
    ,b.extend2
    ,b.extend3
    ,b.extend4
    ,b.extend5
    ,b.FOREIGNID
  FROM QY_BLXDZCCL_JC as a,QY_BLXDZCCL_BS as b WHERE a.autoID=b.foreignid;
  
else
SELECT 
    a.dtDate
    ,a.instInfo
    ,b.autoID
    ,b.BGLX
    ,b.RPTCheckType
    ,b.RPTFeedbackType
    ,b.RPTSendType
    ,b.YWBSH
    ,b.extend1
    ,b.extend2
    ,b.extend3
    ,b.extend4
    ,b.extend5
    ,b.FOREIGNID
  FROM QY_BLXDZCCL_JC as a,QY_BLXDZCCL_BS as b where a.dtDate = dtDate and 
a.instInfo = strInstCode and 
  a.autoID = b.FOREIGNID;
  
END IF;
END$$

create procedure Proc_Check_QY_BHYWXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
       a.dtDate
      ,a.instInfo
      ,b.autoID
      ,b.BHDQR
      ,b.BHJE
      ,b.BHKLRQ
      ,b.BHYE
      ,b.BHZL
      ,b.BHZT
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.YEFSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID  
FROM QY_BHYW_JC as a,QY_BHYWXX as b  WHERE a.autoID=b.foreignid;
else

SELECT 
       a.dtDate
      ,a.instInfo
      ,b.autoID
      ,b.BHDQR
      ,b.BHJE
      ,b.BHKLRQ
      ,b.BHYE
      ,b.BHZL
      ,b.BHZT
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.YEFSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID  
FROM QY_BHYW_JC as a,QY_BHYWXX as b  where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.FOREIGNID;
  END IF;
END$$

create procedure Proc_Check_GR_GRSFXX
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
		a.dtDate
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
  FROM  GR_GRXX_JC as a, GR_GRSFXX as b 
  WHERE a.autoID=b.foreignid;
else

SELECT 
		a.dtDate
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
  FROM  GR_GRXX_JC as a, GR_GRSFXX as b where a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
END IF;
END$$

create procedure Proc_Check_GR_DBXX
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.DBJE
    , b.DBZT
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XM
    , b.ZJHM
    , b.ZJLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

 FROM GR_DBXX as b, GR_GRXX_JC as a
 WHERE a.autoID=b.foreignid;
else

SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.DBJE
    , b.DBZT
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XM
    , b.ZJHM
    , b.ZJLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

 FROM GR_DBXX as b, GR_GRXX_JC as a
 WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
 END IF;
END$$

create procedure Proc_Check_JG_ZYGLQY
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.DJZCHM
    , b.DJZCHMLX
    , b.GLLX
    , b.GLQYMC
    , b.JGXYDM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XXGXRQ
    , b.YLZD
    , b.ZZJGDM
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  
  FROM   JG_ZYGLQY as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.DJZCHM
    , b.DJZCHMLX
    , b.GLLX
    , b.GLQYMC
    , b.JGXYDM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XXGXRQ
    , b.YLZD
    , b.ZZJGDM
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  
  FROM   JG_ZYGLQY as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_JG_ZYGD
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CURRENCY
    , b.GDCZJE
    , b.GDLX
    , b.GDMC
    , b.GDXXGXRQ
    , b.GDZJHM
    , b.GDZJLX
    , b.GDZZJGDM
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
  FROM   JG_ZYGD as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CURRENCY
    , b.GDCZJE
    , b.GDLX
    , b.GDMC
    , b.GDXXGXRQ
    , b.GDZJHM
    , b.GDZJLX
    , b.GDZZJGDM
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
  FROM   JG_ZYGD as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_JG_SJJG
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.JGXYDM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.SJJGDJZCHLX
    , b.SJJGDJZCHM
    , b.SJJGMC
    , b.SJJGXXGXRQ
    , b.SJJGZZJGDM
    , b.YLZD
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_SJJG as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.JGXYDM
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.SJJGDJZCHLX
    , b.SJJGDJZCHM
    , b.SJJGMC
    , b.SJJGXXGXRQ
    , b.SJJGZZJGDM
    , b.YLZD
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_SJJG as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$


create procedure Proc_Check_JG_LLXX
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.BGDZ
    , b.CWBLXDH
    , b.LLXXGXRQ
    , b.LXDH
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

  FROM   JG_LLXX as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else

SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.BGDZ
    , b.CWBLXDH
    , b.LLXXGXRQ
    , b.LXDH
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

  FROM   JG_LLXX as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_JG_JGZT
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_BHYW_BS
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_BHYW_JC as a,QY_BHYW_BS as b  WHERE a.autoID=b.foreignid;
else
SELECT 
       a.dtDate
      ,a.instInfo
	  ,b.autoID
      ,b.BGLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWBSH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  FROM QY_BHYW_JC as a,QY_BHYW_BS as b  where a.dtDate = dtDate and a.instInfo = strInstCode and 
  a.autoID = b.FOREIGNID;
  END IF;
END$$

create procedure Proc_Check_QY_07ZCFZB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CH
      , b.CQDTFY
      , b.CQGQTZ
      , b.CQJK
      , b.CQYFK
      , b.CQYSK
      , b.CYZDQTZ
      , b.DQJK
      , b.DYSDSFZ
      , b.DYSDSZC
      , b.FLDFZHJ
      , b.FLDZCHJ
      , b.FZHJ
      , b.FZHSYZQYHJ
      , b.GCWZ
      , b.GDZC
      , b.GDZCQL
      , b.HBZJ
      , b.JKCG
      , b.JYXJRFZ
      , b.JYXJRZC
      , b.KFZC
      , b.KGCSDJRZC
      , b.LDFZHJ
      , b.LDZCHJ
      , b.QTFLDFZ
      , b.QTFLDZC
      , b.QTLDFZ
      , b.QTLDZC
      , b.QTYFK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SCXSWZC
      , b.SSZBHGB
      , b.SY
      , b.SYZQYHJ
      , b.TZXFDC
      , b.WFPLR
      , b.WXZC
      , b.YFGL
      , b.YFLX
      , b.YFPJ
      , b.YFZGXZ
      , b.YFZK_1
      , b.YFZK_2
      , b.YFZQ
      , b.YJFZ
      , b.YJSF
      , b.YNNDQDFLDFZ
      , b.YNNDQDFLDZC
      , b.YQZC
      , b.YSGL
      , b.YSLX
      , b.YSPJ
      , b.YSZK
      , b.YSZK_1
      , b.YYGJ
      , b.ZBGJ
      , b.ZCZJ
      , b.ZJGC
      , b.ZXYFK
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_07ZCFZB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CH
      , b.CQDTFY
      , b.CQGQTZ
      , b.CQJK
      , b.CQYFK
      , b.CQYSK
      , b.CYZDQTZ
      , b.DQJK
      , b.DYSDSFZ
      , b.DYSDSZC
      , b.FLDFZHJ
      , b.FLDZCHJ
      , b.FZHJ
      , b.FZHSYZQYHJ
      , b.GCWZ
      , b.GDZC
      , b.GDZCQL
      , b.HBZJ
      , b.JKCG
      , b.JYXJRFZ
      , b.JYXJRZC
      , b.KFZC
      , b.KGCSDJRZC
      , b.LDFZHJ
      , b.LDZCHJ
      , b.QTFLDFZ
      , b.QTFLDZC
      , b.QTLDFZ
      , b.QTLDZC
      , b.QTYFK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SCXSWZC
      , b.SSZBHGB
      , b.SY
      , b.SYZQYHJ
      , b.TZXFDC
      , b.WFPLR
      , b.WXZC
      , b.YFGL
      , b.YFLX
      , b.YFPJ
      , b.YFZGXZ
      , b.YFZK_1
      , b.YFZK_2
      , b.YFZQ
      , b.YJFZ
      , b.YJSF
      , b.YNNDQDFLDFZ
      , b.YNNDQDFLDZC
      , b.YQZC
      , b.YSGL
      , b.YSLX
      , b.YSPJ
      , b.YSZK
      , b.YSZK_1
      , b.YYGJ
      , b.ZBGJ
      , b.ZCZJ
      , b.ZJGC
      , b.ZXYFK
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_07ZCFZB as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_07XJLLB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.CHZWSZFDXJ
      , b.CQDTFYTX
      , b.CWFY_1
      , b.CZGDZCWXZCHQTCQZCDSS
      , b.CZGDZCWXZCHQTCQZCSSHDXJJE
      , b.CZHDCSDXJLLJE
      , b.CZHDXJLRXJ
      , b.CZHDXJLRXJ_1
      , b.CZZGSJQTYYDWSDDXJJE
      , b.DTFYJS
      , b.DYSDSFZZJ
      , b.DYSDSZCJS
      , b.FPGLLRHCFLXSZFDXJ
      , b.GDZCBFSS
      , b.GDZCZJYQZCZHSCXSWZCZJ
      , b.GMGDZCWXZCHQTCQZCSZFDXJ
      , b.GMSPJSLWZFDXJ
      , b.GYJZBDSS
      , b.JLR_1
      , b.JYHDCSDXJLLJE
      , b.JYHDCSDXJLLJE_1
      , b.JYHDXJLCXJ
      , b.JYHDXJLRXJ
      , b.JYXYFXMDZJ
      , b.JYXYSXMDJS
      , b.QCXJJXJDJWYE
      , b.QDJKSDDXJ
      , b.QDTZSYSSDDXJ
      , b.QDZGSJQTSYDWZFDXJJE
      , b.QMXJJXJDJWYEL
      , b.QT
      , b.QT_1
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZZRGDZC
      , b.SDDSFFH
      , b.SDQTYCZHDYGDXJ
      , b.SDQTYJYHDYGDXJ
      , b.SDQTYTZHDYGDXJ
      , b.SHTZSSDDXJ
      , b.SLBDDXJJXJDJWDYX
      , b.TZHDCSDXJLLJE
      , b.TZHDXJLRXJ
      , b.TZHDXJLRXJ_1
      , b.TZSS
      , b.TZSZFDXJ
      , b.WXZCTX
      , b.XJDJWDQCYE
      , b.XJDJWDQMYE
      , b.XJDQCYE
      , b.XJDQMYE
      , b.XJJXJDJWJZEW
      , b.XJJXJDJWJZZJE
      , b.XSCPHTGLWSDDXJ
      , b.XSTZSDDXJ
      , b.YNNDQDKZHGSZQ
      , b.YTFYZJ
      , b.ZCJZZB
      , b.ZFDGXSF
      , b.ZFGZGYJWZGZFDXJ
      , b.ZFQTYCZHDYGDXJ
      , b.ZFQTYJYHDYGDXJ
      , b.ZFQTYTZHDYGDXJ
      , b.ZHDJS
      , b.ZWZWZB
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID    
  FROM QY_JKRCWBB_JC as a, QY_07XJLLB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
      , b.autoID
      , b.CHZWSZFDXJ
      , b.CQDTFYTX
      , b.CWFY_1
      , b.CZGDZCWXZCHQTCQZCDSS
      , b.CZGDZCWXZCHQTCQZCSSHDXJJE
      , b.CZHDCSDXJLLJE
      , b.CZHDXJLRXJ
      , b.CZHDXJLRXJ_1
      , b.CZZGSJQTYYDWSDDXJJE
      , b.DTFYJS
      , b.DYSDSFZZJ
      , b.DYSDSZCJS
      , b.FPGLLRHCFLXSZFDXJ
      , b.GDZCBFSS
      , b.GDZCZJYQZCZHSCXSWZCZJ
      , b.GMGDZCWXZCHQTCQZCSZFDXJ
      , b.GMSPJSLWZFDXJ
      , b.GYJZBDSS
      , b.JLR_1
      , b.JYHDCSDXJLLJE
      , b.JYHDCSDXJLLJE_1
      , b.JYHDXJLCXJ
      , b.JYHDXJLRXJ
      , b.JYXYFXMDZJ
      , b.JYXYSXMDJS
      , b.QCXJJXJDJWYE
      , b.QDJKSDDXJ
      , b.QDTZSYSSDDXJ
      , b.QDZGSJQTSYDWZFDXJJE
      , b.QMXJJXJDJWYEL
      , b.QT
      , b.QT_1
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZZRGDZC
      , b.SDDSFFH
      , b.SDQTYCZHDYGDXJ
      , b.SDQTYJYHDYGDXJ
      , b.SDQTYTZHDYGDXJ
      , b.SHTZSSDDXJ
      , b.SLBDDXJJXJDJWDYX
      , b.TZHDCSDXJLLJE
      , b.TZHDXJLRXJ
      , b.TZHDXJLRXJ_1
      , b.TZSS
      , b.TZSZFDXJ
      , b.WXZCTX
      , b.XJDJWDQCYE
      , b.XJDJWDQMYE
      , b.XJDQCYE
      , b.XJDQMYE
      , b.XJJXJDJWJZEW
      , b.XJJXJDJWJZZJE
      , b.XSCPHTGLWSDDXJ
      , b.XSTZSDDXJ
      , b.YNNDQDKZHGSZQ
      , b.YTFYZJ
      , b.ZCJZZB
      , b.ZFDGXSF
      , b.ZFGZGYJWZGZFDXJ
      , b.ZFQTYCZHDYGDXJ
      , b.ZFQTYJYHDYGDXJ
      , b.ZFQTYTZHDYGDXJ
      , b.ZHDJS
      , b.ZWZWZB
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID    
  FROM QY_JKRCWBB_JC as a, QY_07XJLLB as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_07LRJLRFPB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_02ZCFZB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CH
      , b.CHCCP
      , b.CHYCL
      , b.CQFZHJ
      , b.CQGQTZ
      , b.CQJK
      , b.CQTZ
      , b.CQTZHJ
      , b.CQYFK
      , b.CQZQTZ
      , b.DCLGDZCJSS
      , b.DCLLDZCJSS
      , b.DQJK
      , b.DQTZ
      , b.DTFY
      , b.DYSKDX
      , b.DYSKJX
      , b.DYZC
      , b.DYZCGD9577
      , b.DYZCGDZCXL
      , b.FRZB
      , b.FRZBGJFRZB
      , b.FRZBJTFRZB
      , b.FZHJ
      , b.FZHSYZQYHJ
      , b.GCWZ
      , b.GDZCHJ
      , b.GDZCJE
      , b.GDZCJZ
      , b.GDZCQL
      , b.GDZCYJ
      , b.GDZCZJZZB
      , b.GJZB
      , b.GRZB
      , b.HBJC
      , b.HBZJ
      , b.JTZB
      , b.LDFZHJ
      , b.LDZCHJ
      , b.LJZJ
      , b.QHBZJ
      , b.QTCQFZ
      , b.QTCQFZ9629
      , b.QTCQZC
      , b.QTCQZC9581
      , b.QTLDFZ
      , b.QTLDZC
      , b.QTYFK
      , b.QTYJK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SSGDQY
      , b.SSZB
      , b.SYZQYHJ
      , b.WBBBZSCE
      , b.WFPLR
      , b.WQRDTZSS
      , b.WSZB
      , b.WXJQTZCHJ
      , b.WXZC
      , b.WXZCTDSYQ
      , b.YFFLF
      , b.YFGZ
      , b.YFJJ
      , b.YFLR
      , b.YFPJ
      , b.YFSJ
      , b.YFZK
      , b.YIFZK
      , b.YJFZ
      , b.YNNDQ9533
      , b.YNNDQDCQFZ
      , b.YSBTK
      , b.YSCKTS
      , b.YSGL
      , b.YSLX
      , b.YSPJ
      , b.YSZK
      , b.YTFY
      , b.YUSZK
      , b.YYGJ
      , b.YYGJBCLDZB
      , b.YYGJFDYYGJ
      , b.YYGJGJJ
      , b.ZBGJ
      , b.ZCZJ
      , b.ZJGC
      , b.ZXYFK
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02ZCFZB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CH
      , b.CHCCP
      , b.CHYCL
      , b.CQFZHJ
      , b.CQGQTZ
      , b.CQJK
      , b.CQTZ
      , b.CQTZHJ
      , b.CQYFK
      , b.CQZQTZ
      , b.DCLGDZCJSS
      , b.DCLLDZCJSS
      , b.DQJK
      , b.DQTZ
      , b.DTFY
      , b.DYSKDX
      , b.DYSKJX
      , b.DYZC
      , b.DYZCGD9577
      , b.DYZCGDZCXL
      , b.FRZB
      , b.FRZBGJFRZB
      , b.FRZBJTFRZB
      , b.FZHJ
      , b.FZHSYZQYHJ
      , b.GCWZ
      , b.GDZCHJ
      , b.GDZCJE
      , b.GDZCJZ
      , b.GDZCQL
      , b.GDZCYJ
      , b.GDZCZJZZB
      , b.GJZB
      , b.GRZB
      , b.HBJC
      , b.HBZJ
      , b.JTZB
      , b.LDFZHJ
      , b.LDZCHJ
      , b.LJZJ
      , b.QHBZJ
      , b.QTCQFZ
      , b.QTCQFZ9629
      , b.QTCQZC
      , b.QTCQZC9581
      , b.QTLDFZ
      , b.QTLDZC
      , b.QTYFK
      , b.QTYJK
      , b.QTYSK
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SSGDQY
      , b.SSZB
      , b.SYZQYHJ
      , b.WBBBZSCE
      , b.WFPLR
      , b.WQRDTZSS
      , b.WSZB
      , b.WXJQTZCHJ
      , b.WXZC
      , b.WXZCTDSYQ
      , b.YFFLF
      , b.YFGZ
      , b.YFJJ
      , b.YFLR
      , b.YFPJ
      , b.YFSJ
      , b.YFZK
      , b.YIFZK
      , b.YJFZ
      , b.YNNDQ9533
      , b.YNNDQDCQFZ
      , b.YSBTK
      , b.YSCKTS
      , b.YSGL
      , b.YSLX
      , b.YSPJ
      , b.YSZK
      , b.YTFY
      , b.YUSZK
      , b.YYGJ
      , b.YYGJBCLDZB
      , b.YYGJFDYYGJ
      , b.YYGJGJJ
      , b.ZBGJ
      , b.ZCZJ
      , b.ZJGC
      , b.ZXYFK
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02ZCFZB as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_02XJLLB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CHDJS
      , b.CHZWSZFDXJ
      , b.CQDTFYTX
      , b.CWFY
      , b.CZGDZC9819
      , b.CZGDZC9871
      , b.CZHDCS9851
      , b.CZHDXJLCXJ
      , b.CZHDXJLRXJ
      , b.DTFYJS
      , b.DYSKDX
      , b.FPGLLR9845
      , b.GDZCBFSS
      , b.GDZCZJ
      , b.GJGDZC9825
      , b.GMSPJS9803
      , b.HLBDDXJDYX
      , b.JKSSDDXJ
      , b.JLR
      , b.JTDZCJZZB
      , b.JYHDXJLCXJ
      , b.JYHDXJLRXJ
      , b.JYXYSXMDJS
      , b.JYXYSXMDZJ
      , b.QDTZSYSSDDXJ
      , b.QT9897
      , b.QT9915
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZZRGDZC
      , b.SDDQTY9799
      , b.SDDQTY9821
      , b.SDDQTY9839
      , b.SDDSFFH
      , b.SHTZSSDDXJ
      , b.TZHDCS9833
      , b.TZHDXJLCXJ
      , b.TZHDXJLRXJ
      , b.TZSS
      , b.TZSZFDXJ
      , b.WXZCTX
      , b.XJDJWDQCYE
      , b.XJDJWDQMYE
      , b.XJDQCYE
      , b.XJDQMYE
      , b.XJJE9813_1
      , b.XJJE9813_2
      , b.XSSPHT9795
      , b.XSTZSSDDXJ
      , b.YNNDQD9893
      , b.YTFYZJ
      , b.ZFDGXSF
      , b.ZFDQTY9809
      , b.ZFDQTY9829
      , b.ZFDQTY9847
      , b.ZFGZGY9805
      , b.ZJE9855_1
      , b.ZJE9885_2
      , b.ZWZWZB
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02XJLLB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.CHDJS
      , b.CHZWSZFDXJ
      , b.CQDTFYTX
      , b.CWFY
      , b.CZGDZC9819
      , b.CZGDZC9871
      , b.CZHDCS9851
      , b.CZHDXJLCXJ
      , b.CZHDXJLRXJ
      , b.DTFYJS
      , b.DYSKDX
      , b.FPGLLR9845
      , b.GDZCBFSS
      , b.GDZCZJ
      , b.GJGDZC9825
      , b.GMSPJS9803
      , b.HLBDDXJDYX
      , b.JKSSDDXJ
      , b.JLR
      , b.JTDZCJZZB
      , b.JYHDXJLCXJ
      , b.JYHDXJLRXJ
      , b.JYXYSXMDJS
      , b.JYXYSXMDZJ
      , b.QDTZSYSSDDXJ
      , b.QT9897
      , b.QT9915
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.RZZRGDZC
      , b.SDDQTY9799
      , b.SDDQTY9821
      , b.SDDQTY9839
      , b.SDDSFFH
      , b.SHTZSSDDXJ
      , b.TZHDCS9833
      , b.TZHDXJLCXJ
      , b.TZHDXJLRXJ
      , b.TZSS
      , b.TZSZFDXJ
      , b.WXZCTX
      , b.XJDJWDQCYE
      , b.XJDJWDQMYE
      , b.XJDQCYE
      , b.XJDQMYE
      , b.XJJE9813_1
      , b.XJJE9813_2
      , b.XSSPHT9795
      , b.XSTZSSDDXJ
      , b.YNNDQD9893
      , b.YTFYZJ
      , b.ZFDGXSF
      , b.ZFDQTY9809
      , b.ZFDQTY9829
      , b.ZFDQTY9847
      , b.ZFGZGY9805
      , b.ZJE9855_1
      , b.ZJE9885_2
      , b.ZWZWZB
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02XJLLB as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_QY_02LRJLRFPB
(
	IN dtDate varchar(10), 
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BCLDZB
      , b.BTSR
      , b.BTSRBT9719
      , b.CWFY
      , b.DGDXSR
      , b.DXLYDLR
      , b.DYSY
      , b.GLFY
      , b.JLR
      , b.JYFY
      , b.KGFPDLR
      , b.KGTZZFPDLR
      , b.LRGHTZ
      , b.LRZR
      , b.NCWFPLR
      , b.QHSY
      , b.QT9691
      , b.QT9699
      , b.QT9907
      , b.QT9909
      , b.QT9911
      , b.QT9913
      , b.QTTZYS
      , b.QTYWLR
      , b.QTYYQN9731
      , b.QTZC
      , b.QTZCJZ9745
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SDS
      , b.SSGDSY
      , b.TQCBJJ
      , b.TQFDGYJ
      , b.TQFDYYGJ
      , b.TQQYFZJJ
      , b.TQRYYYGJ
      , b.TQZGJJJFLJJ
      , b.TZSY
      , b.WFPLR
      , b.WFPLR9793
      , b.WQRDTZSS
      , b.YFPTGGL
      , b.YFYXGGL
      , b.YYFY
      , b.YYGJBK
      , b.YYLR
      , b.YYWSR
      , b.YYWSRC9723
      , b.YYWSRC9727
      , b.YYWSRF9725
      , b.YYWSRFKJSR
      , b.YYWZC
      , b.YYWZCC9735
      , b.YYWZCFKZC
      , b.YYWZCJZZC
      , b.YYWZCZ9737
      , b.ZKYCR
      , b.ZYYWCB
      , b.ZYYWCB9687
      , b.ZYYWLR
      , b.ZYYWSJJFJ
      , b.ZYYWSR
      , b.ZYYWSR9677
      , b.ZYYWSR9679
      , b.ZYYWSRJE
      , b.ZZZBDPTGGL
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02LRJLRFPB as b
  WHERE a.autoID=b.foreignid;
else
SELECT  a.dtDate
	  , a.instInfo 
	  , b.autoID
      , b.BCLDZB
      , b.BTSR
      , b.BTSRBT9719
      , b.CWFY
      , b.DGDXSR
      , b.DXLYDLR
      , b.DYSY
      , b.GLFY
      , b.JLR
      , b.JYFY
      , b.KGFPDLR
      , b.KGTZZFPDLR
      , b.LRGHTZ
      , b.LRZR
      , b.NCWFPLR
      , b.QHSY
      , b.QT9691
      , b.QT9699
      , b.QT9907
      , b.QT9909
      , b.QT9911
      , b.QT9913
      , b.QTTZYS
      , b.QTYWLR
      , b.QTYYQN9731
      , b.QTZC
      , b.QTZCJZ9745
      , b.RPTCheckType
      , b.RPTFeedbackType
      , b.RPTSendType
      , b.SDS
      , b.SSGDSY
      , b.TQCBJJ
      , b.TQFDGYJ
      , b.TQFDYYGJ
      , b.TQQYFZJJ
      , b.TQRYYYGJ
      , b.TQZGJJJFLJJ
      , b.TZSY
      , b.WFPLR
      , b.WFPLR9793
      , b.WQRDTZSS
      , b.YFPTGGL
      , b.YFYXGGL
      , b.YYFY
      , b.YYGJBK
      , b.YYLR
      , b.YYWSR
      , b.YYWSRC9723
      , b.YYWSRC9727
      , b.YYWSRF9725
      , b.YYWSRFKJSR
      , b.YYWZC
      , b.YYWZCC9735
      , b.YYWZCFKZC
      , b.YYWZCJZZC
      , b.YYWZCZ9737
      , b.ZKYCR
      , b.ZYYWCB
      , b.ZYYWCB9687
      , b.ZYYWLR
      , b.ZYYWSJJFJ
      , b.ZYYWSR
      , b.ZYYWSR9677
      , b.ZYYWSR9679
      , b.ZYYWSRJE
      , b.ZZZBDPTGGL
      , b.extend1
      , b.extend2
      , b.extend3
      , b.extend4
      , b.extend5
      , b.FOREIGNID
  FROM QY_JKRCWBB_JC as a, QY_02LRJLRFPB as b
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$

create procedure Proc_Check_JG_JBSX
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CLRQ
    , b.CURRENCY
    , b.JGYWMC
    , b.JGZWMC
    , b.JJHYFL
    , b.JJLX
    , b.JKRGB
    , b.JYYWFW
    , b.QTXXGXRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XZQH
    , b.YLZD
    , b.ZCDJDZ
    , b.ZCZB
    , b.ZSDQR
    , b.ZZJGLBXF
    , b.ZZJGLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_JBSX as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.CLRQ
    , b.CURRENCY
    , b.JGYWMC
    , b.JGZWMC
    , b.JJHYFL
    , b.JJLX
    , b.JKRGB
    , b.JYYWFW
    , b.QTXXGXRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.XZQH
    , b.YLZD
    , b.ZCDJDZ
    , b.ZCZB
    , b.ZSDQR
    , b.ZZJGLBXF
    , b.ZZJGLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID

  FROM JG_JBSX as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  END IF;
END$$


create procedure Proc_Check_JG_GGJZYGXR
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.GGXM
    , b.GGXXGXRQ
    , b.GGZJHM
    , b.GGZJLX
    , b.GGZWLX
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

  FROM   JG_GGJZYGXR as b, JG_JGJBXX_JC as a
  WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    , a.instInfo
    , b.autoID
    , b.GGXM
    , b.GGXXGXRQ
    , b.GGZJHM
    , b.GGZJLX
    , b.GGZWLX
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

  FROM   JG_GGJZYGXR as b, JG_JGJBXX_JC as a
  WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
  
END IF;
END$$

create procedure Proc_Check_GR_ZYXX
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
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
 WHERE a.autoID=b.foreignid;
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
 WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
 END IF;
END$$

create procedure Proc_Check_GR_TSJY
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.BGYS
    , b.FSJE
    , b.FSRQ
    , b.MXXX
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.TSJYLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_TSJY as b, GR_GRXX_JC as a
 WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.BGYS
    , b.FSJE
    , b.FSRQ
    , b.MXXX
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.TSJYLX
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_TSJY as b, GR_GRXX_JC as a
 WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
 END IF;
END$$

create procedure Proc_Check_GR_JZDZ
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.JZDZ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.ZJDZYZBM
    , b.ZJZK
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  
 FROM   GR_JZDZ as b, GR_GRXX_JC as a
 WHERE a.autoID=b.foreignid;
else
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.JZDZ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.ZJDZYZBM
    , b.ZJZK
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
  
 FROM   GR_JZDZ as b, GR_GRXX_JC as a
 WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
 END IF;
END$$

create procedure Proc_Check_GR_JYBSBG
(
	IN dtDate varchar(10),  
	IN strInstCode varchar(20)
)
BEGIN
if dtDate='*****' and strInstCode='*****'
then
SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.JRJGDM
    , b.JSYHKRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.YWH
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_JYBSBG as b, GR_GRXX_JC as a
 WHERE a.autoID=b.foreignid;
 
else

SELECT a.dtDate
    ,a.instInfo
    , b.autoID
    , b.JRJGDM
    , b.JSYHKRQ
    , b.RPTCheckType
    , b.RPTFeedbackType
    , b.RPTSendType
    , b.YWH
    , b.extend1
    , b.extend2
    , b.extend3
    , b.extend4
    , b.extend5
    , b.FOREIGNID
 FROM GR_JYBSBG as b, GR_GRXX_JC as a
 WHERE a.dtDate=dtDate and a.instInfo=strInstCode and a.autoID=b.foreignid;
 
END IF;
END$$
	