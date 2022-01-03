import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args){
        try {
            //클라이언트의 접속을 처리하는 서버소켓 생성. 포트번호 지정.
            ServerSocket ss = new ServerSocket(8000);
            //클라이언트의 접속을 대기
            System.out.println("Server is wating for client..");
            Socket client = ss.accept();
            System.out.println("Connected client : "+client.getInetAddress().getHostAddress()+" : "
            +client.getPort());
            //데이터 송수신을 위한 입출력 객체생성
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); //수신스트림
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); //송신스트림
            out.println("Welcome to Echo server!!");
            out.flush(); //송신 버퍼의 모든 것을 전송
            while(true){
                //클라이언트 메세지 수신 후 그대로 전송
                String msg = in.readLine();
                System.out.println("Client's msg : "+ msg);
                if(msg.equals("BYE") || msg == null) break;
                out.println(msg);
                out.flush();
            }
               client.close();
        }catch (IOException e){
            e.printStackTrace();;
        }
    }

}