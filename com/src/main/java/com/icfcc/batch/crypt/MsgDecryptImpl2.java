package com.icfcc.batch.crypt;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.BatchException;
import com.icfcc.batch.Constants;
import com.icfcc.batch.config.BatchConfig;
import com.icfcc.batch.util.LogWriter;
import com.icfcc.foundation.configuration.XMLConfiguration;
import com.icfcc.foundation.exception.CFCCException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class MsgDecryptImpl2
        implements Cryption
{
    public static final String MESSAGE_DIGEST_ALGORITHM = "SHA-1";
    private Cipher desCipher = null;
    private KeyManager _km = null;
    private Cipher rsaCipher = null;
    private ArrayList observers = new ArrayList();
    private long finished = 0L;
    private long fileLength = 0L;
    private int headLength = 0;
    private byte[] headBytes;
    private static int BUFFER_SIZE = 1024;

    public MsgDecryptImpl2()
    {
        try
        {
            this._km = KeyManager.getInstance();
            this.desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding", "BC");
            this.rsaCipher = Cipher.getInstance("Rsa", "BC");

            XMLConfiguration config = new XMLConfiguration();
            String homeDir = BatchContext.getInstance().getBaseHome();
            String configurationFile = "conf/Crypt.xml";
            configurationFile = homeDir + configurationFile;

            config.loadConfiguration(configurationFile);

            int count = config.getKeyCount("head.field[@name]");

            for (int i = 0; i < count; ++i) {
                String nameKey = "head.field(" + i + ")[@name]";
                String lenKey = "head.field(" + i + ")[@length]";
                String name = config.getString(nameKey);
                int length = config.getInt(lenKey);
                this.headLength += length;
            }

            this.headBytes = new byte[this.headLength];
        }
        catch (Exception ex) {
            throw new RuntimeException("initialize cipher error", ex);
        }
    }

    public void decryptMsg(String sourceFile, String desFile, int cryptKey)
            throws CFCCException
    {
        if ((null == sourceFile) || (null == desFile)) {
            throw new IllegalArgumentException("源文件和目标文件路径参数不能为null");
        }

        if ((cryptKey != 2) && (cryptKey != 1))
        {
            throw new BatchException(1228, "无效加密方式");
        }

        File sFile = new File(sourceFile);
        DataInputStream in = null;
        try
        {
            try {
                in = new DataInputStream(new FileInputStream(sFile));
            }
            catch (Exception ex) {
                throw new Exception("读取原始报文失败");
            }

            int keyLen = in.readInt();

            if (keyLen == 128) {
                try {
                    in.skip(keyLen);
                    long dataLen = in.readLong();
                    long mdLen = sFile.length() - dataLen - 8L - keyLen - 4L;
                    if (mdLen == 20L) {
                        in.close();
                        in = null;
                        oldDecryptMsg(sourceFile, desFile, cryptKey);
                    } else {
                        throw new IOException("");
                    }
                } catch (IOException ioe) {
                    in.close();
                    in = null;
                    newDecryptMsg(sourceFile, desFile, cryptKey);
                }
            } else {
                in.close();
                newDecryptMsg(sourceFile, desFile, cryptKey);
            }
        }
        catch (Exception keyLen)
        {
        }
        finally
        {
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception ex)
            {
            }
        }
    }

    private void newDecryptMsg(String sourceFile, String desFile, int cryptKey)
            throws CFCCException
    {
        if ((null == sourceFile) || (null == desFile)) {
            throw new IllegalArgumentException("源文件和目标文件路径参数不能为null");
        }

        if ((cryptKey != 2) && (cryptKey != 1))
        {
            throw new BatchException(1228, "无效加密方式");
        }

        File sFile = new File(sourceFile);
        FileOutputStream out = null;
        DataInputStream in = null;
        try
        {
            try {
                out = new FileOutputStream(desFile);
            }
            catch (Exception ex) {
                throw new Exception("生成解密后文件失败");
            }
            try
            {
                in = new DataInputStream(new FileInputStream(sFile));
            }
            catch (Exception ex) {
                throw new Exception("读取原始报文失败");
            }

            Key desKey = null;
            try {
                LogWriter.getInstance().writeSystemInfoLog("从报文中读取密钥...");

                in.read(this.headBytes, 0, this.headLength);

                String dfdfdf = new String(this.headBytes);

                int keyLen = in.readInt();

                if (keyLen > 10240000) {
                    LogWriter.getInstance().writeSystemInfoLog("密钥长度超出范围，文件可能错误...");

                    throw new Exception("密钥长度超出范围，文件可能错误...");
                }

                byte[] secretKey = new byte[keyLen];
                in.read(secretKey);
                desKey = decryptDES(secretKey, cryptKey);
                long dataLen = in.readLong();

                long mdLen = sFile.length() - dataLen - 8L - keyLen - 4L - this.headLength;
                if (BatchConfig.getInstance().isDebug()) {
                    LogWriter.getInstance().writeSystemDebugLog("原消息摘要长度(byte)：" + mdLen);
                }

                LogWriter.getInstance().writeSystemInfoLog("得到密钥，验证报文是否被修改...");

                if (checkMd(in, dataLen, mdLen)) {
                    in.close();
                    RandomAccessFile file = new RandomAccessFile(sFile, "r");
                    int offset = 4 + keyLen + 8 + this.headLength;
                    file.skipBytes(offset);
                    LogWriter.getInstance().writeSystemInfoLog("验证通过，开始解密报文...");

                    this.desCipher.init(2, desKey);
                    decryptMsgData(file, dataLen, out, this.desCipher);
                    file.close();
                }
                else {
                    LogWriter.getInstance().writeSystemErrorLog("Md invalidate", null);
                    throw new Exception("消息摘要不匹配，报文可能被修改");
                }

                LogWriter.getInstance().writeSystemInfoLog("解密报文结束！");
            }
            catch (Exception ex) {
                throw new BatchException(1224, ex.getMessage());
            }

        }
        catch (Exception desKey)
        {
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (Exception ex) {
            }
            try {
                out.close();
            }
            catch (Exception ex)
            {
            }
        }
    }

    public void encryptMsg(String sourceFile, String desFile, int cryptKey)
            throws CFCCException
    {
        if ((null == sourceFile) || (null == desFile)) {
            throw new IllegalArgumentException("源文件和目标文件路径参数不能为null");
        }

        if ((this.headBytes == null) || (this.headBytes.length == 0)) {
            throw new RuntimeException("必须先调用 setHeadBytes() 方法");
        }

        if ((cryptKey != 2) && (cryptKey != 1))
        {
            throw new BatchException(1228, "无效加密方式");
        }

        this.finished = 0L;
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(desFile));

            File sFile = new File(sourceFile);

            this.fileLength = (sFile.length() + this.headBytes.length);
            InputStream in = new FileInputStream(sFile);

            Key desKey = null;
            LogWriter.getInstance().writeSystemInfoLog("生成秘密密钥并加密...");

            desKey = this._km.generateDESKey();
            if (desKey == null) {
                LogWriter.getInstance().writeSystemErrorLog("产生DES密钥失败...", null);
                throw new RuntimeException("产生DES密钥失败...");
            }

            byte[] wrappedKey = encryptDES(desKey, cryptKey);
            if ((wrappedKey == null) || (wrappedKey.length == 0)) {
                LogWriter.getInstance().writeSystemErrorLog("对DES密钥进行加密失败...", null);
                throw new RuntimeException("对DES密钥进行加密失败...");
            }

            out.write(this.headBytes);

            out.writeInt(wrappedKey.length);
            out.write(wrappedKey);

            out.writeLong(0L);

            LogWriter.getInstance().writeSystemInfoLog("密钥加密结束，开始加密报文并计算加密后的摘要...");

            this.desCipher.init(1, desKey);
            long dataLength = encryptAndCltMsgDigest(sFile.length(), in, out, this.desCipher, this.headBytes);

            out.close();
            in.close();

            RandomAccessFile file = new RandomAccessFile(desFile, "rw");
            file.skipBytes(wrappedKey.length + 4 + this.headLength);
            file.writeLong(dataLength);
            file.close();
            LogWriter.getInstance().writeSystemInfoLog("加密报文结束！");
        }
        catch (Exception e)
        {
            LogWriter.getInstance().writeSystemErrorLog("加密错误", e);
            throw new BatchException(1225, "加密错误");
        }
    }

    private boolean checkMd(DataInputStream in, long dataLen, long mdLen)
            throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        long readByte = 0L;
        int readLength = dataLen >= 2048L ? 2048 : (int)dataLen;
        byte inBytes[] = new byte[readLength];
        byte destMd[] = new byte[(int)mdLen];
        int bytes;
        while(readByte < dataLen)
        {
            bytes = in.read(inBytes, 0, readLength);
            readByte += bytes;
            if(dataLen - readByte < (long)readLength)
                readLength = (int)(dataLen - readByte);
            md.update(inBytes, 0, bytes);
        }
        byte computedMd[] = md.digest();
        bytes = in.read(destMd);
        return MessageDigest.isEqual(computedMd, destMd);
    }

    private void decryptMsgData(RandomAccessFile file, long dataLen, OutputStream out, Cipher desCipher)
            throws BatchException
    {
        Exception exception;
        byte decryHeadBytes[] = new byte[headLength];
        try
        {
            byte buffer[] = new byte[8];
            int length = 0;
            long percent = 0L;
            long newPercent = 0L;
            long hasFinished = 0L;
            long descryptLength = 0L;
            boolean over = false;
            do
            {
                if((length = file.read(buffer)) == -1 || hasFinished >= dataLen)
                    break;
                byte descrypByte[] = desCipher.update(buffer, 0, length);
                int le = descrypByte != null ? descrypByte.length : 0;
                if(descryptLength + (long)le < (long)headLength)
                {
                    if(descrypByte != null)
                        System.arraycopy(descrypByte, 0, decryHeadBytes, (int)descryptLength, descrypByte.length);
                    descryptLength += le;
                    hasFinished += length;
                } else
                if(!over)
                {
                    System.arraycopy(descrypByte, 0, decryHeadBytes, (int)descryptLength, (int)((long)headLength - descryptLength));
                    if(!Arrays.equals(headBytes, decryHeadBytes))
                        throw new Exception("解密时头部不匹配，报文可能被修改");
                    BatchContext.getInstance().addPropetiry(Constants.DECRYPT_HEADBYTES, decryHeadBytes);
                    int dfe = (int)((long)headLength - descryptLength);
                    out.write(descrypByte, dfe, descrypByte.length - dfe);
                    descryptLength += descrypByte.length;
                    hasFinished += length;
                    over = true;
                } else
                {
                    out.write(descrypByte, 0, length);
                    hasFinished += length;
                    newPercent = (hasFinished * 100L) / dataLen;
                    if(percent != newPercent)
                        percent = newPercent;
                    long remain = dataLen - hasFinished;
                    if(remain <= (long)buffer.length)
                        buffer = new byte[(int)remain];
                }
            } while(true);
            byte fin[] = desCipher.doFinal();
            out.write(fin);
        }
        catch(Exception ex)
        {
            throw new BatchException(1224, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                file.close();
            }
            catch (IOException ex1)
            {
            }
        }
    }

    private void oldDecryptMsgData(RandomAccessFile file, long dataLen, OutputStream out, Cipher desCipher)
            throws BatchException
    {
        CipherOutputStream cOut;
        Exception exception;
        cOut = new CipherOutputStream(out, desCipher);
        try
        {
            byte buffer[] = new byte[desCipher.getBlockSize()];
            long percent = 0L;
            long newPercent = 0L;
            long hasFinished = 0L;
            do
            {
                int length;
                if((length = file.read(buffer)) == -1 || hasFinished >= dataLen)
                    break;
                cOut.write(buffer, 0, length);
                hasFinished += length;
                newPercent = (hasFinished * 100L) / dataLen;
                if(percent != newPercent)
                    percent = newPercent;
                long remain = dataLen - hasFinished;
                if(remain <= (long)buffer.length)
                    buffer = new byte[(int)remain];
            } while(true);
        }
        catch(Exception ex)
        {
            throw new BatchException(1224, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                file.close();
                cOut.close();
            }
            catch (IOException ex1)
            {
            }
        }
    }

    public byte[] encryptDES(Key desKey, int cryptKey)
            throws Exception
    {
        if (cryptKey == 1) {
            this.rsaCipher.init(3, this._km.getPublicKey());
        }

        if (cryptKey == 2) {
            this._km.initKeyStore("credit", "credit");
            this.rsaCipher.init(3, this._km.getPrivateKey());
        }

        return this.rsaCipher.wrap(desKey);
    }

    public Key decryptDES(byte[] wrapedKey, int cryptKey)
            throws Exception
    {
        if (cryptKey == 1) {
            this.rsaCipher.init(4, this._km.getPublicKey());
        }

        if (cryptKey == 2) {
            this._km.initKeyStore("credit", "credit");
            this.rsaCipher.init(4, this._km.getPrivateKey());
        }

        return this.rsaCipher.unwrap(wrapedKey, "DES", 3);
    }

    private long encryptAndCltMsgDigest(long msgFileSize, InputStream in, OutputStream out, Cipher cipher, byte headBytes[])
            throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        int inLength = 0;
        long dataLength = 0L;
        byte headEncrypt[] = cipher.update(headBytes);
        out.write(headEncrypt);
        out.flush();
        md.update(headEncrypt);
        dataLength += headEncrypt.length;
        finished = (long)inLength + finished;
        update(finished);
        byte inBytes[] = new byte[BUFFER_SIZE];
        byte outBytes[];
        while((inLength = in.read(inBytes)) > 0)
        {
            outBytes = cipher.update(inBytes, 0, inLength);
            int dfd = outBytes != null ? outBytes.length : 0;
            if(outBytes != null)
            {
                out.write(outBytes);
                md.update(outBytes);
            }
            dataLength += dfd;
            finished = (long)inLength + finished;
            update(finished);
        }
        outBytes = cipher.doFinal();
        out.write(outBytes);
        md.update(outBytes);
        dataLength += outBytes.length;
        byte mdbyte[] = md.digest();
        out.write(mdbyte);
        if(BatchConfig.getInstance().isDebug())
            LogWriter.getInstance().writeSystemDebugLog("生成摘要长度为：" + mdbyte.length);
        return dataLength;
    }

    public void initKeyStore(String storePwd, String privateKeyPwd)
            throws CFCCException
    {
        if (null == storePwd)
            storePwd = "";

        if (null == privateKeyPwd)
            privateKeyPwd = "";
        try
        {
            this._km.initKeyStore(storePwd, privateKeyPwd);
        }
        catch (Exception ex) {
            throw new BatchException(1225, ex.getMessage());
        }
    }

    public void addObserver(CryptObserver observer)
    {
        this.observers.add(observer);
    }

    public void update(long value) {
        for (Iterator it = this.observers.iterator(); it.hasNext(); ) {
            CryptObserver observer = (CryptObserver)it.next();
            observer.cryptUpdate((float)value / (float)this.fileLength);
        }
    }

    public void setHeadBytes(byte[] headBytes)
            throws CFCCException
    {
        if (headBytes == null)
            throw new NullPointerException("传入的headBytes[]为空");

        if (headBytes.length != this.headLength)
            throw new RuntimeException("传入的加密的头部的长度不对");

        this.headBytes = headBytes;
    }

    private void oldDecryptMsg(String sourceFile, String desFile, int cryptKey)
            throws CFCCException
    {
        if ((null == sourceFile) || (null == desFile)) {
            throw new IllegalArgumentException("源文件和目标文件路径参数不能为null");
        }

        if ((cryptKey != 2) && (cryptKey != 1))
        {
            throw new BatchException(1228, "无效加密方式");
        }

        BatchContext.getInstance().addPropetiry(Constants.DECRYPT_HEADBYTES, null);

        File sFile = new File(sourceFile);
        FileOutputStream out = null;
        DataInputStream in = null;
        try
        {
            try {
                out = new FileOutputStream(desFile);
            }
            catch (Exception ex) {
                throw new Exception("生成解密后文件失败");
            }
            try
            {
                in = new DataInputStream(new FileInputStream(sFile));
            }
            catch (Exception ex) {
                throw new Exception("读取原始报文失败");
            }

            Key desKey = null;
            try {
                LogWriter.getInstance().writeSystemInfoLog("从报文中读取密钥...");
                int keyLen = in.readInt();

                if (keyLen > 10240000) {
                    LogWriter.getInstance().writeSystemInfoLog("密钥长度超出范围，文件可能错误...");

                    throw new Exception("密钥长度超出范围，文件可能错误...");
                }

                byte[] secretKey = new byte[keyLen];
                in.read(secretKey);
                desKey = decryptDES(secretKey, cryptKey);
                long dataLen = in.readLong();

                long mdLen = sFile.length() - dataLen - 8L - keyLen - 4L;
                if (BatchConfig.getInstance().isDebug()) {
                    LogWriter.getInstance().writeSystemDebugLog("原消息摘要长度(byte)：" + mdLen);
                }

                LogWriter.getInstance().writeSystemInfoLog("得到密钥，验证报文是否被修改...");

                if (checkMd(in, dataLen, mdLen)) {
                    in.close();
                    RandomAccessFile file = new RandomAccessFile(sFile, "r");
                    int offset = 4 + keyLen + 8;
                    file.skipBytes(offset);
                    LogWriter.getInstance().writeSystemInfoLog("验证通过，开始解密报文...");

                    this.desCipher.init(2, desKey);
                    file.skipBytes(offset * -1);
                    oldDecryptMsgData(file, dataLen, out, this.desCipher);
                    file.close();
                }
                else {
                    LogWriter.getInstance().writeSystemErrorLog("Md invalidate", null);
                    throw new Exception("消息摘要不匹配，报文可能被修改");
                }

                LogWriter.getInstance().writeSystemInfoLog("解密报文结束！");
            }
            catch (Exception ex) {
                throw new BatchException(1224, ex.getMessage());
            }

        }
        catch (Exception desKey)
        {
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (Exception ex) {
            }
            try {
                out.close();
            }
            catch (Exception ex)
            {
            }
        }
    }

    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }
}
