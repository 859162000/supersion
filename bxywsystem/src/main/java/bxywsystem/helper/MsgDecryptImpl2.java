package bxywsystem.helper;

import com.icfcc.foundation.configuration.XMLConfiguration;
import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@SuppressWarnings("unchecked")
public class MsgDecryptImpl2 {
	
	private static String DECRYPT_HEADBYTES = "com.cfcc.ecus.eft.crypt.headbytes";
	
	public byte[] getHeadBytes() {
		return headBytes;
	}

	public MsgDecryptImpl2() {
		desCipher = null;
		_km = null;
		rsaCipher = null;
		observers = new ArrayList();
		finished = 0L;
		fileLength = 0L;
		headLength = 0;
		try {
			_km = KeyManager.getInstance();
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding", "BC");
			rsaCipher = Cipher.getInstance("Rsa", "BC");
			XMLConfiguration config = new XMLConfiguration();
			String configurationFile = BatchContext.getInstance().getCrypConfig();
			config.loadConfiguration(configurationFile);
			int count = config.getKeyCount("head.field[@name]");
			for (int i = 0; i < count; i++) {
				String lenKey = "head.field(" + i + ")[@length]";
				int length = config.getInt(lenKey);
				headLength += length;
			}

			headBytes = new byte[headLength];
		} catch (Exception ex) {
			throw new RuntimeException("initialize cipher error", ex);
		}
	}

	public void decryptMsg(String sourceFile, String desFile)
			throws Exception {
		if (sourceFile == null || desFile == null)
			throw new IllegalArgumentException("源文件和目标文件路径参数不能为null");
		
		File sFile = new File(sourceFile);
		DataInputStream in = null;
		try {
			try {
				in = new DataInputStream(new FileInputStream(sFile));
			} catch (Exception ex) {
				throw new Exception(
						"读取原始报文失败");
			}
			int keyLen = in.readInt();
			if (keyLen == 128) {
				try {
					in.skip(keyLen);
					long dataLen = in.readLong();
					long mdLen = sFile.length() - dataLen - 8L - (long) keyLen
							- 4L;
					if (mdLen == 20L) {
						in.close();
						in = null;
						oldDecryptMsg(sourceFile, desFile, 1);
					} else {
						throw new IOException("");
					}
				} catch (IOException ioe) {
					in.close();
					in = null;
					newDecryptMsg(sourceFile, desFile, 1);
				}
			} else {
				in.close();
				newDecryptMsg(sourceFile, desFile, 1);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception ex) {
			}
		}
		return;
	}

	private void newDecryptMsg(String sourceFile, String desFile, int cryptKey)throws Exception {
		if (sourceFile == null || desFile == null)
			throw new IllegalArgumentException(
					"源文件和目标文件路径参数不能为null");
		if (cryptKey != 2 && cryptKey != 1)
			throw new Exception("1228无效加密方式");
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
				in.read(headBytes, 0, headLength);
				int keyLen = in.readInt();
				if (keyLen > 0x9c4000) {
					throw new Exception("密钥长度超出范围，文件可能错误...");
				}
				byte secretKey[] = new byte[keyLen];
				in.read(secretKey);
				desKey = decryptDES(secretKey);
				long dataLen = in.readLong();
				long mdLen = sFile.length() - dataLen - 8L - (long) keyLen - 4L
						- (long) headLength;
				if (checkMd(in, dataLen, mdLen)) {
					in.close();
					RandomAccessFile file = new RandomAccessFile(sFile, "r");
					int offset = 4 + keyLen + 8 + headLength;
					file.skipBytes(offset);
					desCipher.init(2, desKey);

					decryptMsgData(file, dataLen, out, desCipher);
					file.close();
				} else {
					throw new Exception("消息摘要不匹配，报文可能被修改");
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

	public void encryptMsg(String sourceFile, String desFile, int cryptKey)
			throws Exception {
		if (sourceFile == null || desFile == null)
			throw new IllegalArgumentException(
					"源文件和目标文件路径参数不能为null");
		if (headBytes == null || headBytes.length == 0)
			throw new RuntimeException(
					"必须先调用 setHeadBytes() 方法");
		finished = 0L;
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					desFile));
			File sFile = new File(sourceFile);
			fileLength = sFile.length() + (long) headBytes.length;
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
			out.write(headBytes);
			out.writeInt(wrappedKey.length);
			out.write(wrappedKey);
			out.writeLong(0L);
			desCipher.init(1, desKey);
			long dataLength = encryptAndCltMsgDigest(sFile.length(), in, out,
					desCipher, headBytes);
			out.close();
			in.close();
			RandomAccessFile file = new RandomAccessFile(desFile, "rw");
			file.skipBytes(wrappedKey.length + 4 + headLength);
			file.writeLong(dataLength);
			file.close();
		} catch (Exception e) {
			throw e;
		}
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
		byte decryHeadBytes[] = new byte[headLength];
		try {
			byte buffer[] = new byte[8];
			int length = 0;
			long percent = 0L;
			long newPercent = 0L;
			long hasFinished = 0L;
			long descryptLength = 0L;
			boolean over = false;
			while ((length = file.read(buffer)) != -1 && hasFinished < dataLen) {
				byte descrypByte[] = desCipher.update(buffer, 0, length);
				int le = descrypByte != null ? descrypByte.length : 0;
				if (descryptLength + (long) le < (long) headLength) {
					if (descrypByte != null)
						System.arraycopy(descrypByte, 0, decryHeadBytes,
								(int) descryptLength, descrypByte.length);
					descryptLength += le;
					hasFinished += length;
				} else if (!over) {
					System.arraycopy(descrypByte, 0, decryHeadBytes,
							(int) descryptLength,
							(int) ((long) headLength - descryptLength));
					if (!Arrays.equals(headBytes, decryHeadBytes))
						throw new Exception(
								"解密时头部不匹配，报文可能被修改");
					
					BatchContext.getInstance().addPropetiry(DECRYPT_HEADBYTES, decryHeadBytes);
					int dfe = (int) ((long) headLength - descryptLength);
					out.write(descrypByte, dfe, descrypByte.length - dfe);
					descryptLength += descrypByte.length;
					hasFinished += length;
					over = true;
				} else {
					out.write(descrypByte, 0, length);
					hasFinished += length;
					newPercent = (hasFinished * 100L) / dataLen;
					if (percent != newPercent)
						percent = newPercent;
					long remain = dataLen - hasFinished;
					if (remain <= (long) buffer.length)
						buffer = new byte[(int) remain];
				}
			}
			byte fin[] = desCipher.doFinal();
			out.write(fin);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				file.close();
			} catch (IOException ex1) {
			}
		}
		return;
	}

	private void oldDecryptMsgData(RandomAccessFile file, long dataLen,
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

	public Key decryptDES(byte wrapedKey[]) throws Exception {
		rsaCipher.init(4, _km.getPublicKey());
		return rsaCipher.unwrap(wrapedKey, "DES", 3);
	}

	private long encryptAndCltMsgDigest(long msgFileSize, InputStream in,
			OutputStream out, Cipher cipher, byte headBytes[]) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		int inLength = 0;
		long dataLength = 0L;
		byte headEncrypt[] = cipher.update(headBytes);
		out.write(headEncrypt);
		out.flush();
		md.update(headEncrypt);
		dataLength += headEncrypt.length;
		finished = (long) inLength + finished;
		update(finished);
		byte inBytes[] = new byte[BUFFER_SIZE];
		byte outBytes[];
		while ((inLength = in.read(inBytes)) > 0) {
			outBytes = cipher.update(inBytes, 0, inLength);
			int dfd = outBytes != null ? outBytes.length : 0;
			if (outBytes != null) {
				out.write(outBytes);
				md.update(outBytes);
			}
			dataLength += dfd;
			finished = (long) inLength + finished;
			update(finished);
		}
		outBytes = cipher.doFinal();
		out.write(outBytes);
		md.update(outBytes);
		dataLength += outBytes.length;
		byte mdbyte[] = md.digest();
		out.write(mdbyte);
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

	public void setHeadBytes(byte headBytes[]) throws Exception {
		if (headBytes == null)
			throw new NullPointerException(
					"传入的headBytes[]为空");
		if (headBytes.length != headLength) {
			throw new RuntimeException("传入的加密的头部的长度不对");
		} else {
			this.headBytes = headBytes;
			return;
		}
	}

	private void oldDecryptMsg(String sourceFile, String desFile, int cryptKey)
			throws Exception {
		if (sourceFile == null || desFile == null)
			throw new IllegalArgumentException(
					"源文件和目标文件路径参数不能为null");
		
		BatchContext.getInstance().addPropetiry(DECRYPT_HEADBYTES,null);
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
				throw new Exception("读取原始报文失败");
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
				desKey = decryptDES(secretKey);
				long dataLen = in.readLong();
				long mdLen = sFile.length() - dataLen - 8L - (long) keyLen - 4L;
				
				if (checkMd(in, dataLen, mdLen)) {
					in.close();
					RandomAccessFile file = new RandomAccessFile(sFile, "r");
					int offset = 4 + keyLen + 8;
					file.skipBytes(offset);
					
					desCipher.init(2, desKey);
					file.skipBytes(offset * -1);
					oldDecryptMsgData(file, dataLen, out, desCipher);
					file.close();
				} else {
					
					throw new Exception("消息摘要不匹配，报文可能被修改");
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

	public static final String MESSAGE_DIGEST_ALGORITHM = "SHA-1";
	private Cipher desCipher;
	private KeyManager _km;
	private Cipher rsaCipher;
	private ArrayList observers;
	private long finished;
	private long fileLength;
	private int headLength;
	private byte headBytes[];
	private static int BUFFER_SIZE = 1024;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
}
