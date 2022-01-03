import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
//UDPReceiver
public class DatagramReceiver {
    public static void main(String[] args) throws IOException {
        InetAddress hostIP = InetAddress.getLocalHost(); //자기자신의 IP
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8005);
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(address); //adss와 port 바인드(연결됨)
        channel.configureBlocking(true); //데이터그램을 리시브하고 샌드하는데에 대한 블로킹.

        ByteBuffer buf = ByteBuffer.allocate(64);
        while(true){
            channel.receive(buf);// 수신할 데이터가 올 때 까지 기다림
            buf.flip();
            byte[] data = new byte[buf.limit()];
            buf.get(data, 0, buf.limit());
            String msg = new String(data); //받은 데이터 문자열로 바꾸고 출력
            if(msg.equalsIgnoreCase("quit")) break;
            System.out.println(msg);
            buf.clear();
        }

    }
}