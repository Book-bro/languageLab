package kr.or.ddit.crypto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * encode/decode
 * 	encoding(부호화) : 전송이나 저장을 위해 매체(media)가 이해할 수 있는 방식으로 데이터의 표현 방식을 바꾸는 작업.
 * 						ex) Base64, URLEncoding(percent encoding)
 * encrypt/decrypt
 * 	encrypting(암호화) : 권한(key) 없는 사용자가 snipping 하거나 spooping 하는 걸 막기 위해 데이터 표현을 바꾸는 작업.
 * - 단방향 암호화(hash 함수) : 암호화된 이후 평문 복원이 불가능한 방식(비밀번호 암호화에 활용).
 * 							: 다양한 길이의 입력 데이터에 만들어지는 결과 데이터가 길이가 동일한 경우.
 * 			ex) SHA-512 , 충돌을 막기위해 길이를 늘림, 입력된 데이터의 길이가 무제한이면 언젠가 충돌이 일어나므로 길이제한을 둠
 * - 양방향 암호화 : 암호문에서 원래 평문으로 복호화가 가능한 방식
 * 		- 대칭키 방식 : 하나의 비밀키로 암호화와 복호화에 모두 사용(ebook).
 * 				ex) AES(128, 256)
 * 		- 비대칭키 방식 : 공개키와 개인키, 한쌍의 키로 암호화와 복호화에 다른 키를 사용하는 방식(전자서명).
 * 				ex) RSA(2048)
 */				
public class EncryptionDesc {
	public static void main(String[] args) throws Exception {
		//암호화하기전 평문으로 되어있는 데이터 필요
		String plain = "java";
//		encryptAESTest(plain);
		
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA"); //알고리즘 설정
		pairGen.initialize(2048); //키 사이즈
		KeyPair keyPair = pairGen.generateKeyPair(); //키 생성
		PrivateKey privateKey = keyPair.getPrivate(); //개인키
		PublicKey publicKey = keyPair.getPublic(); //공개키
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] input = plain.getBytes(); //바이트 단위로 쪼갬
		byte[] encrypted = cipher.doFinal(input);
		String encoded = Base64.getEncoder().encodeToString(encrypted);//인코딩
		System.out.println(encoded);
		
		//디코딩은 인코딩과 반대방향으로 
		byte[] decoded = Base64.getDecoder().decode(encoded);
		
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decrypted = cipher.doFinal(decoded);
		System.out.println(new String(decrypted)); //비대칭키는 딜레이가 큼(키가 하나가 아닌 쌍이기 때문)
		
	}
	
	//양방향 암호화 대칭키
	private static void encryptAESTest(String plain) throws Exception {
		String ivValueText = "초기화 벡터";
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] iv = md.digest(ivValueText.getBytes()); //바이트 단위로 쪼갬
		IvParameterSpec ivSpec = new IvParameterSpec(iv); //초기화블럭
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); //키의 길이 설정 클수록 좋음 ,256은 갖고 있는 jdk의 보안 설정 때문에 쓰려면 바꿔줘야함
		SecretKey key = keyGen.generateKey(); //인터페이스라 객체 생성 불가, 키 하나 생성
		//암호화할건지 복호화 할건지 결정해야함
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec); //키를 가지고 암호화
		
		byte[] input = plain.getBytes();
		byte[] encrypted = cipher.doFinal(input); //암호화 진행, 암호문
		String encoded = Base64.getEncoder().encodeToString(encrypted); //바이트를 문자열로 만듬,부호화
		
		System.out.println(encoded);
		
		//복호화
		byte[] decoded = Base64.getDecoder().decode(encoded); // encrypted와 같음
		
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		byte[] decrypted = cipher.doFinal(decoded); //복호화 진행
		System.out.println(new String(decrypted)); //에러뜸, 초기화 블럭이 없기 때문
	}
	
	//단방향 암호화
	private static String encriptSha512(String plain) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512"); //알고리즘설정
			byte[] input = plain.getBytes(); //바이트 단위로 쪼갬
			//암호화의 기본데이터는 바이트
			byte[] encrypted = md.digest(input); //해쉬함수의 결과물
//			System.out.println(encrypted.length);
			String encoded = Base64.getEncoder().encodeToString(encrypted); //이 데이터를 db에 넣을것
//			System.out.println(encoded);
			return encoded;
		}catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	//인코딩,디코딩
	public static void encodeTest() throws UnsupportedEncodingException {
		String plain = "원문데이터";
		//인코딩
		String base64Encoded = Base64.getEncoder().encodeToString(plain.getBytes());
		System.out.println(base64Encoded);
		//다시 원문으로 돌리기
		System.out.println(new String(Base64.getDecoder().decode(base64Encoded))); //바이트단위를 스트링으로 만듬
		String urlEncoded = URLEncoder.encode(plain,"UTF-8");
		System.out.println(urlEncoded); //인코딩,우리는 못읽고 네트워크만 읽을수 있게 함
		System.out.println(URLDecoder.decode(urlEncoded, "UTF-8"));//디코딩
	}
}
