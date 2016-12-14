package zdzsystem.schedule;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import zdzsystem.Constants;
import zdzsystem.dto.AutoDTO_ZDZ_BZXRZHXX;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGWZZJRN;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGZZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import zdzsystem.dto.AutoDTO_ZDZ_CXQQWSNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYDFKCXCLSBNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYDFKKZCLSBNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGWZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGZZZJNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZWSNR;
import zdzsystem.dto.AutoDTO_ZDZ_GLZLZHXX;
import zdzsystem.dto.AutoDTO_ZDZ_GYQYXQXX;
import zdzsystem.dto.AutoDTO_ZDZ_JRJGKZHTNR;
import zdzsystem.dto.AutoDTO_ZDZ_JRJJFKCXHTNR;
import zdzsystem.dto.AutoDTO_ZDZ_JRZCXX;
import zdzsystem.dto.AutoDTO_ZDZ_JTZHXX;
import zdzsystem.dto.AutoDTO_ZDZ_KZCLJG;
import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;
import zdzsystem.dto.AutoDTO_ZDZ_SFQZCSXX;
import zdzsystem.dto.AutoDTO_ZDZ_YXQXX;
import zdzsystem.dto.AutoDTO_ZDZ_ZJWLXX;
import zdzsystem.dto.AutoDTO_ZDZ_ZXDJXX;
import zdzsystem.dto.CustInfo;
import zdzsystem.oxm.CxFk;
import zdzsystem.oxm.CxFkDjxx;
import zdzsystem.oxm.CxFkDjxxList;
import zdzsystem.oxm.CxFkGlxx;
import zdzsystem.oxm.CxFkGlxxList;
import zdzsystem.oxm.CxFkJrxx;
import zdzsystem.oxm.CxFkJrxxList;
import zdzsystem.oxm.CxFkQlxx;
import zdzsystem.oxm.CxFkQlxxList;
import zdzsystem.oxm.CxFkWlxx;
import zdzsystem.oxm.CxFkWlxxList;
import zdzsystem.oxm.CxFkZhxx;
import zdzsystem.oxm.CxHt;
import zdzsystem.oxm.CxHtxx;
import zdzsystem.oxm.Cxclsbxx;
import zdzsystem.oxm.CxqqwsInfo;
import zdzsystem.oxm.Cxqqwsxx;
import zdzsystem.oxm.Cxqqxx;
import zdzsystem.oxm.CxqqzjInfo;
import zdzsystem.oxm.Cxqqzjxx;
import zdzsystem.oxm.FkCxclsbxx;
import zdzsystem.oxm.FkKzclsbxx;
import zdzsystem.oxm.KzFk;
import zdzsystem.oxm.KzFkDjxx;
import zdzsystem.oxm.KzFkDjxxList;
import zdzsystem.oxm.KzFkHz;
import zdzsystem.oxm.KzFkHzxx;
import zdzsystem.oxm.KzFkKzxx;
import zdzsystem.oxm.KzFkQlxx;
import zdzsystem.oxm.KzFkQlxxList;
import zdzsystem.oxm.KzHt;
import zdzsystem.oxm.KzHtxx;
import zdzsystem.oxm.Kzclsbxx;
import zdzsystem.oxm.KzqqwsInfo;
import zdzsystem.oxm.Kzqqwsxx;
import zdzsystem.oxm.Kzqqxx;
import zdzsystem.oxm.KzqqzjInfo;
import zdzsystem.oxm.Kzqqzjxx;
import zdzsystem.utils.BeanMapper;
import zdzsystem.utils.Exceptions;
import zdzsystem.utils.XStreamUtils;
import zdzsystem.utils.ZipUtils;
import zdzsystem.utils.pdf.FeedBack;
import zdzsystem.utils.pdf.FileWriter;
import zdzsystem.utils.pdf.IFileWriter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author veggieg
 * @since 2016-09-23 14:41
 */
public class ScannerJob {
    // private static final Logger logger = Logger.getLogger(ScannerJob.class);
    private final static Logger logger = ApplicationManager.getActionExcuteLog();

    private UserInfo userInfo;

    private InstInfo instInfo;
    
    private IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");

    private IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");

    private IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

    private IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");

    private IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");

    /**
     * 自动扫描相关表判断是否有需要反馈的报文
     *
     */
    public void uploadScanner() {
        try {
        	
        	//判断上传路径是否存在
        	if(!GetPathHelper.GetUploadPathIsExsist()){
        		return;
        	}
        	
            // 0. 清空上传临时文件夹
            FileUtils.cleanDirectory(new File(GetPathHelper.GetPathNoSlash("uploadPathTmp")));

            // 1. 查询反馈
            processCxfk();

            // 2. 控制反馈
            processKzFk();

        } catch (Exception e) {
            logger.error("---> DEAL ERROR :" + Exceptions.getStackTraceAsString(e));

            e.printStackTrace();
        }

    }

    /**
     * 自动扫描下载目录是否有新文件
     *
     * @see Constants#BUSINESS_TYPE_CXQQ
     * @see Constants#BUSINESS_TYPE_CXHZ
     * @see Constants#BUSINESS_TYPE_KZQQ
     * @see Constants#BUSINESS_TYPE_KZHZ
     */
    public void downloadScanner() throws IOException {
        List<File> destFiles = Lists.newArrayList();
        List<File> successFiles = Lists.newArrayList();
        try {
        	
        	//判断下载路径是否存在
        	if(!GetPathHelper.GetDownloadPathIsExsist()){
        		return;
        	}
        	
            // 遍历下载目录 zip 文件
            Collection<File> files = FileUtils.listFiles(new File(GetPathHelper.GetPath("downloadPath")), new String[]{"zip"}, false);
            
            // 下载目录 zip 文件移动至存储目录
            for (File file : files) {
                File destFile = new File(GetPathHelper.GetPath("storagePath") + file.getName());
                Files.move(file, destFile);
                destFiles.add(destFile);
            }

            if (!files.isEmpty()) {
                instInfo = (InstInfo) singleObjectFindByIdDao.paramObjectResultExecute(
                        new Object[]{InstInfo.class.getName(), GetPathHelper.GetConst("INST_CODE"), null});

                // 尚未登陆， session 中尚未有任何操作员信息。
                // userInfo = (UserInfo) SecurityContext.getInstance().getLoginInfo().getTag();
            }

            for (File destFile : destFiles) {
                List<String> xmls = ZipUtils.unZip(destFile, GetPathHelper.GetPathNoSlash("storagePath"));
                for (String xml : xmls) {
                    String xmlPath = GetPathHelper.GetPath("storagePath") + xml;
                    if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_CXQQ)) {
                        String batchNo = xml.substring(2 + 17, xml.length() - 4);// 批次号 QA + 银行代码 [17位] + 请求批次号 [30位]

                        if (xml.startsWith(Constants.FILE_TYPE_QA)) {
                            processCxqqxx(xmlPath, batchNo);
                        } else if (xml.startsWith(Constants.FILE_TYPE_QI)) {
                            processCxqqwsxx(xmlPath, batchNo);
                        } else if (xml.startsWith(Constants.FILE_TYPE_QC)) {
                            processCxqqzjxx(xmlPath, batchNo);
                        } else {
                            // omitted
                        }
                    } else if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_CXFK)) {
                        // omitted
                    } else if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_CXHZ)) {
                        String batchNo = xml.substring(3 + 17, xml.length() - 4); // 批次号 QNR +银行代码 [17位] + 请求批次号 [30位]

                        if (xml.startsWith(Constants.FILE_TYPE_QNR)) {
                            processFkCxclsbxx(xmlPath, batchNo);
                        }
                    } else if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_KZQQ)) {
                        String batchNo = xml.substring(2 + 17, xml.length() - 4);

                        if (xml.startsWith(Constants.FILE_TYPE_QA)) {
                            processKzqqxx(xmlPath, batchNo);
                        } else if (xml.startsWith(Constants.FILE_TYPE_QI)) {
                            processKzqqwsxx(xmlPath, batchNo);
                        } else if (xml.startsWith(Constants.FILE_TYPE_QC)) {
                            processKzqqzjxx(xmlPath, batchNo);
                        } else {
                            // omitted
                        }
                    } else if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_KZFK)) {
                        // omitted
                    } else if (destFile.getName().startsWith(Constants.BUSINESS_TYPE_KZHZ)) {
                        String batchNo = xml.substring(3 + 17, xml.length() - 4);
                        if (xml.startsWith(Constants.FILE_TYPE_QNR)) {
                            processFkKzclsbxx(xmlPath, batchNo);
                        }
                    } else {
                        // omitted
                    }

                    // 删除处理后的 xml 文件
                    FileUtils.forceDelete(new File(xmlPath));
                }

                successFiles.add(destFile);// 处理成功的文件
            }
        } catch (Exception e) {
            logger.error("---> DEAL ERROR :" + Exceptions.getStackTraceAsString(e));

            // 出错后, 未处理的文件移动到 ERROR 文件夹下
            destFiles.removeAll(successFiles);
            for (File file : destFiles) {
                Files.move(file, new File(GetPathHelper.GetPath("errorPath") + file.getName()));
            }

            // 删除存储目录下的所有 xml 文件(解压的临时文件)
            Collection<File> xmlFiles = FileUtils.listFiles(new File(GetPathHelper.GetPath("storagePath")), new String[]{"xml"}, Boolean.FALSE);
            for (File xmlFile : xmlFiles) {
                FileUtils.forceDelete(xmlFile);
            }

            e.printStackTrace();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void processCxfk() throws Exception {
        Multimap<String, CxFk> cxFkMultimap = buildCxFkMultimap(); // 金融机构反馈查询结果信息 CXFK + QR

        Multimap<String, CxHt> cxHtMultimap = buildCxHtMultimap(); // 金融机构反馈回退信息 CXFK + QNA

        Set<String> batchNumberSet = new HashSet<String>();
        for(String k :cxFkMultimap.keySet()){
        	batchNumberSet.add(k);
        }
        for(String k :cxHtMultimap.keySet()){
        	batchNumberSet.add(k);
        }

        // 查控反馈文件格式：QR+银行代码【17位】+请求批次号【30位】+序号【12位，000000000001-999999999999】.xml
        // QNA+银行代码【17位】+请求批次号【30位】.xml
        // 打包规则：将上述两类文件打包，一个批次一个包: 业务类型【CXFK】+银行代码【17位】+请求批次号【30位】.ZIP
        for (String batchNumber : batchNumberSet) {
            String basePath = GetPathHelper.GetPath("uploadPathTmp") + batchNumber + File.separator;
            File file = new File(basePath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            for (CxFk cxFk : cxFkMultimap.get(batchNumber)) {
                String xml = XStreamUtils.marshal(cxFk);
                String name = Constants.FILE_TYPE_QR//
                        + GetPathHelper.GetConst("BANK_CODE")//
                        + batchNumber + String.format("%1$012d", 1)// 实际同一个批次号只有一条记录
                        + ".xml";

                Files.write(xml, new File(basePath + name), Charsets.UTF_8);
            }

            for (CxHt cxHt : cxHtMultimap.get(batchNumber)) {
                String xml = XStreamUtils.marshal(cxHt);
                String name = Constants.FILE_TYPE_QNA//
                        + GetPathHelper.GetConst("BANK_CODE")//
                        + batchNumber;

                Files.write(xml, new File(basePath + name), Charsets.UTF_8);
            }

            String zipName = Constants.BUSINESS_TYPE_CXFK//
                    + GetPathHelper.GetConst("BANK_CODE")//
                    + batchNumber + ".zip";
            ZipUtils.zip(basePath, GetPathHelper.GetPath("uploadPath") + zipName);

            FileUtils.forceDelete(new File(basePath));

            // 更新字段信息 RPTSendType = '2'

            for (CxFk cxFk : cxFkMultimap.get(batchNumber)) {
                for (CxFkZhxx zhxx : cxFk.getZhxxList()) {
                    AutoDTO_ZDZ_JTZHXX jtzhxx = (AutoDTO_ZDZ_JTZHXX) singleObjectFindByIdDao.paramObjectResultExecute(
                            new Object[]{AutoDTO_ZDZ_JTZHXX.class.getName(), zhxx.getAutoID(), null});
                    jtzhxx.setRPTSendType("2");
                    singleObjectUpdateDao.paramVoidResultExecute(new Object[]{jtzhxx, null});

                    // 金融资产信息
                    if (zhxx.getJrxxList() != null && !zhxx.getJrxxList().isEmpty()) {
                        List<CxFkJrxx> jrxxList = zhxx.getJrxxList().get(0).getJrxxList();
                        for (CxFkJrxx cxFkJrxx : jrxxList) {
                            AutoDTO_ZDZ_JRZCXX jrzcxx = (AutoDTO_ZDZ_JRZCXX) singleObjectFindByIdDao.paramObjectResultExecute(
                                    new Object[]{AutoDTO_ZDZ_JRZCXX.class.getName(), cxFkJrxx.getAutoID(), null});
                            jrzcxx.setRPTSendType("2");
                            singleObjectUpdateDao.paramVoidResultExecute(new Object[]{jrzcxx, null});
                        }
                    }

                    // 司法强制措施信息
                    if (zhxx.getDjxxList() != null && !zhxx.getDjxxList().isEmpty()) {
                        List<CxFkDjxx> djxxList = zhxx.getDjxxList().get(0).getDjxxList();
                        for (CxFkDjxx cxFkDjxx : djxxList) {
                            AutoDTO_ZDZ_SFQZCSXX sfqzcsxx = (AutoDTO_ZDZ_SFQZCSXX) singleObjectFindByIdDao.paramObjectResultExecute(
                                    new Object[]{AutoDTO_ZDZ_SFQZCSXX.class.getName(), cxFkDjxx.getAutoID(), null});
                            sfqzcsxx.setRPTSendType("2");
                            singleObjectUpdateDao.paramVoidResultExecute(new Object[]{sfqzcsxx, null});
                        }
                    }

                    // 资金往来（交易）信息
                    if (zhxx.getWlxxList() != null && !zhxx.getWlxxList().isEmpty()) {
                        List<CxFkWlxx> wlxxList = zhxx.getWlxxList().get(0).getWlxxList();
                        for (CxFkWlxx cxFkWlxx : wlxxList) {
                            AutoDTO_ZDZ_ZJWLXX zjwlxx = (AutoDTO_ZDZ_ZJWLXX) singleObjectFindByIdDao.paramObjectResultExecute(
                                    new Object[]{AutoDTO_ZDZ_ZJWLXX.class.getName(), cxFkWlxx.getAutoID(), null});
                            zjwlxx.setRPTSendType("2");
                            singleObjectUpdateDao.paramVoidResultExecute(new Object[]{zjwlxx, null});
                        }
                    }

                    // 共有权/优先权信息
                    if (zhxx.getQlxxList() != null && !zhxx.getQlxxList().isEmpty()) {
                        List<CxFkQlxx> qlxxList = zhxx.getQlxxList().get(0).getQlxxList();
                        for (CxFkQlxx cxFkQlxx : qlxxList) {
                            AutoDTO_ZDZ_GYQYXQXX gyqyxqxx = (AutoDTO_ZDZ_GYQYXQXX) singleObjectFindByIdDao.paramObjectResultExecute(
                                    new Object[]{AutoDTO_ZDZ_GYQYXQXX.class.getName(), cxFkQlxx.getAutoID(), null});
                            gyqyxqxx.setRPTSendType("2");
                            singleObjectUpdateDao.paramVoidResultExecute(new Object[]{gyqyxqxx, null});
                        }
                    }

                    // 关联子类账户信息
                    if (zhxx.getGlxxList() != null && !zhxx.getGlxxList().isEmpty()) {
                        List<CxFkGlxx> glxxList = zhxx.getGlxxList().get(0).getGlxxList();
                        for (CxFkGlxx cxFkGlxx : glxxList) {
                            AutoDTO_ZDZ_GLZLZHXX glzlzhxx = (AutoDTO_ZDZ_GLZLZHXX) singleObjectFindByIdDao.paramObjectResultExecute(
                                    new Object[]{AutoDTO_ZDZ_GLZLZHXX.class.getName(), cxFkGlxx.getAutoID(), null});
                            glzlzhxx.setRPTSendType("2");
                            singleObjectUpdateDao.paramVoidResultExecute(new Object[]{glzlzhxx, null});
                        }
                    }
                }
            }

            for (CxHt cxHt : cxHtMultimap.get(batchNumber)) {
                List<CxHtxx> htxxList = cxHt.getHtxxList();

                for (CxHtxx cxHtxx : htxxList) {
                    AutoDTO_ZDZ_JRJJFKCXHTNR jrjjfkcxhtnr = (AutoDTO_ZDZ_JRJJFKCXHTNR) singleObjectFindByIdDao.paramObjectResultExecute(
                            new Object[]{AutoDTO_ZDZ_JRJJFKCXHTNR.class.getName(), cxHtxx.getAutoID(), null});
                    jrjjfkcxhtnr.setRPTSendType("2");
                    singleObjectUpdateDao.paramVoidResultExecute(new Object[]{jrjjfkcxhtnr, null});
                }
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void processKzFk() throws Exception {   	
        DetachedCriteria detachedCriteria = null;

        detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZCLJG"));
        detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
        detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
        List<AutoDTO_ZDZ_KZCLJG> kzcljgList = (List<AutoDTO_ZDZ_KZCLJG>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

        // 鉴于法院端对于控制（主要是冻结）的时效性要求，控制申请需根据请求批次里的每个请求单号进行反馈
        // 控制请求内容/被执行人账号信息#表单号码 + 被执行人账户信息#序号 -> 控制处理结果#表单号码 + 控制处理结果#序号
        Multimap<String, AutoDTO_ZDZ_KZCLJG> kzcljgMultimap = ArrayListMultimap.create();
        for (AutoDTO_ZDZ_KZCLJG kzcljg : kzcljgList) {
            kzcljgMultimap.put(StringUtils.trimToEmpty(kzcljg.getBDHM().getBDHM()), kzcljg);
        }

        detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_JRJGKZHTNR"));
        detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
        detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
        List<AutoDTO_ZDZ_JRJGKZHTNR> jrjgkzhtnrList = (List<AutoDTO_ZDZ_JRJGKZHTNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

        Multimap<String, AutoDTO_ZDZ_JRJGKZHTNR> jrjgkzhtnrMultimap = ArrayListMultimap.create();
        for (AutoDTO_ZDZ_JRJGKZHTNR jrjgkzhtnr : jrjgkzhtnrList) {
            jrjgkzhtnrMultimap.put(StringUtils.trimToEmpty(jrjgkzhtnr.getBDHM().getBDHM()), jrjgkzhtnr);
        }

        Set<String> bdhmSet = new HashSet<String>();
        for(String k :kzcljgMultimap.keySet()){
        	bdhmSet.add(k);
        }
        for(String k :jrjgkzhtnrMultimap.keySet()){
        	bdhmSet.add(k);
        }
        	
        for (String bdhm : bdhmSet) {
            KzFk kzFk = new KzFk();
            KzFkHz kzFkHz = new KzFkHz();
            KzHt kzHt = new KzHt();

            detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR"));
            detachedCriteria.add(Restrictions.eq("BDHM", bdhm));
            List<AutoDTO_ZDZ_KZQQJTNR> kzqqjtnrs = (List<AutoDTO_ZDZ_KZQQJTNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
            AutoDTO_ZDZ_KZQQJTNR kzqqjtnr = kzqqjtnrs.get(0);

            String batchNumber = StringUtils.trimToEmpty(kzqqjtnr.getBatchNumber());// 去空格, 如果前台输入有空格，导致文件名称和路径会报错.

            for (AutoDTO_ZDZ_KZCLJG kzcljg : kzcljgMultimap.get(bdhm)) {
                // AutoDTO_ZDZ_KZQQJTNR kzqqjtnr = kzcljg.getBDHM();

                KzFkKzxx kzxx = new KzFkKzxx();
                BeanMapper.copy(kzcljg, kzxx);
                kzxx.setBdhmContent(kzqqjtnr.getBDHM());

                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_BZXRZHXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", kzqqjtnr));
                detachedCriteria.add(Restrictions.eq("CCXH", kzcljg.getCCXH()));
                List<AutoDTO_ZDZ_BZXRZHXX> bzxrzhxxes = (List<AutoDTO_ZDZ_BZXRZHXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
                AutoDTO_ZDZ_BZXRZHXX bzxrzhxx = bzxrzhxxes.get(0);// 1 - 1
                kzxx.setKzlx(bzxrzhxx.getKZLX());
                kzxx.setKzcs(bzxrzhxx.getKZCS());

                // 在先冻结信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_ZXDJXX"));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                detachedCriteria.add(Restrictions.eq("BDHM", kzqqjtnr));
                detachedCriteria.add(Restrictions.eq("CCXH", kzcljg.getCCXH()));
                List<AutoDTO_ZDZ_ZXDJXX> zxdjxxes = (List<AutoDTO_ZDZ_ZXDJXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (zxdjxxes != null && !zxdjxxes.isEmpty()) {
                    KzFkDjxxList djxxList = new KzFkDjxxList();
                    djxxList.setDjxxList(BeanMapper.mapList(zxdjxxes, KzFkDjxx.class));
                    kzxx.setDjxxLists(Lists.<KzFkDjxxList>newArrayList(djxxList));
                }

                // 优先权信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_YXQXX"));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                detachedCriteria.add(Restrictions.eq("BDHM", kzqqjtnr));
                detachedCriteria.add(Restrictions.eq("CCXH", kzcljg.getCCXH()));
                List<AutoDTO_ZDZ_YXQXX> yxqxxes = (List<AutoDTO_ZDZ_YXQXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (yxqxxes != null && !yxqxxes.isEmpty()) {
                    KzFkQlxxList qlxxList = new KzFkQlxxList();
                    qlxxList.setQlxxList(BeanMapper.mapList(yxqxxes, KzFkQlxx.class));
                    kzxx.setQlxxLists(Lists.newArrayList(qlxxList));
                }

                if (kzFk.getKzxxList() == null || kzFk.getKzxxList().isEmpty()) {
                    kzFk.setKzxxList(Lists.newArrayList(kzxx));
                } else {
                    kzFk.getKzxxList().add(kzxx);
                }
            }

            for (AutoDTO_ZDZ_JRJGKZHTNR jrjgkzhtnr : jrjgkzhtnrMultimap.get(bdhm)) {// 一条记录....?
                KzHtxx htxx = new KzHtxx();
                BeanMapper.copy(jrjgkzhtnr, htxx);
                htxx.setBdhmContent(bdhm);

                if (kzHt.getHtxxList() == null || kzHt.getHtxxList().isEmpty()) {
                    kzHt.setHtxxList(Lists.newArrayList(htxx));
                } else {
                    kzHt.getHtxxList().add(htxx);
                }
            }

            // 文件处理...

            String basePath = GetPathHelper.GetPath("uploadPathTmp") + bdhm + File.separator;
            File file = new File(basePath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            if (kzFk.getKzxxList() != null && !kzFk.getKzxxList().isEmpty()) {
                FeedBack feedBack = new FeedBack();
                feedBack.setKzFk(kzFk);
                feedBack.setFymc(kzqqjtnr.getFYMC());
                feedBack.setCkwh(kzqqjtnr.getAH());
                feedBack.setCktz(kzqqjtnr.getCKH());
                feedBack.setYhmc(GetPathHelper.GetConst("BANK_NAME"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                feedBack.setWssj(sdf.format(new Date()));

                feedBack.setBzxrxm(kzqqjtnr.getXM());

                IFileWriter fileWriter = FileWriter.getFileWriter();
                fileWriter.setDzyzPath(GetPathHelper.GetPathNoSlash("dzyzPath"));
                // fileWriter.setCreateFilePath();// pdf 存放路径
                byte[] content = fileWriter.write(feedBack);

                KzFkHzxx hzxx = new KzFkHzxx();
                hzxx.setBdhm(bdhm);
                hzxx.setWjmc(bdhm);//TODO 暂取 bdhm
                hzxx.setWjlx("pdf");
                hzxx.setWslb("回执");
                hzxx.setWsnr(Base64.encodeBase64String(content));
                hzxx.setMd5(DigestUtils.md5Hex(content));

                kzFkHz.setHzxxList(Lists.newArrayList(hzxx));

                // 查控反馈文件格式: QR+银行代码【17位】+请求批次号【30位】+请求单号【30位】.xml
                String xml = XStreamUtils.marshal(kzFk);
                String name = Constants.FILE_TYPE_QR //
                        + batchNumber//
                        + bdhm//
                        + ".xml";
                Files.write(xml, new File(basePath + name), Charsets.UTF_8);

                // 回执信息文件命名格式: QRI+银行代码【17位】+请求批次号【30位】+请求单号【30位】.xml
                xml = XStreamUtils.marshal(kzFkHz);
                name = Constants.FILE_TYPE_QRI//
                        + batchNumber//
                        + bdhm//
                        + ".xml";
                Files.write(xml, new File(basePath + name), Charsets.UTF_8);
            }

            if (kzHt.getHtxxList() != null && !kzHt.getHtxxList().isEmpty()) {
                // 查控回退文格式: QNA+银行代码【17位】+请求批次号【30位】+请求单号【30位】.xml
                String xml = XStreamUtils.marshal(kzHt);
                String name = Constants.FILE_TYPE_QNA//
                        + batchNumber//
                        + bdhm//
                        + ".xml";
                Files.write(xml, new File(basePath + name), Charsets.UTF_8);
            }

            // 业务类型【KZFK】+银行代码【17位】+请求批次号【30位】+请求单号【30位】.ZIP
            String zipName = Constants.BUSINESS_TYPE_KZFK//
                    + GetPathHelper.GetConst("BANK_CODE")//
                    + batchNumber//
                    + bdhm//
                    + ".zip";
            ZipUtils.zip(basePath, GetPathHelper.GetPath("uploadPath") + zipName);

            FileUtils.forceDelete(new File(basePath));

            // 更新 RPTSendType = '2'

            for (AutoDTO_ZDZ_KZCLJG kzcljg : kzcljgMultimap.get(bdhm)) {
                kzcljg.setRPTSendType("2");
                singleObjectUpdateDao.paramVoidResultExecute(new Object[]{kzcljg, null});

                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_ZXDJXX"));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                detachedCriteria.add(Restrictions.eq("BDHM", kzqqjtnr));
                detachedCriteria.add(Restrictions.eq("CCXH", kzcljg.getCCXH()));
                List<AutoDTO_ZDZ_ZXDJXX> zxdjxxes = (List<AutoDTO_ZDZ_ZXDJXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
                for (AutoDTO_ZDZ_ZXDJXX zxdjxx : zxdjxxes) {
                    zxdjxx.setRPTSendType("2");
                    singleObjectUpdateDao.paramVoidResultExecute(new Object[]{zxdjxx, null});
                }

                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_YXQXX"));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                detachedCriteria.add(Restrictions.eq("BDHM", kzqqjtnr));
                detachedCriteria.add(Restrictions.eq("CCXH", kzcljg.getCCXH()));
                List<AutoDTO_ZDZ_YXQXX> yxqxxes = (List<AutoDTO_ZDZ_YXQXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
                for (AutoDTO_ZDZ_YXQXX yxqxx : yxqxxes) {
                    yxqxx.setRPTSendType("2");
                    singleObjectUpdateDao.paramVoidResultExecute(new Object[]{yxqxx, null});
                }
            }

            for (AutoDTO_ZDZ_JRJGKZHTNR jrjgkzhtnr : jrjgkzhtnrMultimap.get(bdhm)) {
                jrjgkzhtnr.setRPTSendType("2");
                singleObjectUpdateDao.paramVoidResultExecute(new Object[]{jrjgkzhtnr, null});
            }
        }
    }

    /**
     * 金融机构反馈查询结果信息 CXFK + QR
     *
     * @return
     * @throws Exception
     */
    private Multimap<String, CxFk> buildCxFkMultimap() throws Exception {
        DetachedCriteria detachedCriteria = null;

        detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_JTZHXX"));
        detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));// 审核通过
        detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));// 未报送
        List<AutoDTO_ZDZ_JTZHXX> jtzhxxAll = (List<AutoDTO_ZDZ_JTZHXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

        // 根据查询请求中被查询人的证件类型及证件号码，反馈被查询人在金融机构的开户信息，本项信息必须反馈，
        // 若无开户信息仍需反馈一条记录即“查无开户信息”
        Collection<AutoDTO_ZDZ_JTZHXX> filter = Collections2.filter(jtzhxxAll, new Predicate<AutoDTO_ZDZ_JTZHXX>() {
            @Override
            public boolean apply(AutoDTO_ZDZ_JTZHXX input) {
                DetachedCriteria dc = null;
                try {
                    dc = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR"));
                    dc.add(Restrictions.eq("BatchNumber", input.getBatchNumber()));
                    int qq = (Integer) singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{dc, null});

                    dc = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_JTZHXX"));
                    dc.add(Restrictions.eq("BatchNumber", input.getBatchNumber()));
                    dc.add(Restrictions.eq("RPTVerifyType", 2));
                    dc.add(Restrictions.eq("RPTSendType", 1));
                    int fk = (Integer) singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{dc, null});

                    return qq != fk;// 确保所有反馈录入完成, 否则跳过
                } catch (Exception e) {
                    logger.error("---> Error:" + Exceptions.getStackTraceAsString(e));
                    return true;
                }
            }
        });

        // 按批次号分组 (打包)
        Multimap<String, AutoDTO_ZDZ_JTZHXX> jtzhxxMultimap = ArrayListMultimap.create();
        for (AutoDTO_ZDZ_JTZHXX jtzhxx : filter) {
            jtzhxxMultimap.put(StringUtils.trimToEmpty(jtzhxx.getBatchNumber()), jtzhxx);
        }

        // 组装成 multimap key -> batchNumber value -> CxFk
        Multimap<String, CxFk> cxFkMultimap = ArrayListMultimap.create();
        for (String batchNumber : jtzhxxMultimap.keySet()) {
            Collection<AutoDTO_ZDZ_JTZHXX> jtzhxxes = jtzhxxMultimap.get(batchNumber);

            List<CxFkZhxx> zhxxList = Lists.newArrayList();
            for (AutoDTO_ZDZ_JTZHXX jtzhxx : jtzhxxes) {
                CxFkZhxx zhxx = new CxFkZhxx();
                BeanMapper.copy(jtzhxx, zhxx);
                zhxx.setBdhmContent(StringUtils.trimToEmpty(jtzhxx.getBDHM().getBDHM()));

                // 金融资产信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_JRZCXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", jtzhxx.getBDHM()));
                detachedCriteria.add(Restrictions.eq("CCXH", jtzhxx.getCCXH()));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                List<AutoDTO_ZDZ_JRZCXX> jrzcxxs = (List<AutoDTO_ZDZ_JRZCXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (jrzcxxs != null && !jrzcxxs.isEmpty()) {
                    CxFkJrxxList cxFkJrxxList = new CxFkJrxxList();
                    cxFkJrxxList.setJrxxList(BeanMapper.mapList(jrzcxxs, CxFkJrxx.class));
                    zhxx.setJrxxList(Lists.newArrayList(cxFkJrxxList));
                }

                // 司法强制措施信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_SFQZCSXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", jtzhxx.getBDHM()));
                detachedCriteria.add(Restrictions.eq("CCXH", jtzhxx.getCCXH()));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                List<AutoDTO_ZDZ_SFQZCSXX> sfqzcsxxes = (List<AutoDTO_ZDZ_SFQZCSXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (sfqzcsxxes != null && !sfqzcsxxes.isEmpty()) {
                    CxFkDjxxList cxFkDjxxList = new CxFkDjxxList();
                    cxFkDjxxList.setDjxxList(BeanMapper.mapList(sfqzcsxxes, CxFkDjxx.class));
                    zhxx.setDjxxList(Lists.newArrayList(cxFkDjxxList));
                }

                // 资金往来（交易）信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_ZJWLXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", jtzhxx.getBDHM()));
                detachedCriteria.add(Restrictions.eq("CCXH", jtzhxx.getCCXH()));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                List<AutoDTO_ZDZ_ZJWLXX> zjwlxxes = (List<AutoDTO_ZDZ_ZJWLXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (zjwlxxes != null && !zjwlxxes.isEmpty()) {
                    CxFkWlxxList cxFkWlxxList = new CxFkWlxxList();
                    cxFkWlxxList.setWlxxList(BeanMapper.mapList(zjwlxxes, CxFkWlxx.class));
                    zhxx.setWlxxList(Lists.newArrayList(cxFkWlxxList));
                }

                // 共有权/优先权信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_GYQYXQXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", jtzhxx.getBDHM()));
                detachedCriteria.add(Restrictions.eq("CCXH", jtzhxx.getCCXH()));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                List<AutoDTO_ZDZ_GYQYXQXX> gyqyxqxxes = (List<AutoDTO_ZDZ_GYQYXQXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (gyqyxqxxes != null && !gyqyxqxxes.isEmpty()) {
                    CxFkQlxxList cxFkQlxxList = new CxFkQlxxList();
                    cxFkQlxxList.setQlxxList(BeanMapper.mapList(gyqyxqxxes, CxFkQlxx.class));
                    zhxx.setQlxxList(Lists.newArrayList(cxFkQlxxList));
                }

                // 关联子类账户信息
                detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_GLZLZHXX"));
                detachedCriteria.add(Restrictions.eq("BDHM", jtzhxx.getBDHM()));
                detachedCriteria.add(Restrictions.eq("CCXH", jtzhxx.getCCXH()));
                detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
                detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
                List<AutoDTO_ZDZ_GLZLZHXX> glzlzhxxes = (List<AutoDTO_ZDZ_GLZLZHXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

                if (glzlzhxxes != null && !glzlzhxxes.isEmpty()) {
                    CxFkGlxxList cxFkGlxxList = new CxFkGlxxList();
                    cxFkGlxxList.setGlxxList(BeanMapper.mapList(glzlzhxxes, CxFkGlxx.class));
                    zhxx.setGlxxList(Lists.newArrayList(cxFkGlxxList));
                }

                zhxxList.add(zhxx);
            }

            CxFk cxFk = new CxFk();
            cxFk.setZhxxList(zhxxList);

            cxFkMultimap.put(batchNumber, cxFk);
        }

        return cxFkMultimap;
    }

    /**
     * 金融机构反馈回退信息 CXFK + QNA
     *
     * @return
     * @throws Exception
     */
    private Multimap<String, CxHt> buildCxHtMultimap() throws Exception {
        DetachedCriteria detachedCriteria = null;

        detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_JRJJFKCXHTNR"));
        detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));// 审核通过
        detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));// 未报送
        List<AutoDTO_ZDZ_JRJJFKCXHTNR> jrjjfkcxhtnrAll = (List<AutoDTO_ZDZ_JRJJFKCXHTNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

        // 按批次号分组 (打包)
        Multimap<String, AutoDTO_ZDZ_JRJJFKCXHTNR> jrjjfkcxhtnrMultimap = ArrayListMultimap.create();
        for (AutoDTO_ZDZ_JRJJFKCXHTNR jrjjfkcxhtnr : jrjjfkcxhtnrAll) {
            jrjjfkcxhtnrMultimap.put(jrjjfkcxhtnr.getBatchNumber(), jrjjfkcxhtnr);
        }

        Multimap<String, CxHt> cxHtMultimap = ArrayListMultimap.create();
        for (String batchNumber : cxHtMultimap.keySet()) {
            Collection<AutoDTO_ZDZ_JRJJFKCXHTNR> cxHts = jrjjfkcxhtnrMultimap.get(batchNumber);

            List<CxHtxx> cxHtxxList = Lists.newArrayList();
            for (AutoDTO_ZDZ_JRJJFKCXHTNR jrjjfkcxhtnr : cxHts) {
                CxHtxx htxx = new CxHtxx();
                BeanMapper.copy(jrjjfkcxhtnr, htxx);
                htxx.setBdhmContent(jrjjfkcxhtnr.getBDHM().getBDHM());

                cxHtxxList.add(htxx);
            }

            CxHt cxHt = new CxHt();
            cxHt.setHtxxList(cxHtxxList);

            cxHtMultimap.put(batchNumber, cxHt);
        }

        return cxHtMultimap;
    }

    /**
     * 法院提供的司法查询请求信息 CXQQ + QA
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processCxqqxx(String xmlPath, String batchNo) throws Exception {
        Cxqqxx cxqqxx = (Cxqqxx) XStreamUtils.unmarshal(xmlPath, Cxqqxx.class);
        for (AutoDTO_ZDZ_CXQQNR cxqqnr : cxqqxx.getCxqqList()) {
            cxqqnr.setBatchNumber(batchNo);

            cxqqnr.setRPTCheckType("1");
            cxqqnr.setRPTSubmitStatus("1");
            cxqqnr.setRPTVerifyType("1");
            cxqqnr.setRPTSendType("1");
            cxqqnr.setRPTFeedbackType("1");

            cxqqnr.setDtDate(GetPathHelper.GetConst("DT_DATE"));
            cxqqnr.setInstInfo(instInfo);
            cxqqnr.setOperationUser(userInfo);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.CustInfo"));
            detachedCriteria.add(Restrictions.eq("custname", cxqqnr.getXM()));
            detachedCriteria.add(Restrictions.eq("custcode", cxqqnr.getZJLX()));
            detachedCriteria.add(Restrictions.eq("custnum", cxqqnr.getDSRZJHM()));
            List<CustInfo> custInfos = (List<CustInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            cxqqnr.setIsMatchCustInfo(custInfos.isEmpty() ? "2" : "1");

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{cxqqnr, null});
        }
    }

    /**
     * 法院提供的文书信息 CXQQ + QI
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processCxqqwsxx(String xmlPath, String batchNo) throws Exception {
        Cxqqwsxx cxqqwsxx = (Cxqqwsxx) XStreamUtils.unmarshal(xmlPath, Cxqqwsxx.class);
        for (CxqqwsInfo cxqqwsInfo : cxqqwsxx.getWsinfoList()) {
            AutoDTO_ZDZ_CXQQWSNR cxqqwsnr = new AutoDTO_ZDZ_CXQQWSNR();
            BeanMapper.copy(cxqqwsInfo, cxqqwsnr);

            cxqqwsnr.setBatchNumber(batchNo);
            cxqqwsnr.setWSNR(Base64.decodeBase64(cxqqwsInfo.getWsnrContent().getBytes(Charsets.UTF_8)));

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{cxqqwsnr, null});
        }
    }

    /**
     * 法院提供的证件信息 CXQQ + QC
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processCxqqzjxx(String xmlPath, String batchNo) throws Exception {
        Cxqqzjxx cxqqzjxx = (Cxqqzjxx) XStreamUtils.unmarshal(xmlPath, Cxqqzjxx.class);
        for (CxqqzjInfo cxqqzjInfo : cxqqzjxx.getZjinfoList()) {
            if (StringUtils.isNotEmpty(cxqqzjInfo.getGzzContent())//
                    && StringUtils.isNotEmpty(cxqqzjInfo.getGzzwjgs())//
                    && StringUtils.isNotEmpty(cxqqzjInfo.getGzzbm())) {//
                AutoDTO_ZDZ_CXQQFGGZZZJNR cxqqfggzzzjnr = new AutoDTO_ZDZ_CXQQFGGZZZJNR();
                BeanMapper.copy(cxqqzjInfo, cxqqfggzzzjnr);
                cxqqfggzzzjnr.setBatchNumber(batchNo);
                cxqqfggzzzjnr.setGZZ(Base64.decodeBase64(cxqqzjInfo.getGzzContent().getBytes(Charsets.UTF_8)));

                singleObjectSaveDao.paramVoidResultExecute(new Object[]{cxqqfggzzzjnr, null});
            }

            if (StringUtils.isNotEmpty(cxqqzjInfo.getGwzContent())//
                    && StringUtils.isNotEmpty(cxqqzjInfo.getGwzwjgs())//
                    && StringUtils.isNotEmpty(cxqqzjInfo.getGwzbm())) {//
                AutoDTO_ZDZ_CXQQFGGWZZJRN cxqqfggwzzjrn = new AutoDTO_ZDZ_CXQQFGGWZZJRN();
                BeanMapper.copy(cxqqzjInfo, cxqqfggwzzjrn);
                cxqqfggwzzjrn.setBatchNumber(batchNo);
                cxqqfggwzzjrn.setGWZ(Base64.decodeBase64(cxqqzjInfo.getGwzContent().getBytes(Charsets.UTF_8)));

                singleObjectSaveDao.paramVoidResultExecute(new Object[]{cxqqfggwzzjrn, null});
            }
        }
    }

    /**
     * 法院端反馈查询处理失败信息 CXHZ + QNR
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processFkCxclsbxx(String xmlPath, String batchNo) throws Exception {
        FkCxclsbxx fkCxclsbxx = (FkCxclsbxx) XStreamUtils.unmarshal(xmlPath, FkCxclsbxx.class);

        for (Cxclsbxx cxclsbxx : fkCxclsbxx.getHtxxList()) {
            AutoDTO_ZDZ_FYDFKCXCLSBNR fydfkcxclsbnr = new AutoDTO_ZDZ_FYDFKCXCLSBNR();
            BeanMapper.copy(cxclsbxx, fydfkcxclsbnr);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR"));
            detachedCriteria.add(Restrictions.eq("BDHM", cxclsbxx.getBdhmContent()));
            detachedCriteria.add(Restrictions.eq("BatchNumber", batchNo));
            List<AutoDTO_ZDZ_CXQQNR> cxqqnrList = (List<AutoDTO_ZDZ_CXQQNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            fydfkcxclsbnr.setBDHM(cxqqnrList.get(0));

            fydfkcxclsbnr.setBatchNumber(batchNo);
            fydfkcxclsbnr.setRPTCheckType("1");
            fydfkcxclsbnr.setRPTSubmitStatus("1");
            fydfkcxclsbnr.setRPTVerifyType("1");
            fydfkcxclsbnr.setRPTSendType("1");
            fydfkcxclsbnr.setRPTFeedbackType("1");

            fydfkcxclsbnr.setDtDate(GetPathHelper.GetConst("DT_DATE"));
            fydfkcxclsbnr.setInstInfo(instInfo);
            fydfkcxclsbnr.setOperationUser(userInfo);

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{fydfkcxclsbnr, null});
        }
    }

    /**
     * 提供的司法控制请求内容 KZQQ + QA
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processKzqqxx(String xmlPath, String batchNo) throws Exception {
        Kzqqxx kzqqxx = (Kzqqxx) XStreamUtils.unmarshal(xmlPath, Kzqqxx.class);
        for (AutoDTO_ZDZ_KZQQJTNR kzqqjtnr : kzqqxx.getKzqqList()) {
            kzqqjtnr.setBatchNumber(batchNo);

            kzqqjtnr.setRPTCheckType("1");
            kzqqjtnr.setRPTSubmitStatus("1");
            kzqqjtnr.setRPTVerifyType("1");
            kzqqjtnr.setRPTSendType("1");
            kzqqjtnr.setRPTFeedbackType("1");

            kzqqjtnr.setDtDate(GetPathHelper.GetConst("DT_DATE"));
            kzqqjtnr.setInstInfo(instInfo);
            kzqqjtnr.setOperationUser(userInfo);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.CustInfo"));
            detachedCriteria.add(Restrictions.eq("custname", kzqqjtnr.getXM()));
            detachedCriteria.add(Restrictions.eq("custcode", kzqqjtnr.getZJLX()));
            detachedCriteria.add(Restrictions.eq("custnum", kzqqjtnr.getDSRZJHM()));
            List<CustInfo> custInfos = (List<CustInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            kzqqjtnr.setIsMatchCustInfo(custInfos.isEmpty() ? "2" : "1");

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{kzqqjtnr, null});

            for (AutoDTO_ZDZ_BZXRZHXX bzxrzhxx : kzqqjtnr.getKzzhlist().get(0).getkzzhlist()) {
                bzxrzhxx.setBatchNumber(batchNo);

                bzxrzhxx.setRPTCheckType("1");
                bzxrzhxx.setRPTSubmitStatus("1");
                bzxrzhxx.setRPTVerifyType("1");
                bzxrzhxx.setRPTSendType("1");
                bzxrzhxx.setRPTFeedbackType("1");

                bzxrzhxx.setBDHM(kzqqjtnr);

                singleObjectSaveDao.paramVoidResultExecute(new Object[]{bzxrzhxx, null});
            }
        }
    }

    /**
     * 法院提供的司法控制文书信息 KZQQ + QI
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processKzqqwsxx(String xmlPath, String batchNo) throws Exception {
        Kzqqwsxx kzqqwsxx = (Kzqqwsxx) XStreamUtils.unmarshal(xmlPath, Kzqqwsxx.class);
        for (KzqqwsInfo kzqqwsInfo : kzqqwsxx.getWsinfoList()) {
            AutoDTO_ZDZ_FYSFKZWSNR fysfkzwsnr = new AutoDTO_ZDZ_FYSFKZWSNR();
            BeanMapper.copy(kzqqwsInfo, fysfkzwsnr);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR"));
            detachedCriteria.add(Restrictions.eq("BDHM", kzqqwsInfo.getBdhmContent()));
            detachedCriteria.add(Restrictions.eq("BatchNumber", batchNo));
            List<AutoDTO_ZDZ_KZQQJTNR> kzqqjtnrList = (List<AutoDTO_ZDZ_KZQQJTNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            fysfkzwsnr.setBDHM(kzqqjtnrList.get(0));

            fysfkzwsnr.setBatchNumber(batchNo);
            fysfkzwsnr.setWSNR(Base64.decodeBase64(kzqqwsInfo.getWsnrContent().getBytes(Charsets.UTF_8)));

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{fysfkzwsnr, null});
        }
    }

    /**
     * 法院提供的证件信息 KZQQ + QC
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processKzqqzjxx(String xmlPath, String batchNo) throws Exception {
        Kzqqzjxx kzqqzjxx = (Kzqqzjxx) XStreamUtils.unmarshal(xmlPath, Kzqqzjxx.class);
        for (KzqqzjInfo kzqqzjInfo : kzqqzjxx.getZjinfoList()) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR"));
            detachedCriteria.add(Restrictions.eq("BDHM", kzqqzjInfo.getBdhmContent()));
            detachedCriteria.add(Restrictions.eq("BatchNumber", batchNo));
            List<AutoDTO_ZDZ_KZQQJTNR> kzqqjtnrList = (List<AutoDTO_ZDZ_KZQQJTNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            if (StringUtils.isNotEmpty(kzqqzjInfo.getGzzContent())//
                    && StringUtils.isNotEmpty(kzqqzjInfo.getGzzwjgs())//
                    && StringUtils.isNotEmpty(kzqqzjInfo.getGzzbm())) {//
                AutoDTO_ZDZ_FYSFKZFGGZZZJNR fysfkzfggzzzjnr = new AutoDTO_ZDZ_FYSFKZFGGZZZJNR();
                BeanMapper.copy(kzqqzjInfo, fysfkzfggzzzjnr);

                fysfkzfggzzzjnr.setBatchNumber(batchNo);
                fysfkzfggzzzjnr.setGZZ(Base64.decodeBase64(kzqqzjInfo.getGzzContent().getBytes(Charsets.UTF_8)));

                fysfkzfggzzzjnr.setBDHM(kzqqjtnrList.get(0));

                singleObjectSaveDao.paramVoidResultExecute(new Object[]{fysfkzfggzzzjnr, null});
            }

            if (StringUtils.isNotEmpty(kzqqzjInfo.getGwzContent())//
                    && StringUtils.isNotEmpty(kzqqzjInfo.getGwzwjgs())//
                    && StringUtils.isNotEmpty(kzqqzjInfo.getGwzbm())) {//
                AutoDTO_ZDZ_FYSFKZFGGWZZJNR fysfkzfggwzzjnr = new AutoDTO_ZDZ_FYSFKZFGGWZZJNR();
                BeanMapper.copy(kzqqzjInfo, fysfkzfggwzzjnr);

                fysfkzfggwzzjnr.setBatchNumber(batchNo);
                fysfkzfggwzzjnr.setGWZ(Base64.decodeBase64(kzqqzjInfo.getGwzContent().getBytes(Charsets.UTF_8)));

                fysfkzfggwzzjnr.setBDHM(kzqqjtnrList.get(0));

                singleObjectSaveDao.paramVoidResultExecute(new Object[]{fysfkzfggwzzjnr, null});
            }
        }
    }

    /**
     * 法院端反馈控制处理失败信息 KZHZ + QNR
     *
     * @param xmlPath
     * @param batchNo
     * @throws Exception
     */
    private void processFkKzclsbxx(String xmlPath, String batchNo) throws Exception {
        FkKzclsbxx fkKzclsbxx = (FkKzclsbxx) XStreamUtils.unmarshal(xmlPath, FkKzclsbxx.class);

        for (Kzclsbxx kzclsbxx : fkKzclsbxx.getHtxxList()) {
            AutoDTO_ZDZ_FYDFKKZCLSBNR fydfkkzclsbnr = new AutoDTO_ZDZ_FYDFKKZCLSBNR();
            BeanMapper.copy(kzclsbxx, fydfkkzclsbnr);

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR"));
            detachedCriteria.add(Restrictions.eq("BDHM", kzclsbxx.getBdhmContent()));
            detachedCriteria.add(Restrictions.eq("BatchNumber", batchNo));
            List<AutoDTO_ZDZ_KZQQJTNR> kzqqjtnrList = (List<AutoDTO_ZDZ_KZQQJTNR>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

            fydfkkzclsbnr.setBDHM(kzqqjtnrList.get(0));

            fydfkkzclsbnr.setBatchNumber(batchNo);
            fydfkkzclsbnr.setRPTCheckType("1");
            fydfkkzclsbnr.setRPTSubmitStatus("1");
            fydfkkzclsbnr.setRPTVerifyType("1");
            fydfkkzclsbnr.setRPTSendType("1");
            fydfkkzclsbnr.setRPTFeedbackType("1");

            fydfkkzclsbnr.setDtDate(GetPathHelper.GetConst("DT_DATE"));
            fydfkkzclsbnr.setInstInfo(instInfo);
            fydfkkzclsbnr.setOperationUser(userInfo);

            singleObjectSaveDao.paramVoidResultExecute(new Object[]{fydfkkzclsbnr, null});
        }
    }
    
}