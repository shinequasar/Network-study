import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

//UDPSender
public class DatagramSender {
    private static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        ByteBuffer buf = ByteBuffer.allocate(64);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",9005);
        while(true){
            String msg;
            System.out.print("Message : ");
            msg = scn.nextLine();
            buf.put(msg.getBytes()); //바이트값으로 버퍼에저장
            buf.flip(); //기록하던걸 읽는 용도로 바꿔야 전송가능
            channel.send(buf, new InetSocketAddress("127.0.0.1",8005));
            buf.clear();
            if(msg.equalsIgnoreCase("quit")) break;
        }
    }
}