package zxptsystem.helper.QY;

import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@SuppressWarnings("unchecked")
class Cryptor {

	public Cryptor() {
		_km = null;
		desCipher = null;
		rsaCipher = null;
		observers = new ArrayList();
		finished = 0L;
		fileLength = 0L;
		try {
			_km = KeyManager.getInstance();
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			rsaCipher = Cipher.getInstance("Rsa", "BC");
		} catch (Exception ex) {
			throw new RuntimeException("initialize cipher error", ex);
		}
	}

	public void encryptMsg(String sourceFile, String desFile, int cryptKey)
			throws Exception {
		if (sourceFile == null || desFile == null)
			throw new Exception("源文件和目标文件路径参数不能为null");
		if (cryptKey != 2 && cryptKey != 1)
			throw new Exception("1228:无效加密方式");
		finished = 0L;
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					desFile));
			File sFile = new File(sourceFile);
			fileLength = sFile.length();
			InputStream in = new FileInputStream(sFile);
			Key desKey = null;
			
			desKey = _km.generateDESKey();
			if (desKey == null) {
				
				throw new RuntimeException("产生DES密钥失败...");
			}
			byte wrappedKey[] = encryptDES(desKey);
			if (wrappedKey == null || wrappedKey.length == 0) {				
				throw new RuntimeException("对DES密钥进行加密失败...");
			}
			out.writeInt(wrappedKey.length);
			out.write(wrappedKey);
			out.writeLong(0L);
			
			desCipher.init(1, desKey);
			long dataLength = encryptAndCltMsgDigest(sFile.length(), in, out,
					desCipher);
			out.close();
			in.close();
			RandomAccessFile file = new RandomAccessFile(desFile, "rw");
			file.skipBytes(wrappedKey.length + 4);
			file.writeLong(dataLength);
			file.close();
			
		} catch (Exception e) {			
			throw e;
		}
	}

	public void decryptMsg(String sourceFile, String desFile, int cryptKey)
			throws Exception {
		if (sourceFile == null || desFile == null)
			throw new IllegalArgumentException(
					"源文件和目标文件路径参数不能为null");
		if (cryptKey != 2 && cryptKey != 1)
			throw new Exception("1228:无效加密方式");
		File sFile = new File(sourceFile);
		FileOutputStream out = null;
		DataInputStream in = null;
		try {
			try {
				out = new FileOutputStream(desFile);
			} catch (Exception ex) {
				throw new Exception(
						"生成解密后文件失败");
			}
			try {
				in = new DataInputStream(new FileInputStream(sFile));
			} catch (Exception ex) {
				throw new Exception(
						"读取原始报文失败");
			}
			Key desKey = null;
			try {
				
				int keyLen = in.readInt();
				if (keyLen > 0x9c4000) {
					
					throw new Exception(
							"密钥长度超出范围，文件可能错误...");
				}
				byte secretKey[] = new byte[keyLen];
				in.read(secretKey);
				desKey = decryptDES(secretKey, cryptKey);
				long dataLen = in.readLong();
				long mdLen = sFile.length() - dataLen - 8L - (long) keyLen - 4L;
				
				if (checkMd(in, dataLen, mdLen)) {
					in.close();
					RandomAccessFile file = new RandomAccessFile(sFile, "r");
					int offset = 4 + keyLen + 8;
					file.skipBytes(offset);
					
					desCipher.init(2, desKey);
					file.skipBytes(offset * -1);
					decryptMsgData(file, dataLen, out, desCipher);
					file.close();
				} else {
					
					throw new Exception(
							"消息摘要不匹配，报文可能被修改");
				}
				
			} catch (Exception ex) {
				throw ex;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				in.close();
			} catch (Exception exception1) {
			}
			try {
				out.close();
			} catch (Exception ex) {
			}
		}
		return;
	}

	private boolean checkMd(DataInputStream in, long dataLen, long mdLen)
			throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		long readByte = 0L;
		int readLength = dataLen >= 2048L ? 2048 : (int) dataLen;
		byte inBytes[] = new byte[readLength];
		byte destMd[] = new byte[(int) mdLen];
		int bytes;
		while (readByte < dataLen) {
			bytes = in.read(inBytes, 0, readLength);
			readByte += bytes;
			if (dataLen - readByte < (long) readLength)
				readLength = (int) (dataLen - readByte);
			md.update(inBytes, 0, bytes);
		}
		byte computedMd[] = md.digest();
		bytes = in.read(destMd);
		return MessageDigest.isEqual(computedMd, destMd);
	}

	private void decryptMsgData(RandomAccessFile file, long dataLen,
			OutputStream out, Cipher desCipher) throws Exception {
		CipherOutputStream cOut = new CipherOutputStream(out, desCipher);
		try {
			byte buffer[] = new byte[desCipher.getBlockSize()];
			long percent = 0L;
			long newPercent = 0L;
			int length;
			for (long hasFinished = 0L; (length = file.read(buffer)) != -1
					&& hasFinished < dataLen;) {
				cOut.write(buffer, 0, length);
				hasFinished += length;
				newPercent = (hasFinished * 100L) / dataLen;
				if (percent != newPercent)
					percent = newPercent;
				long remain = dataLen - hasFinished;
				if (remain <= (long) buffer.length)
					buffer = new byte[(int) remain];
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				file.close();
				cOut.close();
			} catch (IOException ex1) {
			}
		}
		return;
	}

	public byte[] encryptDES(Key desKey) throws Exception {
		rsaCipher.init(3, _km.getPublicKey());
		return rsaCipher.wrap(desKey);
	}

	public Key decryptDES(byte wrapedKey[], int cryptKey) throws Exception {
		rsaCipher.init(4, _km.getPublicKey());
		return rsaCipher.unwrap(wrapedKey, "DES", 3);
	}

	private long encryptAndCltMsgDigest(long msgFileSize, InputStream in,
			OutputStream out, Cipher cipher) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte inBytes[] = new byte[blockSize];
		byte outBytes[] = new byte[outputSize];
		int inLength = 0;
		long dataLength = 0L;
		while ((inLength = in.read(inBytes)) > 0) {
			int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
			out.write(outBytes, 0, outLength);
			out.flush();
			md.update(outBytes, 0, outLength);
			dataLength += outLength;
			finished = (long) inLength + finished;
			update(finished);
		}
		outBytes = cipher.doFinal();
		out.write(outBytes);
		md.update(outBytes);
		dataLength += outBytes.length;
		byte mdbyte[] = md.digest();
		out.write(mdbyte);
		out.flush();
		return dataLength;
	}

	public void addObserver(CryptObserver observer) {
		observers.add(observer);
	}

	public void update(long value) {
		CryptObserver observer;
		for (Iterator it = observers.iterator(); it.hasNext(); observer
				.cryptUpdate((float) value / (float) fileLength))
			observer = (CryptObserver) it.next();

	}

	public void setHeadBytes(byte abyte0[]) throws Exception {
	}

	public byte[] getHeadBytes() {
		return null;
	}

	public static final String MESSAGE_DIGEST_ALGORITHM = "SHA-1";
	private KeyManager _km;
	private Cipher desCipher;
	private Cipher rsaCipher;
	private ArrayList observers;
	private long finished;
	private long fileLength;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
}
