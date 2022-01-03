import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPEchoServer {
    UDPEchoServer(){
        System.out.println("UDP EchoServer is started. Portno 9001...");
        // DatagramSocket socket = new DatagramSocket();
        // socket.bind(new InetAddress(9001));
        try(DatagramSocket socket = new DatagramSocket(9001)){
            while (true){
                // 데이터그램 패킷을 수신하기
                byte[] message = new byte[1024];
                DatagramPacket packet = new DatagramPacket(message, message.length);
                socket.receive(packet);
                String data = new String(packet.getData());
                if(data.equalsIgnoreCase("quit")) break;
                System.out.println("Received from : ["+ data.trim() + "] \n"+
                        "From : "+ packet.getAddress()); //원격지(보낸 곳) 주소 출력
                // 데이터그램 패킷을 송신하기
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] sendMessage = data.getBytes(); // String --> byte[]
                DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length,
                        address, port);
                socket.send(sendPacket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new UDPEchoServer();
    }
}