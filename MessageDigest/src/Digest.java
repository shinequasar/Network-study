import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest { // 단방향 암호화 알고리즘
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "Hello Crypto";
        String msg2 = "Hello Crypte";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(msg.getBytes()); //암호화할 메세지 전달
        byte[] bytes = digest.digest(); //암호화된 해쉬값 생성
        System.out.println(bytes2Hex(bytes));

        digest.update(msg2.getBytes()); //암호화할 메세지 전달
        byte[] bytes2 = digest.digest(); //암호화된 해쉬값 생성
        System.out.println(bytes2Hex(bytes2));
    }

    public static String bytes2Hex(byte[] bytes){
        StringBuffer builder = new StringBuffer();
        for(byte b : bytes) builder.append(String.format("%02x",b));
        return builder.toString();
    }
}