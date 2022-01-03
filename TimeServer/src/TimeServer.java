import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TimeServer {
    public static void main(String[] args){
        System.out.println("Time server is started.");
        try{
            ServerSocketChannel sschannel = ServerSocketChannel.open();
            sschannel.configureBlocking(true); //blocking 모드로 동작하게 설정.(클라이언트로부터 연결을 계속 기다리는 모드)
            sschannel.socket().bind(new InetSocketAddress(5000)); //5000번 포트 지정
            while (true){
                System.out.println("Server is wating for clients...");
                SocketChannel client = sschannel.accept();
                InetSocketAddress clientAddress = (InetSocketAddress) client.getRemoteAddress();
                System.out.println("Connected: "+clientAddress.getAddress()+" "+clientAddress.getPort());
                //Buffer creation
                ByteBuffer buf = ByteBuffer.allocate(64); //non direct buffer. JVM
                for(int i=0; i<10; i++){
                    String dateTime = "Date: "+ new Date(System.currentTimeMillis());
                    buf.clear(); // 이전에 입력한 데이터 삭제. position = 0, limit = capacity = 64
                    buf.put(dateTime.getBytes()); //String --> byte[]
                    buf.flip(); //writing --> reading
                    while (buf.hasRemaining()) client.write(buf);
                    System.out.println("Sent: "+ dateTime);
                    sleep(1000); //1초 대기
                }
                client.close();
            }
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}