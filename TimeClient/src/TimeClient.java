import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.Thread.sleep;

public class TimeClient {
    public static void main(String[] args){
        System.out.println("Time client is started.");
        SocketAddress address = new InetSocketAddress("127.0.0.1",5000);
        try{
            SocketChannel channel = SocketChannel.open(address); //서버에 접속요청
            System.out.println("Connected!");
            //Buffer creation
            ByteBuffer buf = ByteBuffer.allocate(64);
            int bytes = channel.read(buf); //channel --> 데이터 읽어와서 buf 기록.
            System.out.println("Read bytes: "+bytes);
            while (bytes != -1){
                buf.flip(); //writing --> reading
//                while (buf.hasRemaining())  System.out.print((char) buf.get());
                byte[] data = new byte[buf.limit()]; //buf.limit() 읽어야할 데이터크기
                buf.get(data);
                System.out.println(new String(data));
                System.out.println();
                sleep(1000);
                buf.clear();
                bytes = channel.read(buf);//다음거 읽기
                System.out.println(bytes+" bytes are received.");
            }
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}