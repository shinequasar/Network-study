import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPQuizClient {
    final static int ServerPort = 3005;
    private static boolean threadSatus = true;
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in); //키보드입력
        InetAddress ipAddr = InetAddress.getByName("localhost");
        Socket s = new Socket(ipAddr, ServerPort);
        System.out.println("[EngQuizClient]");
        System.out.println("Client is connedted to the English Quiz server.");
        //입출력 스트림 처리
        DataInputStream dis = new DataInputStream(s.getInputStream()); //수신
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //송신
        System.out.print("Name : ");
        String name = scn.nextLine();
        dos.writeUTF(name); //클라이언트 이름을 서버에 전송


        // 전송쓰레드 생성
        Thread sending = new Thread(new Runnable() { //스레드오브젝트 생성, 익명클래스기법
            @Override
            public void run() {
                while (threadSatus){
                    String msg = scn.nextLine();
                    try {
                        dos.writeUTF(msg);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                try {
                    s.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        Thread receiving = new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadSatus){
                    try {
                        String msg = dis.readUTF();
                        if(msg.equals("See you next time.")){ //상대방이접속종료
                            System.out.println(msg+" bye!");
                            break;
                        }
                        System.out.println(msg);
                    }catch (IOException e){
                       e.printStackTrace();
                    }
                }
                try {
                    s.close();
                    threadSatus = false;
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        //쓰레드 시작
        sending.start();
        receiving.start();

    }


}