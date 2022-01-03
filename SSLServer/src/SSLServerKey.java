import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServerKey {
    public static void main(String[] args) throws Exception {
        int port = 8089;
        String clientAuth = "No";
        System.out.println("USAGE: java SslServerCmd [port [clientAuth]]");
        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) clientAuth = args[1];

        KeyStore ks = KeyStore.getInstance("JKS"); //키스토어 생성
        ks.load(new FileInputStream("server.jks"), "summer".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, "summer".toCharArray());

        SSLContext ctx = SSLContext.getInstance("TLS"); //SSL컨텍스트 생성.TLS로 작동
        ctx.init(kmf.getKeyManagers(), null, null); //컨텍스트를 키스토어로 초기화

        SSLServerSocketFactory ssf = ctx.getServerSocketFactory(); //서버소켓팩토리만들고
        SSLServerSocket ss = (SSLServerSocket)ssf.createServerSocket(port); //서버소켓만들어
        if (clientAuth.equals("Yes")) ss.setNeedClientAuth(true);

        System.out.println("Listening: port="+port+", clientAuth="+clientAuth);
        SSLSocket socket = (SSLSocket) ss.accept(); //accept

        BufferedReader in = new BufferedReader(new InputStreamReader(  //수신 객체로부터 데이터를 읽어옴
                socket.getInputStream()));
        String line = in.readLine();
        while (line.length()>0) {
            System.out.println(line);
            line = in.readLine();
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(  //데이터 송신
                new OutputStreamWriter(socket.getOutputStream())));
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("Content-Length: 40"); // including \r\n
        out.println();
        out.println("<html><body>Hello there!</body></html>");
        out.flush();

        out.close();
        in.close();
        socket.close();
    }
}
