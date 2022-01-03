import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TCPQuizServer {
    static Vector<QuizHandler> clients = new Vector<>();
    static int quizNum = 3; //출제 문항 수

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(3005);
        while (true){
            //클라이언트 접속대기
            System.out.println("[EngQuizServer]");
            System.out.println("The number of words in a quiz : "+quizNum);
            System.out.println("English server is waiting ...");
            Socket s = ss.accept(); //block mode
            System.out.println("New client  is connected : "+ s);
            //입출력 스트림 처리
            DataInputStream dis = new DataInputStream(s.getInputStream()); //수신
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //송신
            String name = dis.readUTF(); //클라이언트 이름을 수신
            System.out.println("Creating a new Quiz Maker for this client : "+name);

            QuizHandler handler = new QuizHandler(name, s, dis, dos, quizNum);
            clients.add(handler);
            Thread thread = new Thread(handler);
            thread.start();


        }
    }

    }

