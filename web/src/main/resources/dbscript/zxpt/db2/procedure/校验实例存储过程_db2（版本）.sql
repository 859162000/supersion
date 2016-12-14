--1、担保信息段
CREATE PROCEDURE PROC_CHECK_GR_DBXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
	a.dtdate,
	a.instinfo,
	b.autoID,
      b.DBJE,
      b.DBZT,
      b.RPTCheckType,
      b.RPTFeedbackType,
      b.RPTSendType,
      b.XM,
      b.ZJHM,
      b.ZJLX,
      b.extend1,
      b.extend2,
      b.extend3,
      b.extend4,
      b.extend5,
      b.FOREIGNID
    from GR_GRXX_JC a,GR_DBXX b  
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
	a.dtdate,
	a.instinfo,
	b.autoID,
      b.DBJE,
      b.DBZT,
      b.RPTCheckType,
      b.RPTFeedbackType,
      b.RPTSendType,
      b.XM,
      b.ZJHM,
      b.ZJLX,
      b.extend1,
      b.extend2,
      b.extend3,
      b.extend4,
      b.extend5,
      b.FOREIGNID
    from GR_GRXX_JC a,GR_DBXX b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--2、身份信息段
CREATE PROCEDURE PROC_CHECK_GR_GRSFXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CSRQ
      ,b.DWDH
      ,b.DZYX
      ,b.HJDZ
      ,b.HYZK
      ,b.POGZDW
      ,b.POLXDH
      ,b.POXM
      ,b.POZJHM
      ,b.POZJLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJHM
      ,b.TXDZ
      ,b.TXDZYZBM
      ,b.XB
      ,b.ZGXL
      ,b.ZGXW
      ,b.ZZDH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,FOREIGNID 
        from GR_GRXX_JC a,GR_GRSFXX b
        where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CSRQ
      ,b.DWDH
      ,b.DZYX
      ,b.HJDZ
      ,b.HYZK
      ,b.POGZDW
      ,b.POLXDH
      ,b.POXM
      ,b.POZJHM
      ,b.POZJLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJHM
      ,b.TXDZ
      ,b.TXDZYZBM
      ,b.XB
      ,b.ZGXL
      ,b.ZGXW
      ,b.ZZDH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,FOREIGNID 
        from GR_GRXX_JC a,GR_GRSFXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--3、个人信息基础段
CREATE PROCEDURE PROC_CHECK_GR_GRXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
    from GR_GRXX_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
    from GR_GRXX_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--4、交易标识变更段
CREATE PROCEDURE PROC_CHECK_GR_JYBSBG
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JRJGDM
      ,b.JSYHKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_JYBSBG b  
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JRJGDM
      ,b.JSYHKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YWH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_JYBSBG b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--5、居住地址段
CREATE PROCEDURE PROC_CHECK_GR_JZDZ
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JZDZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJDZYZBM
      ,b.ZJZK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_JZDZ b 
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JZDZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJDZYZBM
      ,b.ZJZK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_JZDZ b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--6、特殊交易段
CREATE PROCEDURE PROC_CHECK_GR_TSJY
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BGYS
      ,b.FSJE
      ,b.FSRQ
      ,b.MXXX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.TSJYLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_TSJY b  
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BGYS
      ,b.FSJE
      ,b.FSRQ
      ,b.MXXX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.TSJYLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from GR_GRXX_JC a,GR_TSJY b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--7、职业信息段
CREATE PROCEDURE PROC_CHECK_GR_ZYXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BDWGZQSNF
      ,b.DWDZ
      ,b.DWDZYZBM
      ,b.DWMC
      ,b.DWSSHY
      ,b.GZZH
      ,b.GZZHKHYH
      ,b.NSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZC
      ,b.ZW
      ,b.ZY
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
        from GR_GRXX_JC a,GR_ZYXX b
        where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BDWGZQSNF
      ,b.DWDZ
      ,b.DWDZYZBM
      ,b.DWMC
      ,b.DWSSHY
      ,b.GZZH
      ,b.GZZHKHYH
      ,b.NSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZC
      ,b.ZW
      ,b.ZY
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
    from GR_GRXX_JC a,GR_ZYXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--8、高管及主要关系人段
CREATE PROCEDURE PROC_CHECK_JG_GGJZYGXR
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.GGXM
      ,b.GGXXGXRQ
      ,b.GGZJHM
      ,b.GGZJLX
      ,b.GGZWLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from JG_JGJBXX_JC a,JG_GGJZYGXR b
        where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.GGXM
      ,b.GGXXGXRQ
      ,b.GGZJHM
      ,b.GGZJLX
      ,b.GGZWLX
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from JG_JGJBXX_JC a,JG_GGJZYGXR b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--9、基本属性段
CREATE PROCEDURE PROC_CHECK_JG_JBSX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.CLRQ
      ,b.CURRENCY
      ,b.JGYWMC
      ,b.JGZWMC
      ,b.JJHYFL
      ,b.JJLX
      ,b.JKRGB
      ,b.JYYWFW
      ,b.QTXXGXRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XZQH
      ,b.YLZD
      ,b.ZCDJDZ
      ,b.ZCZB
      ,b.ZSDQR
      ,b.ZZJGLBXF
      ,b.ZZJGLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
     from JG_JGJBXX_JC a,JG_JBSX b  
     where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.CLRQ
      ,b.CURRENCY
      ,b.JGYWMC
      ,b.JGZWMC
      ,b.JJHYFL
      ,b.JJLX
      ,b.JKRGB
      ,b.JYYWFW
      ,b.QTXXGXRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XZQH
      ,b.YLZD
      ,b.ZCDJDZ
      ,b.ZCZB
      ,b.ZSDQR
      ,b.ZZJGLBXF
      ,b.ZZJGLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
     from JG_JGJBXX_JC a,JG_JBSX b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--10、机构基本信息基础段
CREATE PROCEDURE PROC_CHECK_JG_JGJBXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
     from JG_JGJBXX_JC ';
    
    ELSE
    
    SET V_CURSQL = ' select autoID
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
     from JG_JGJBXX_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--11、机构状态段
CREATE PROCEDURE PROC_CHECK_JG_JGZT
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JBHZT
      ,b.JGZT
      ,b.QTXXGXRQ
      ,b.QYGM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from JG_JGJBXX_JC a,JG_JGZT b
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.JBHZT
      ,b.JGZT
      ,b.QTXXGXRQ
      ,b.QYGM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from JG_JGJBXX_JC a,JG_JGZT b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--12、联络信息段
CREATE PROCEDURE PROC_CHECK_JG_LLXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BGDZ
      ,b.CWBLXDH
      ,b.LLXXGXRQ
      ,b.LXDH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from JG_JGJBXX_JC a,JG_LLXX b 
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BGDZ
      ,b.CWBLXDH
      ,b.LLXXGXRQ
      ,b.LXDH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from JG_JGJBXX_JC a,JG_LLXX b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--13、上级机构（主管单位）段
CREATE PROCEDURE PROC_CHECK_JG_SJJG
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJJGDJZCHLX
      ,b.SJJGDJZCHM
      ,b.SJJGMC
      ,b.SJJGXXGXRQ
      ,b.SJJGZZJGDM
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from JG_JGJBXX_JC a,JG_SJJG b  where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJJGDJZCHLX
      ,b.SJJGDJZCHM
      ,b.SJJGMC
      ,b.SJJGXXGXRQ
      ,b.SJJGZZJGDM
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
	from JG_JGJBXX_JC a,JG_SJJG b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--14、重要股东段
CREATE PROCEDURE PROC_CHECK_JG_ZYGD
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CURRENCY
      ,b.GDCZJE
      ,b.GDLX
      ,b.GDMC
      ,b.GDXXGXRQ
      ,b.GDZJHM
      ,b.GDZJLX
      ,b.GDZZJGDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
      from JG_JGJBXX_JC a,JG_ZYGD b        
	  where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CURRENCY
      ,b.GDCZJE
      ,b.GDLX
      ,b.GDMC
      ,b.GDXXGXRQ
      ,b.GDZJHM
      ,b.GDZJLX
      ,b.GDZZJGDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YLZD
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from JG_JGJBXX_JC a,JG_ZYGD b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--15、主要关联企业段
CREATE PROCEDURE PROC_CHECK_JG_ZYGLQY
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
      ,b.DJZCHM
      ,b.DJZCHMLX
      ,b.GLLX
      ,b.GLQYMC
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XXGXRQ
      ,b.YLZD
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from JG_JGJBXX_JC a,JG_ZYGLQY b  where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
      ,b.DJZCHM
      ,b.DJZCHMLX
      ,b.GLLX
      ,b.GLQYMC
      ,b.JGXYDM
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XXGXRQ
      ,b.YLZD
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from JG_JGJBXX_JC a,JG_ZYGLQY b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO
--16、家族成员信息基础段
CREATE PROCEDURE PROC_CHECK_JZ_JZCYXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
     from JZ_JZCYXX_JC';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
     from JZ_JZCYXX_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--17、2002版利润及利润分配表
CREATE PROCEDURE PROC_CHECK_QY_02LRJLRFPB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
      ,b.BCLDZB
      ,b.BTSR
      ,b.BTSRBT9719
      ,b.CWFY
      ,b.DGDXSR
      ,b.DXLYDLR
      ,b.DYSY
      ,b.GLFY
      ,b.JLR
      ,b.JYFY
      ,b.KGFPDLR
      ,b.KGTZZFPDLR
      ,b.LRGHTZ
      ,b.LRZR
      ,b.NCWFPLR
      ,b.QHSY
      ,b.QT9691
      ,b.QT9699
      ,b.QT9907
      ,b.QT9909
      ,b.QT9911
      ,b.QT9913
      ,b.QTTZYS
      ,b.QTYWLR
      ,b.QTYYQN9731
      ,b.QTZC
      ,b.QTZCJZ9745
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDS
      ,b.SSGDSY
      ,b.TQCBJJ
      ,b.TQFDGYJ
      ,b.TQFDYYGJ
      ,b.TQQYFZJJ
      ,b.TQRYYYGJ
      ,b.TQZGJJJFLJJ
      ,b.TZSY
      ,b.WFPLR
      ,b.WFPLR9793
      ,b.WQRDTZSS
      ,b.YFPTGGL
      ,b.YFYXGGL
      ,b.YYFY
      ,b.YYGJBK
      ,b.YYLR
      ,b.YYWSR
      ,b.YYWSRC9723
      ,b.YYWSRC9727
      ,b.YYWSRF9725
      ,b.YYWSRFKJSR
      ,b.YYWZC
      ,b.YYWZCC9735
      ,b.YYWZCFKZC
      ,b.YYWZCJZZC
      ,b.YYWZCZ9737
      ,b.ZKYCR
      ,b.ZYYWCB
      ,b.ZYYWCB9687
      ,b.ZYYWLR
      ,b.ZYYWSJJFJ
      ,b.ZYYWSR
      ,b.ZYYWSR9677
      ,b.ZYYWSR9679
      ,b.ZYYWSRJE
      ,b.ZZZBDPTGGL
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02LRJLRFPB b  
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
      ,b.BCLDZB
      ,b.BTSR
      ,b.BTSRBT9719
      ,b.CWFY
      ,b.DGDXSR
      ,b.DXLYDLR
      ,b.DYSY
      ,b.GLFY
      ,b.JLR
      ,b.JYFY
      ,b.KGFPDLR
      ,b.KGTZZFPDLR
      ,b.LRGHTZ
      ,b.LRZR
      ,b.NCWFPLR
      ,b.QHSY
      ,b.QT9691
      ,b.QT9699
      ,b.QT9907
      ,b.QT9909
      ,b.QT9911
      ,b.QT9913
      ,b.QTTZYS
      ,b.QTYWLR
      ,b.QTYYQN9731
      ,b.QTZC
      ,b.QTZCJZ9745
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDS
      ,b.SSGDSY
      ,b.TQCBJJ
      ,b.TQFDGYJ
      ,b.TQFDYYGJ
      ,b.TQQYFZJJ
      ,b.TQRYYYGJ
      ,b.TQZGJJJFLJJ
      ,b.TZSY
      ,b.WFPLR
      ,b.WFPLR9793
      ,b.WQRDTZSS
      ,b.YFPTGGL
      ,b.YFYXGGL
      ,b.YYFY
      ,b.YYGJBK
      ,b.YYLR
      ,b.YYWSR
      ,b.YYWSRC9723
      ,b.YYWSRC9727
      ,b.YYWSRF9725
      ,b.YYWSRFKJSR
      ,b.YYWZC
      ,b.YYWZCC9735
      ,b.YYWZCFKZC
      ,b.YYWZCJZZC
      ,b.YYWZCZ9737
      ,b.ZKYCR
      ,b.ZYYWCB
      ,b.ZYYWCB9687
      ,b.ZYYWLR
      ,b.ZYYWSJJFJ
      ,b.ZYYWSR
      ,b.ZYYWSR9677
      ,b.ZYYWSR9679
      ,b.ZYYWSRJE
      ,b.ZZZBDPTGGL
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02LRJLRFPB b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--18、2002版现金流量表
CREATE PROCEDURE PROC_CHECK_QY_02XJLLB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CHDJS
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY
      ,b.CZGDZC9819
      ,b.CZGDZC9871
      ,b.CZHDCS9851
      ,b.CZHDXJLCXJ
      ,b.CZHDXJLRXJ
      ,b.DTFYJS
      ,b.DYSKDX
      ,b.FPGLLR9845
      ,b.GDZCBFSS
      ,b.GDZCZJ
      ,b.GJGDZC9825
      ,b.GMSPJS9803
      ,b.HLBDDXJDYX
      ,b.JKSSDDXJ
      ,b.JLR
      ,b.JTDZCJZZB
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYSXMDJS
      ,b.JYXYSXMDZJ
      ,b.QDTZSYSSDDXJ
      ,b.QT9897
      ,b.QT9915
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDQTY9799
      ,b.SDDQTY9821
      ,b.SDDQTY9839
      ,b.SDDSFFH
      ,b.SHTZSSDDXJ
      ,b.TZHDCS9833
      ,b.TZHDXJLCXJ
      ,b.TZHDXJLRXJ
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJE9813_1
      ,b.XJJE9813_2
      ,b.XSSPHT9795
      ,b.XSTZSSDDXJ
      ,b.YNNDQD9893
      ,b.YTFYZJ
      ,b.ZFDGXSF
      ,b.ZFDQTY9809
      ,b.ZFDQTY9829
      ,b.ZFDQTY9847
      ,b.ZFGZGY9805
      ,b.ZJE9855_1
      ,b.ZJE9885_2
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02XJLLB b 
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CHDJS
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY
      ,b.CZGDZC9819
      ,b.CZGDZC9871
      ,b.CZHDCS9851
      ,b.CZHDXJLCXJ
      ,b.CZHDXJLRXJ
      ,b.DTFYJS
      ,b.DYSKDX
      ,b.FPGLLR9845
      ,b.GDZCBFSS
      ,b.GDZCZJ
      ,b.GJGDZC9825
      ,b.GMSPJS9803
      ,b.HLBDDXJDYX
      ,b.JKSSDDXJ
      ,b.JLR
      ,b.JTDZCJZZB
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYSXMDJS
      ,b.JYXYSXMDZJ
      ,b.QDTZSYSSDDXJ
      ,b.QT9897
      ,b.QT9915
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDQTY9799
      ,b.SDDQTY9821
      ,b.SDDQTY9839
      ,b.SDDSFFH
      ,b.SHTZSSDDXJ
      ,b.TZHDCS9833
      ,b.TZHDXJLCXJ
      ,b.TZHDXJLRXJ
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJE9813_1
      ,b.XJJE9813_2
      ,b.XSSPHT9795
      ,b.XSTZSSDDXJ
      ,b.YNNDQD9893
      ,b.YTFYZJ
      ,b.ZFDGXSF
      ,b.ZFDQTY9809
      ,b.ZFDQTY9829
      ,b.ZFDQTY9847
      ,b.ZFGZGY9805
      ,b.ZJE9855_1
      ,b.ZJE9885_2
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02XJLLB b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--19、2002版资产负债表
CREATE PROCEDURE PROC_CHECK_QY_02ZCFZB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,
	b.autoID
      ,b.CH
      ,b.CHCCP
      ,b.CHYCL
      ,b.CQFZHJ
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQTZ
      ,b.CQTZHJ
      ,b.CQYFK
      ,b.CQZQTZ
      ,b.DCLGDZCJSS
      ,b.DCLLDZCJSS
      ,b.DQJK
      ,b.DQTZ
      ,b.DTFY
      ,b.DYSKDX
      ,b.DYSKJX
      ,b.DYZC
      ,b.DYZCGD9577
      ,b.DYZCGDZCXL
      ,b.FRZB
      ,b.FRZBGJFRZB
      ,b.FRZBJTFRZB
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZCHJ
      ,b.GDZCJE
      ,b.GDZCJZ
      ,b.GDZCQL
      ,b.GDZCYJ
      ,b.GDZCZJZZB
      ,b.GJZB
      ,b.GRZB
      ,b.HBJC
      ,b.HBZJ
      ,b.JTZB
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.LJZJ
      ,b.QHBZJ
      ,b.QTCQFZ
      ,b.QTCQFZ9629
      ,b.QTCQZC
      ,b.QTCQZC9581
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYJK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSGDQY
      ,b.SSZB
      ,b.SYZQYHJ
      ,b.WBBBZSCE
      ,b.WFPLR
      ,b.WQRDTZSS
      ,b.WSZB
      ,b.WXJQTZCHJ
      ,b.WXZC
      ,b.WXZCTDSYQ
      ,b.YFFLF
      ,b.YFGZ
      ,b.YFJJ
      ,b.YFLR
      ,b.YFPJ
      ,b.YFSJ
      ,b.YFZK
      ,b.YIFZK
      ,b.YJFZ
      ,b.YNNDQ9533
      ,b.YNNDQDCQFZ
      ,b.YSBTK
      ,b.YSCKTS
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YTFY
      ,b.YUSZK
      ,b.YYGJ
      ,b.YYGJBCLDZB
      ,b.YYGJFDYYGJ
      ,b.YYGJGJJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
     from QY_JKRCWBB_JC a,QY_02ZCFZB b 
 
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,
	b.autoID
      ,b.CH
      ,b.CHCCP
      ,b.CHYCL
      ,b.CQFZHJ
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQTZ
      ,b.CQTZHJ
      ,b.CQYFK
      ,b.CQZQTZ
      ,b.DCLGDZCJSS
      ,b.DCLLDZCJSS
      ,b.DQJK
      ,b.DQTZ
      ,b.DTFY
      ,b.DYSKDX
      ,b.DYSKJX
      ,b.DYZC
      ,b.DYZCGD9577
      ,b.DYZCGDZCXL
      ,b.FRZB
      ,b.FRZBGJFRZB
      ,b.FRZBJTFRZB
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZCHJ
      ,b.GDZCJE
      ,b.GDZCJZ
      ,b.GDZCQL
      ,b.GDZCYJ
      ,b.GDZCZJZZB
      ,b.GJZB
      ,b.GRZB
      ,b.HBJC
      ,b.HBZJ
      ,b.JTZB
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.LJZJ
      ,b.QHBZJ
      ,b.QTCQFZ
      ,b.QTCQFZ9629
      ,b.QTCQZC
      ,b.QTCQZC9581
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYJK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SSGDQY
      ,b.SSZB
      ,b.SYZQYHJ
      ,b.WBBBZSCE
      ,b.WFPLR
      ,b.WQRDTZSS
      ,b.WSZB
      ,b.WXJQTZCHJ
      ,b.WXZC
      ,b.WXZCTDSYQ
      ,b.YFFLF
      ,b.YFGZ
      ,b.YFJJ
      ,b.YFLR
      ,b.YFPJ
      ,b.YFSJ
      ,b.YFZK
      ,b.YIFZK
      ,b.YJFZ
      ,b.YNNDQ9533
      ,b.YNNDQDCQFZ
      ,b.YSBTK
      ,b.YSCKTS
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YTFY
      ,b.YUSZK
      ,b.YYGJ
      ,b.YYGJBCLDZB
      ,b.YYGJFDYYGJ
      ,b.YYGJGJJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_02ZCFZB b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--20、2007版利润及利润分配表
CREATE PROCEDURE PROC_CHECK_QY_07LRJLRFPB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.CWFY
      ,b.DLYQYHHYQYDTZSY
      ,b.FLDZCSS
      ,b.GLFY
      ,b.GYJZBDJSY
      ,b.JBMGSY
      ,b.JLR
      ,b.LRZE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDSFY
      ,b.TZJSY
      ,b.XSFY
      ,b.XSMGSY
      ,b.YYCB
      ,b.YYLR
      ,b.YYSJJFJ
      ,b.YYSR
      ,b.YYWSR
      ,b.YYWZC
      ,b.ZCJZSS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_JKRCWBB_JC a,QY_07LRJLRFPB b  where  a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.CWFY
      ,b.DLYQYHHYQYDTZSY
      ,b.FLDZCSS
      ,b.GLFY
      ,b.GYJZBDJSY
      ,b.JBMGSY
      ,b.JLR
      ,b.LRZE
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SDSFY
      ,b.TZJSY
      ,b.XSFY
      ,b.XSMGSY
      ,b.YYCB
      ,b.YYLR
      ,b.YYSJJFJ
      ,b.YYSR
      ,b.YYWSR
      ,b.YYWZC
      ,b.ZCJZSS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_07LRJLRFPB b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO
--21、2007版现金流量表
CREATE PROCEDURE PROC_CHECK_QY_07XJLLB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY_1
      ,b.CZGDZCWXZCHQTCQZCDSS
      ,b.CZGDZCWXZCHQTCQZCSSHDXJJE
      ,b.CZHDCSDXJLLJE
      ,b.CZHDXJLRXJ
      ,b.CZHDXJLRXJ_1
      ,b.CZZGSJQTYYDWSDDXJJE
      ,b.DTFYJS
      ,b.DYSDSFZZJ
      ,b.DYSDSZCJS
      ,b.FPGLLRHCFLXSZFDXJ
      ,b.GDZCBFSS
      ,b.GDZCZJYQZCZHSCXSWZCZJ
      ,b.GMGDZCWXZCHQTCQZCSZFDXJ
      ,b.GMSPJSLWZFDXJ
      ,b.GYJZBDSS
      ,b.JLR_1
      ,b.JYHDCSDXJLLJE
      ,b.JYHDCSDXJLLJE_1
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYFXMDZJ
      ,b.JYXYSXMDJS
      ,b.QCXJJXJDJWYE
      ,b.QDJKSDDXJ
      ,b.QDTZSYSSDDXJ
      ,b.QDZGSJQTSYDWZFDXJJE
      ,b.QMXJJXJDJWYEL
      ,b.QT
      ,b.QT_1
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDSFFH
      ,b.SDQTYCZHDYGDXJ
      ,b.SDQTYJYHDYGDXJ
      ,b.SDQTYTZHDYGDXJ
      ,b.SHTZSSDDXJ
      ,b.SLBDDXJJXJDJWDYX
      ,b.TZHDCSDXJLLJE
      ,b.TZHDXJLRXJ
      ,b.TZHDXJLRXJ_1
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJXJDJWJZEW
      ,b.XJJXJDJWJZZJE
      ,b.XSCPHTGLWSDDXJ
      ,b.XSTZSDDXJ
      ,b.YNNDQDKZHGSZQ
      ,b.YTFYZJ
      ,b.ZCJZZB
      ,b.ZFDGXSF
      ,b.ZFGZGYJWZGZFDXJ
      ,b.ZFQTYCZHDYGDXJ
      ,b.ZFQTYJYHDYGDXJ
      ,b.ZFQTYTZHDYGDXJ
      ,b.ZHDJS
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
  from QY_JKRCWBB_JC a,QY_07XJLLB b  where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.CHZWSZFDXJ
      ,b.CQDTFYTX
      ,b.CWFY_1
      ,b.CZGDZCWXZCHQTCQZCDSS
      ,b.CZGDZCWXZCHQTCQZCSSHDXJJE
      ,b.CZHDCSDXJLLJE
      ,b.CZHDXJLRXJ
      ,b.CZHDXJLRXJ_1
      ,b.CZZGSJQTYYDWSDDXJJE
      ,b.DTFYJS
      ,b.DYSDSFZZJ
      ,b.DYSDSZCJS
      ,b.FPGLLRHCFLXSZFDXJ
      ,b.GDZCBFSS
      ,b.GDZCZJYQZCZHSCXSWZCZJ
      ,b.GMGDZCWXZCHQTCQZCSZFDXJ
      ,b.GMSPJSLWZFDXJ
      ,b.GYJZBDSS
      ,b.JLR_1
      ,b.JYHDCSDXJLLJE
      ,b.JYHDCSDXJLLJE_1
      ,b.JYHDXJLCXJ
      ,b.JYHDXJLRXJ
      ,b.JYXYFXMDZJ
      ,b.JYXYSXMDJS
      ,b.QCXJJXJDJWYE
      ,b.QDJKSDDXJ
      ,b.QDTZSYSSDDXJ
      ,b.QDZGSJQTSYDWZFDXJJE
      ,b.QMXJJXJDJWYEL
      ,b.QT
      ,b.QT_1
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZZRGDZC
      ,b.SDDSFFH
      ,b.SDQTYCZHDYGDXJ
      ,b.SDQTYJYHDYGDXJ
      ,b.SDQTYTZHDYGDXJ
      ,b.SHTZSSDDXJ
      ,b.SLBDDXJJXJDJWDYX
      ,b.TZHDCSDXJLLJE
      ,b.TZHDXJLRXJ
      ,b.TZHDXJLRXJ_1
      ,b.TZSS
      ,b.TZSZFDXJ
      ,b.WXZCTX
      ,b.XJDJWDQCYE
      ,b.XJDJWDQMYE
      ,b.XJDQCYE
      ,b.XJDQMYE
      ,b.XJJXJDJWJZEW
      ,b.XJJXJDJWJZZJE
      ,b.XSCPHTGLWSDDXJ
      ,b.XSTZSDDXJ
      ,b.YNNDQDKZHGSZQ
      ,b.YTFYZJ
      ,b.ZCJZZB
      ,b.ZFDGXSF
      ,b.ZFGZGYJWZGZFDXJ
      ,b.ZFQTYCZHDYGDXJ
      ,b.ZFQTYJYHDYGDXJ
      ,b.ZFQTYTZHDYGDXJ
      ,b.ZHDJS
      ,b.ZWZWZB
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_07XJLLB b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--22、2007版资产负债表
CREATE PROCEDURE PROC_CHECK_QY_07ZCFZB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CH
      ,b.CQDTFY
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQYFK
      ,b.CQYSK
      ,b.CYZDQTZ
      ,b.DQJK
      ,b.DYSDSFZ
      ,b.DYSDSZC
      ,b.FLDFZHJ
      ,b.FLDZCHJ
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZC
      ,b.GDZCQL
      ,b.HBZJ
      ,b.JKCG
      ,b.JYXJRFZ
      ,b.JYXJRZC
      ,b.KFZC
      ,b.KGCSDJRZC
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.QTFLDFZ
      ,b.QTFLDZC
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SCXSWZC
      ,b.SSZBHGB
      ,b.SY
      ,b.SYZQYHJ
      ,b.TZXFDC
      ,b.WFPLR
      ,b.WXZC
      ,b.YFGL
      ,b.YFLX
      ,b.YFPJ
      ,b.YFZGXZ
      ,b.YFZK_1
      ,b.YFZK_2
      ,b.YFZQ
      ,b.YJFZ
      ,b.YJSF
      ,b.YNNDQDFLDFZ
      ,b.YNNDQDFLDZC
      ,b.YQZC
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.YYGJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID from QY_JKRCWBB_JC a,QY_07ZCFZB b  where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.CH
      ,b.CQDTFY
      ,b.CQGQTZ
      ,b.CQJK
      ,b.CQYFK
      ,b.CQYSK
      ,b.CYZDQTZ
      ,b.DQJK
      ,b.DYSDSFZ
      ,b.DYSDSZC
      ,b.FLDFZHJ
      ,b.FLDZCHJ
      ,b.FZHJ
      ,b.FZHSYZQYHJ
      ,b.GCWZ
      ,b.GDZC
      ,b.GDZCQL
      ,b.HBZJ
      ,b.JKCG
      ,b.JYXJRFZ
      ,b.JYXJRZC
      ,b.KFZC
      ,b.KGCSDJRZC
      ,b.LDFZHJ
      ,b.LDZCHJ
      ,b.QTFLDFZ
      ,b.QTFLDZC
      ,b.QTLDFZ
      ,b.QTLDZC
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SCXSWZC
      ,b.SSZBHGB
      ,b.SY
      ,b.SYZQYHJ
      ,b.TZXFDC
      ,b.WFPLR
      ,b.WXZC
      ,b.YFGL
      ,b.YFLX
      ,b.YFPJ
      ,b.YFZGXZ
      ,b.YFZK_1
      ,b.YFZK_2
      ,b.YFZQ
      ,b.YJFZ
      ,b.YJSF
      ,b.YNNDQDFLDFZ
      ,b.YNNDQDFLDZC
      ,b.YQZC
      ,b.YSGL
      ,b.YSLX
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.YYGJ
      ,b.ZBGJ
      ,b.ZCZJ
      ,b.ZJGC
      ,b.ZXYFK
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
	from QY_JKRCWBB_JC a,QY_07ZCFZB b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--23、保函业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_BHYW_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_BHYW_JC a,QY_BHYW_BS b   
	where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_BHYW_JC a,QY_BHYW_BS b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--24、保函业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_BHYW_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
      ,operationUser from QY_BHYW_JC';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
      ,operationUser from QY_BHYW_JC
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--25、保函业务信息段
CREATE PROCEDURE PROC_CHECK_QY_BHYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
    from QY_BHYW_JC a,QY_BHYWXX b  where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
    from QY_BHYW_JC a,QY_BHYWXX b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--26、不良信贷资产处置信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_BLXDZCCL_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
     from QY_BLXDZCCL_JC a,QY_BLXDZCCL_BS b
     where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
    from QY_BLXDZCCL_JC a,QY_BLXDZCCL_BS b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--27、不良信贷资产处置信息基础段
CREATE PROCEDURE PROC_CHECK_QY_BLXDZCCL_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
    from QY_BLXDZCCL_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
    from QY_BLXDZCCL_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--28、保理叙做信息段
CREATE PROCEDURE PROC_CHECK_QY_BLXZXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
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
from QY_BLYW_JC a,QY_BLXZXX b  where  a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select  a.dtdate,a.instinfo,
b.autoID
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
    from QY_BLYW_JC a,QY_BLXZXX b   
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--29、保理业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_BLYW_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
           b.autoID
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
     from QY_BLYW_JC a,QY_BLYW_BS b  
     where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
           b.autoID
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
     from QY_BLYW_JC a,QY_BLYW_BS b     
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--30、保理业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_BLYW_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
    from QY_BLYW_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
    from QY_BLYW_JC    
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--31、财务部联系方式
CREATE PROCEDURE PROC_CHECK_QY_CWBLXFS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_JKRGK_JC a,QY_CWBLXFS b  
     where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_JKRGK_JC a,QY_CWBLXFS b   
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--33、出资资本构成情况
CREATE PROCEDURE PROC_CHECK_QY_CZZBGC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BZ
      ,b.CZF
      ,b.CZFDKKBM
      ,b.CZJE
      ,b.DJZCH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
    from QY_JKRZBGC_JC a,QY_CZZBGC b  
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BZ
      ,b.CZF
      ,b.CZFDKKBM
      ,b.CZJE
      ,b.DJZCH
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZJHM
      ,b.ZJLX
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
     from QY_JKRZBGC_JC a,QY_CZZBGC b  
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--32、处置信息段
CREATE PROCEDURE PROC_CHECK_QY_CZXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.CZRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YE
      ,b.YSHJE
      ,b.ZYZCCZFS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID  
     from QY_BLXDZCCL_JC a,QY_CZXX b  
     where a.autoid=b.foreignid' ;
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.CZRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.YE
      ,b.YSHJE
      ,b.ZYZCCZFS
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
     from QY_BLXDZCCL_JC a,QY_CZXX b 	  
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--34、保证合同信息段
CREATE PROCEDURE PROC_CHECK_QY_DBHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
from QY_DBXX_JC a,QY_DBHTXX b  where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
     from QY_DBXX_JC a,QY_DBHTXX b  
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--35、担保信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_DBXX_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_DBXX_JC a,QY_DBXX_BS b  
     where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_DBXX_JC a,QY_DBXX_BS b  
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--36、担保信息基础段
CREATE PROCEDURE PROC_CHECK_QY_DBXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
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
    from QY_DBXX_JC';
    
    ELSE
    
    SET V_CURSQL = 'select 
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
     from QY_DBXX_JC 
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--37、垫款信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_DKXX_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKXX_JC a,QY_DKXX_BS b  
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKXX_JC a,QY_DKXX_BS b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--38、垫款信息基础段
CREATE PROCEDURE PROC_CHECK_QY_DKXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
      ,operationUser from QY_DKXX_JC';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
      ,operationUser from QY_DKXX_JC    
     where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--39、贷款业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_DKYW_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_DKYW_BS b  
    where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_DKYW_BS b   
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--40、贷款业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_DKYW_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
      from QY_DKYW_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
    from QY_DKYW_JC  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--41、展期信息
CREATE PROCEDURE PROC_CHECK_QY_DKYW_ZQXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_DKYW_ZQXX b    
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_DKYW_ZQXX b     
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--42、贷款业务信息
CREATE PROCEDURE PROC_CHECK_QY_DKYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
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
    from QY_DKYW_JC a,QY_DKYWXX b      
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
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
    from QY_DKYW_JC a,QY_DKYWXX b     
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--43、大事信息
CREATE PROCEDURE PROC_CHECK_QY_DSXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
    from QY_JKRGZ_JC a,QY_DSXX b       
    where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
    from QY_JKRGZ_JC a,QY_DSXX b      
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--44、对外投资情况
CREATE PROCEDURE PROC_CHECK_QY_DWTZQK
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.BTZDWDKKBM,
        b.autoID
      ,b.BTZDWDKKBM
      ,b.BTZDWMC
      ,b.BZ1
      ,b.BZ2
      ,b.BZ3
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.TZJE1
      ,b.TZJE2
      ,b.TZJE3
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
    from QY_JKRZBGC_JC a,QY_DWTZQK b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.BTZDWDKKBM,
        b.autoID
      ,b.BTZDWDKKBM
      ,b.BTZDWMC
      ,b.BZ1
      ,b.BZ2
      ,b.BZ3
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.TZJE1
      ,b.TZJE2
      ,b.TZJE3
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
    from QY_JKRZBGC_JC a,QY_DWTZQK b     
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--45、抵押合同信息段
CREATE PROCEDURE PROC_CHECK_QY_DYHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DBXX_JC a,QY_DYHTXX b
	where a.autoid=b.foreignid ';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DBXX_JC a,QY_DYHTXX b      
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--46、法人代表家族企业成员情况
CREATE PROCEDURE PROC_CHECK_QY_FRDBJZQYCY
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
      ,b.FOREIGNID from QY_JKRZBGC_JC a,QY_FRDBJZQYCY b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
      ,b.FOREIGNID from QY_JKRZBGC_JC a,QY_FRDBJZQYCY b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--47、高级管理人情况
CREATE PROCEDURE PROC_CHECK_QY_GJGLRQK
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.GGRYCSRQ
      ,b.GGRYGZJL
      ,b.GGRYLB
      ,b.GGRYXM
      ,b.GGRYZGXL
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XB
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_JKRZBGC_JC a,QY_GJGLRQK b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.GGRYCSRQ
      ,b.GGRYGZJL
      ,b.GGRYLB
      ,b.GGRYXM
      ,b.GGRYZGXL
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.XB
      ,b.ZJHM
      ,b.ZJLX
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_JKRZBGC_JC a,QY_GJGLRQK b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--48、公开授信信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_GKSX_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_GKSX_JC a,QY_GKSX_BS b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_GKSX_JC a,QY_GKSX_BS b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--49、公开授信信息基础段
CREATE PROCEDURE PROC_CHECK_QY_GKSX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
      autoID
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
from QY_GKSX_JC';
    
    ELSE
    
    SET V_CURSQL = 'select 
autoID
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
    from QY_GKSX_JC  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--50、股票信息
CREATE PROCEDURE PROC_CHECK_QY_GPXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
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
    from QY_JKRGK_JC a,QY_GPXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
       b.autoID
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
    from QY_JKRGK_JC a,QY_GPXX b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--51、还款信息
CREATE PROCEDURE PROC_CHECK_QY_HKXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_HKXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_HKXX b    
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--52、汇票业务信息段
CREATE PROCEDURE PROC_CHECK_QY_HPYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.BZ
      ,b.BZJBL
      ,b.CPRMC
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.HPCDR
      ,b.HPDQR
      ,b.HPFKRQ
      ,b.HPJE
      ,b.HPZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_YHCDHP_JC a,QY_HPYWXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
      ,b.BZ
      ,b.BZJBL
      ,b.CPRMC
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.HPCDR
      ,b.HPDQR
      ,b.HPFKRQ
      ,b.HPJE
      ,b.HPZT
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_YHCDHP_JC a,QY_HPYWXX b   
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--53、合同金额信息
CREATE PROCEDURE PROC_CHECK_QY_HTJEXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_DKYW_JC a,QY_HTJEXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_DKYW_JC a,QY_HTJEXX b   
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--54、合同信息
CREATE PROCEDURE PROC_CHECK_QY_HTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
from QY_DKYW_JC a,QY_HTXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
from QY_DKYW_JC a,QY_HTXX b   
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--55、借据信息
CREATE PROCEDURE PROC_CHECK_QY_JJXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_JJXX b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_DKYW_JC a,QY_JJXX b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--56、借款人财务报表标识变更段
CREATE PROCEDURE PROC_CHECK_QY_JKRCWBB_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRCWBB_JC a,QY_JKRCWBB_BS b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRCWBB_JC a,QY_JKRCWBB_BS b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--57、借款人财务报表基础段
CREATE PROCEDURE PROC_CHECK_QY_JKRCWBB_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
     from QY_JKRCWBB_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
     from QY_JKRCWBB_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--58、借款人概况信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_JKRGK_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRGK_JC a,QY_JKRGK_BS b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRGK_JC a,QY_JKRGK_BS b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--59、借款人概况信息基础段
CREATE PROCEDURE PROC_CHECK_QY_JKRGK_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
        from QY_JKRGK_JC';
    
    ELSE
    
    SET V_CURSQL = 'select 
        autoID
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
        from QY_JKRGK_JC
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--60、借款人概况信息
CREATE PROCEDURE PROC_CHECK_QY_JKRGKXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.instinfo,
        a.dtdate,
        b.autoID
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
        from QY_JKRGK_JC a,QY_JKRGKXX b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.instinfo,
        a.dtdate,
        b.autoID
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
        from QY_JKRGK_JC a,QY_JKRGKXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--61、借款人关注信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_JKRGZ_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRGZ_JC a,QY_JKRGZ_BS b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_JKRGZ_JC a,QY_JKRGZ_BS b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--62、借款人关注信息基础段  
CREATE PROCEDURE PROC_CHECK_QY_JKRGZ_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
     from QY_JKRGZ_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
     from QY_JKRGZ_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--63、借款人资本构成标识变更
CREATE PROCEDURE PROC_CHECK_QY_JKRZBGC_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
from QY_JKRZBGC_JC a,QY_JKRZBGC_BS b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
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
from QY_JKRZBGC_JC a,QY_JKRZBGC_BS b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--64、借款人资本构成基础段
CREATE PROCEDURE PROC_CHECK_QY_JKRZBGC_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select
autoID
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
from QY_JKRZBGC_JC';
    
    ELSE
    
    SET V_CURSQL = 'select
autoID
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
from QY_JKRZBGC_JC
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--65、借款人注册资本情况
CREATE PROCEDURE PROC_CHECK_QY_JKRZCZBQK
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZCZJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_JKRZBGC_JC a,QY_JKRZCZBQK b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.ZCZJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_JKRZBGC_JC a,QY_JKRZCZBQK b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--66、集团公司信息
CREATE PROCEDURE PROC_CHECK_QY_JTGSXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJGSDDKKBM
      ,b.SJGSMC
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_JKRZBGC_JC a,QY_JTGSXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJGSDDKKBM
      ,b.SJGSMC
      ,b.ZZJGDM
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
        from QY_JKRZBGC_JC a,QY_JTGSXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--67、贸易融资业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_MYRZ_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_MYRZ_JC a,QY_MYRZ_BS b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_MYRZ_JC a,QY_MYRZ_BS b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--68、贸易融资业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_MYRZ_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
autoID
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
from QY_MYRZ_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select 
autoID
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
from QY_MYRZ_JC  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--69、展期信息段
CREATE PROCEDURE PROC_CHECK_QY_MYRZ_ZQXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZYWBH
      ,b.ZQCS
      ,b.ZQDQRQ
      ,b.ZQJE
      ,b.ZQQSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID from QY_MYRZ_JC a,QY_MYRZ_ZQXX b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZYWBH
      ,b.ZQCS
      ,b.ZQDQRQ
      ,b.ZQJE
      ,b.ZQQSRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID from QY_MYRZ_JC a,QY_MYRZ_ZQXX b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--70、票据贴现业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_PJTX_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_PJTX_JC a,QY_PJTX_BS b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_PJTX_JC a,QY_PJTX_BS b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--71、票据贴现业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_PJTX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
     from QY_PJTX_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
     from QY_PJTX_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--72、欠息信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_QXXX_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
from QY_QXXX_JC a,QY_QXXX_BS b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,a.instinfo,b.autoID
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
from QY_QXXX_JC a,QY_QXXX_BS b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--73、欠息信息基础段
CREATE PROCEDURE PROC_CHECK_QY_QXXX_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select autoID
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
      ,operationUser from QY_QXXX_JC ';
    
    ELSE
    
    SET V_CURSQL = 'select autoID
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
      ,operationUser from QY_QXXX_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--74、欠息业务信息段
CREATE PROCEDURE PROC_CHECK_QY_QXYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_QXXX_JC a,QY_QXYWXX b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_QXXX_JC a,QY_QXYWXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--75、融资协议金额信息段
CREATE PROCEDURE PROC_CHECK_QY_RZXYJEXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZXYJE
      ,b.RZXYYE
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
     from QY_MYRZ_JC a,QY_RZXYJEXX b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZXYJE
      ,b.RZXYYE
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
     from QY_MYRZ_JC a,QY_RZXYJEXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--76、融资协议信息段
CREATE PROCEDURE PROC_CHECK_QY_RZXYXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.DBBZ
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXXYHM
      ,b.XYSXRQ
      ,b.XYYXZT
      ,b.XYZZRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_MYRZ_JC a,QY_RZXYXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.DBBZ
      ,b.JKRMC
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SXXYHM
      ,b.XYSXRQ
      ,b.XYYXZT
      ,b.XYZZRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_MYRZ_JC a,QY_RZXYXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--77、融资业务还款信息段
CREATE PROCEDURE PROC_CHECK_QY_RZYWHKXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.HKCS
      ,b.HKFS
      ,b.HKJE
      ,b.HKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZYWBH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
        from QY_MYRZ_JC a,QY_RZYWHKXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.HKCS
      ,b.HKFS
      ,b.HKJE
      ,b.HKRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZYWBH
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID 
        from QY_MYRZ_JC a,QY_RZYWHKXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--78、融资业务信息段
CREATE PROCEDURE PROC_CHECK_QY_RZYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZJE
      ,b.RZYE
      ,b.RZYWBH
      ,b.RZYWFSRQ
      ,b.RZYWJSRQ
      ,b.RZYWZL
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_MYRZ_JC a,QY_RZYWXX b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
a.dtdate,
a.instinfo,
b.autoID
      ,b.BZ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.RZJE
      ,b.RZYE
      ,b.RZYWBH
      ,b.RZYWFSRQ
      ,b.RZYWJSRQ
      ,b.RZYWZL
      ,b.SJFL
      ,b.WJFL
      ,b.ZQBZ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_MYRZ_JC a,QY_RZYWXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--79、诉讼信息
CREATE PROCEDURE PROC_CHECK_QY_SSXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_JKRGZ_JC a,QY_SSXX b  
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
        from QY_JKRGZ_JC a,QY_SSXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--80、授信业务信息段
CREATE PROCEDURE PROC_CHECK_QY_SXYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_GKSX_JC a,QY_SXYWXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_GKSX_JC a,QY_SXYWXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO



--81、事业单位收入支出表
CREATE PROCEDURE PROC_CHECK_QY_SYDWSRZCB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.BRZKXJ
      ,b.CZBZSR
      ,b.CZBZZC
      ,b.DFSDWBZ
      ,b.FSDWJK
      ,b.JYFP
      ,b.JYJY
      ,b.JYSR
      ,b.JYSRXJ
      ,b.JYZC
      ,b.JYZCXJ
      ,b.JZZCJJ
      ,b.QTJYFP
      ,b.QTSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SHYQNDSYZC
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRZJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYSRXJ
      ,b.SYZC
      ,b.SYZCXJ
      ,b.TQZYJJ
      ,b.XSSJ_1
      ,b.XSSJ_2
      ,b.YJSDS
      ,b.YQNDJYKS
      ,b.YSWZJSR
      ,b.YSWZJZC
      ,b.ZCSRJY
      ,b.ZCZJ
      ,b.ZKXJ
      ,b.ZKZC
      ,b.ZRSYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWSRZCB b    
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.BRZKXJ
      ,b.CZBZSR
      ,b.CZBZZC
      ,b.DFSDWBZ
      ,b.FSDWJK
      ,b.JYFP
      ,b.JYJY
      ,b.JYSR
      ,b.JYSRXJ
      ,b.JYZC
      ,b.JYZCXJ
      ,b.JZZCJJ
      ,b.QTJYFP
      ,b.QTSR
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SHYQNDSYZC
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRZJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYSRXJ
      ,b.SYZC
      ,b.SYZCXJ
      ,b.TQZYJJ
      ,b.XSSJ_1
      ,b.XSSJ_2
      ,b.YJSDS
      ,b.YQNDJYKS
      ,b.YSWZJSR
      ,b.YSWZJZC
      ,b.ZCSRJY
      ,b.ZCZJ
      ,b.ZKXJ
      ,b.ZKZC
      ,b.ZRSYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWSRZCB b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--82、事业单位资产负债表
CREATE PROCEDURE PROC_CHECK_QY_SYDWZCFZB
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = '  select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.CBJF
      ,b.CCP
      ,b.CL
      ,b.CZBZSR
      ,b.DFSDWBZ
      ,b.DWTZ
      ,b.FSDWJK
      ,b.FZBLZJ
      ,b.FZHJ
      ,b.GDJJ
      ,b.GDZC
      ,b.JJKX
      ,b.JYJY
      ,b.JYSR
      ,b.JYZC
      ,b.JZCHJ
      ,b.JZZCJJ
      ,b.QTSR
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRHJ
      ,b.SYJJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYZC
      ,b.TZJJ
      ,b.WXZC
      ,b.XJ
      ,b.XSSJ
      ,b.YBJJ
      ,b.YFPJ
      ,b.YFZK
      ,b.YFZK_1
      ,b.YHCK
      ,b.YJCZZHK
      ,b.YJSJ
      ,b.YJYFK
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.ZCBLZJ
      ,b.ZCHJ
      ,b.ZCHJ_1
      ,b.ZKZC
      ,b.ZYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWZCFZB b   
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = '  select
        a.dtdate,
        a.instinfo,
        b.autoID
      ,b.BCJF
      ,b.BCZK
      ,b.BRZK
      ,b.CBJF
      ,b.CCP
      ,b.CL
      ,b.CZBZSR
      ,b.DFSDWBZ
      ,b.DWTZ
      ,b.FSDWJK
      ,b.FZBLZJ
      ,b.FZHJ
      ,b.GDJJ
      ,b.GDZC
      ,b.JJKX
      ,b.JYJY
      ,b.JYSR
      ,b.JYZC
      ,b.JZCHJ
      ,b.JZZCJJ
      ,b.QTSR
      ,b.QTYFK
      ,b.QTYSK
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.SJBZSR
      ,b.SJSJZC
      ,b.SRHJ
      ,b.SYJJ
      ,b.SYJY
      ,b.SYSR
      ,b.SYZC
      ,b.TZJJ
      ,b.WXZC
      ,b.XJ
      ,b.XSSJ
      ,b.YBJJ
      ,b.YFPJ
      ,b.YFZK
      ,b.YFZK_1
      ,b.YHCK
      ,b.YJCZZHK
      ,b.YJSJ
      ,b.YJYFK
      ,b.YSPJ
      ,b.YSZK
      ,b.YSZK_1
      ,b.ZCBLZJ
      ,b.ZCHJ
      ,b.ZCHJ_1
      ,b.ZKZC
      ,b.ZYJJ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
    from QY_JKRCWBB_JC a,QY_SYDWZCFZB b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--83、贴现业务信息段
CREATE PROCEDURE PROC_CHECK_QY_TXYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select
a.dtdate,
a.instinfo,
b.autoID
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
from QY_PJTX_JC a,QY_TXYWXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select
a.dtdate,
a.instinfo,
b.autoID
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
from QY_PJTX_JC a,QY_TXYWXX b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--84、信用证业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_XYZYW_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
a.instinfo,
b.autoID
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
from QY_XYZYW_JC a,QY_XYZYW_BS b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
a.instinfo,
b.autoID
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
from QY_XYZYW_JC a,QY_XYZYW_BS b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--85、信用证业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_XYZYW_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = ' select 
  autoID
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
  from QY_XYZYW_JC';
    
    ELSE
    
    SET V_CURSQL = '  select 
  autoID
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
  from QY_XYZYW_JC
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO


--86、信用证业务信息段
CREATE PROCEDURE PROC_CHECK_QY_XYZYWXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,a.instinfo,
b.autoID
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.KZJE
      ,b.KZRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.XYZFKQX
      ,b.XYZYE
      ,b.XYZYXQ
      ,b.XYZZT
      ,b.XYZZXRQ
      ,b.YEBGRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_XYZYW_JC a,QY_XYZYWXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select a.dtdate,a.instinfo,
b.autoID
      ,b.BZ
      ,b.BZJBL
      ,b.DBBZ
      ,b.DKBZ
      ,b.DKKBM
      ,b.JKRMC
      ,b.KZJE
      ,b.KZRQ
      ,b.RPTCheckType
      ,b.RPTFeedbackType
      ,b.RPTSendType
      ,b.WJFL
      ,b.XYZFKQX
      ,b.XYZYE
      ,b.XYZYXQ
      ,b.XYZZT
      ,b.XYZZXRQ
      ,b.YEBGRQ
      ,b.extend1
      ,b.extend2
      ,b.extend3
      ,b.extend4
      ,b.extend5
      ,b.FOREIGNID
from QY_XYZYW_JC a,QY_XYZYWXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--87、银行承兑汇票业务信息标识变更段
CREATE PROCEDURE PROC_CHECK_QY_YHCDHP_BS
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_YHCDHP_JC a,QY_YHCDHP_BS b 
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
        a.dtdate,
        a.instinfo,
        b.autoID
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
    from QY_YHCDHP_JC a,QY_YHCDHP_BS b  
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--88、银行承兑汇票业务信息基础段
CREATE PROCEDURE PROC_CHECK_QY_YHCDHP_JC
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
        autoID
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
    from QY_YHCDHP_JC';
    
    ELSE
    
    SET V_CURSQL = ' select 
        autoID
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
    from QY_YHCDHP_JC 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||'''';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--89、自然人保证合同信息段
CREATE PROCEDURE PROC_CHECK_QY_ZRRBZHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
a.instinfo,
b.autoID
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
from QY_DBXX_JC a,QY_ZRRBZHTXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
a.instinfo,
b.autoID
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
from QY_DBXX_JC a,QY_ZRRBZHTXX b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--90、自然人抵押合同信息段
CREATE PROCEDURE PROC_CHECK_QY_ZRRDYHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select
        a.dtdate,
        a.instinfo,
       b.autoID
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
        from QY_DBXX_JC a,QY_ZRRDYHTXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select
        a.dtdate,
        a.instinfo,
       b.autoID
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
        from QY_DBXX_JC a,QY_ZRRDYHTXX b
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--91、自然人质押合同信息段
CREATE PROCEDURE PROC_CHECK_QY_ZRRZYHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select 
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
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = ' select 
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
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END
GO

--92、质押合同信息段
CREATE PROCEDURE PROC_CHECK_QY_ZYHTXX
(
	IN pAutoDtDate varchar(50),
	IN strInstCode  varchar(50)
)
  DYNAMIC RESULT SETS 1
  LANGUAGE SQL
BEGIN
DECLARE CC VARCHAR(4000);
DECLARE V_CURSQL VARCHAR(4000); 

DECLARE cur CURSOR WITH RETURN TO CALLER FOR CC;

IF pAutoDtDate = '*****' and strInstCode='*****'

	THEN
	
	SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_DBXX_JC a,QY_ZYHTXX b
	where a.autoid=b.foreignid';
    
    ELSE
    
    SET V_CURSQL = 'select a.dtdate,
            a.instinfo,
            b.autoID
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
     from QY_DBXX_JC a,QY_ZYHTXX b 
    where to_Date('''||pAutoDtDate||''', ''yyyy-MM-dd'')=dtDate and Instinfo='''||strInstCode||''' and a.autoid=b.foreignid';
    
	END IF;	
PREPARE CC FROM V_CURSQL;
OPEN cur;
END



