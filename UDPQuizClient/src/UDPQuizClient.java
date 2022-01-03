import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPQuizClient {
    int port = 9001;
    byte[] message = new byte[1024];
    InetAddress address = InetAddress.getLocalHost();
    UDPQuizClient() throws UnknownHostException {
        System.out.println("UDP EchoServer is started. Portno 9001...");
        Scanner scn = new Scanner(System.in);

        try (DatagramSocket socket = new DatagramSocket()) {
            System.out.println("[EngQuizClient]");
            while (true) {
                //전송
                System.out.print("Name : ");
                String data = scn.nextLine();
                if (data.equalsIgnoreCase("quit")) break; //종료하기
                sending(data, socket, address,port);

                //수신
                byte[] recvMessage = new byte[1024];
                DatagramPacket recvPacket = new DatagramPacket(recvMessage, recvMessage.length);
                socket.receive(recvPacket);
                data = new String(recvPacket.getData());
                System.out.println(data.trim());

                while (true){
                    System.out.println(receive(socket));  //Are you
                    data = scn.nextLine();
                    sending(data,socket,address,port); //yes or no

                    System.out.println(receive(socket)); //qiz start
                    int quizNum = Integer.parseInt(receive(socket));
                    System.out.println("출제문항 수 : 정" + quizNum );
                    for(int i=0;i<quizNum;i++){
                        System.out.println(receive(socket));
                        data = scn.nextLine();
                        sending(data,socket,address,port);
                    }
                    System.out.println(receive(socket)); //점수
                    System.out.println("======================================");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receive(DatagramSocket socket) throws IOException {
        byte[] recvMessage = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvMessage, recvMessage.length);
        socket.receive(recvPacket);
        return new String(recvPacket.getData()).trim();
    }

    public static void sending(String message, DatagramSocket socket,InetAddress address,int port) throws IOException {
        byte[] sendMessage= message.getBytes();
        DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, address, port);
        socket.send(packet);
    }

    public static void main(String[] args) throws UnknownHostException {
        new UDPQuizClient();
    }
}