import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

public class HttpSecure {
    public static void getUrlInfo() throws IOException{
        URL url = new URL("https://www.oracle.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) //메인페이지에 대한 hltml가져오기
            System.out.println(line);
        reader.close();
    }

    public static void getCertificate() throws IOException {
        URL url = new URL("https://www.oracle.com");
        URLConnection con = url.openConnection();
        HttpsURLConnection scon = (HttpsURLConnection) con; //보안커넥션
        scon.connect(); //oracle.com에 보안접속
        Certificate[] certs = scon.getServerCertificates(); //certificate 가 하나가 아닐수 있으니 배열
        System.out.println("Server Certificate: "+certs[0].toString()); //하나만 출력해보기
    }
    public static void main(String[] args) throws IOException {
        //getUrlInfo();
        getCertificate();
    }
}