import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class SSLClient {
    public static void main(String[] args) throws IOException {
//        String host = "www.oracle.com";
        String host = "127.0.0.1";
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//        SSLSocket socket = (SSLSocket) factory.createSocket(host,443);
        SSLSocket socket = (SSLSocket) factory.createSocket(host,8089);

        socket.startHandshake();//SSL handshake
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        out.println("GET http://"+host+"/index.html HTTP/1.1");
        out.println();
        out.flush();

        //서버가 주는걸 수신
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) System.out.println(line);

        in.close();
        out.close();
        socket.close();
    }
}