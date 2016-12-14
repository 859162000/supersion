package zxptsystem.helper.GR;

import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyGenerator;

class KeyManager {
	private class MyKeyStore {

		KeyStore _keyStore;
		String _alias;
		String _privateKeyPwd;

		MyKeyStore() {
		}
	}

	private KeyManager() {
		_pubKey = null;
		_priKey = null;
		_desKeyGen = null;
		_myStore = new MyKeyStore();
		init();
	}

	private void init() {
		try {
			_desKeyGen = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
		}
	}

	private void initPrivateKey() throws Exception {
		if (_myStore._keyStore == null) {
			throw new NullPointerException(
					"keystore is not been initialized,please init keystore first!");
		} else {
			_priKey = _myStore._keyStore.getKey(_myStore._alias,
					_myStore._privateKeyPwd.toCharArray());
			return;
		}
	}

	static KeyManager getInstance() {
		if (_km == null)
			_km = new KeyManager();
		return _km;
	}

	Key getPublicKey() throws Exception {
		if (_pubKey == null)
			initPublicKey();
		return _pubKey;
	}

	Key getPrivateKey() throws Exception {
		if (_priKey == null)
			initPrivateKey();
		return _priKey;
	}

	Key generateDESKey() {
		SecureRandom random = new SecureRandom();
		_desKeyGen.init(random);
		return _desKeyGen.generateKey();
	}

	private void initPublicKey() throws Exception {
		String pkPath = BatchContext.getInstance().getCrypPublickey();
		File file1 = new File(pkPath);
		FileInputStream in = null;
		try {
			byte bytes[] = new byte[(int) file1.length()];
			in = new FileInputStream(pkPath);
			in.read(bytes);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			_pubKey = keyFactory.generatePublic(keySpec);
		} catch (Exception ex) {
			throw ex;
		} finally {
			in.close();
		}
		return;
	}

	private static KeyManager _km = null;
	private Key _pubKey;
	private Key _priKey;
	private KeyGenerator _desKeyGen;
	private MyKeyStore _myStore;

}
