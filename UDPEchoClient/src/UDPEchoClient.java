import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPEchoClient {
    UDPEchoClient(){
        System.out.println("UDP EchoServer is started. Portno 9001...");
        Scanner scn = new Scanner(System.in);
        try(DatagramSocket socket = new DatagramSocket()) {
            // IP주소를 알고있으면 생략가능.
            InetAddress address = InetAddress.getByName("localhost");
            byte[] message;
            while (true){
                // 데이터입력해서 데이터그램 패킷으로 만들고 전송하기
                System.out.println("Enter a message : ");
                String data = scn.nextLine();
                if(data.equalsIgnoreCase("quit")) break; //종료하기
                message = data.getBytes(); //String --> byte[]
                DatagramPacket packet = new DatagramPacket(message, message.length, address, 9001);
                socket.send(packet); //데이터그램 전송하기
                //서버로부터 데이터그램 수신하기
                byte[] recvMessage = new byte[1024]; //수신데이터가 들어갈 자리
                DatagramPacket recvPacket = new DatagramPacket(recvMessage, recvMessage.length);
                socket.receive(recvPacket);
                data = new String(recvPacket.getData()); //byte[] --> String
                System.out.println("Received data : ["+data.trim()+"] \n"+ "From : "+ recvPacket.getSocketAddress());
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        new UDPEchoClient();
    }
}