import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Crypto {
    private static Cipher encryptor;
    private static Cipher decryptor;
    private static SecretKey key;
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        //AES or DES 알고리즘을 가지고 비밀키 생성
        key = KeyGenerator.getInstance("AES").generateKey();  //"DES"
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        encryptor = Cipher.getInstance("RSA");//"DES"
        decryptor = Cipher.getInstance("RSA");//"DES"

//        encryptor = Cipher.getInstance("AES");//"DES"
//        decryptor = Cipher.getInstance("AES");//"DES"
        //초기화 RSA
        encryptor.init(Cipher.ENCRYPT_MODE, privateKey);
        decryptor.init(Cipher.DECRYPT_MODE, publicKey);
        //초기화 AES,DES
//        encryptor.init(Cipher.ENCRYPT_MODE, key);
//        decryptor.init(Cipher.DECRYPT_MODE, key);
        // 암호화 및 복호화
        String cipherText = encrypt("My name is Jone");
        System.out.println("Cipher Text : "+cipherText);
        String plainText = decrypt(cipherText);
        System.out.println("Plain Text : "+plainText);
    }

    private static String encrypt(String str) throws IllegalBlockSizeException, BadPaddingException {
        byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
        byte[] etext = encryptor.doFinal(utf8);
        etext = Base64.getEncoder().encode(etext);
        return new String(etext);
    }

    private static String decrypt(String str) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] dtext = Base64.getDecoder().decode(str.getBytes());
        byte[] utf8 = decryptor.doFinal(dtext);
        return new String(utf8, "UTF8");
    }
}