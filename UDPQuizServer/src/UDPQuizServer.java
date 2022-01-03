import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class UDPQuizServer {
    static int quizNum = 3; //출제 문항 수

    UDPQuizServer() {
        System.out.println("[EngQuizServer]");
        System.out.println("UDPQuizServer is started. Portno 9001...");
        System.out.println("The number of words in a quiz : "+quizNum);
        System.out.println("English server is waiting ...");

        try(DatagramSocket socket = new DatagramSocket(9001)){
            while (true){
                byte[] message = new byte[1024];
                DatagramPacket packet = new DatagramPacket(message, message.length);
                socket.receive(packet);
                // 수신
                String name = new String(packet.getData()).trim();
                System.out.println("New client  is connected : "+ packet.getSocketAddress());
                System.out.println("Creating a new Quiz Maker for this client : "+name);

                // 송신
                String data = "Client is connedted to the English Quiz server.";
                byte[] sendMessage = data.getBytes(); // String --> byte[]
                // 패킷 정보 담아두기
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length,
                        address, port);
                socket.send(sendPacket);


                // checkTakeTest
                System.out.println("English server is waiting ...");
                String answer;
                while (true){
                    data = "Are you ready for quiz test(yes/no)?";
                    sending(data, socket,address,port);
                    answer = receive(socket);
                    System.out.println(answer);
                    if (answer.equalsIgnoreCase("no")) {
                        sendMessage = "See you next time.".getBytes(); // String --> byte[]
                        sendPacket = new DatagramPacket(sendMessage, sendMessage.length, address, port);
                        socket.send(sendPacket);
                        System.out.println("'" + name + "' is Disconnected.\n");
                        socket.close();
                        break;
                    } else {
                        sending("Quiz test is started.\n", socket,address,port);
                        sending("3", socket,address,port);
                        LoadFile.loadFile("src/data/voca1800.txt");
                        HashMap<String, String> word = LoadFile.getWord();
                        List<String> test = testVoca(word, quizNum);
                        System.out.println("============= Quiz words =============");
                        for (int i = 0; i < quizNum; i++) {
                            String key = test.get(i);
                            System.out.println(key + " : " + word.get(key));
                        }
                        System.out.println("======================================");
                        startTest(word, test, socket, quizNum,address,port);
                    }
                }

            }
        }catch (IOException e){
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
        DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length,address, port);
        socket.send(sendPacket);
    }

    public static List<String> testVoca (HashMap < String, String > word,int quizNum) throws IOException {
        Set<String> keys = word.keySet();
        List<String> keyList = new ArrayList<>(keys);
        List<String> testVocas = new ArrayList<>();
        for (int i = 0; i < quizNum; i++) {
            String randomKey = keyList.get(new Random().nextInt(keys.size()));
            testVocas.add(randomKey);
        }
        return testVocas;
    }

    public static void startTest(HashMap<String, String> word, List<String> test, DatagramSocket socket, int quizNum,InetAddress address,int port) throws IOException {
        int correct=0;
        int wrong=0;
        for (int i = 0; i < quizNum; i++) {
            String key = test.get(i);
            sending("Question : " + key, socket,address,port);
            String answer = receive(socket);
            System.out.println("Received : "+answer);
            if(word.get(key).contains(answer)) correct++;
            else wrong++;
            System.out.println(correct+" / "+wrong);
        }
        String score = "Sent the final score : "+correct*10 +" / (total : "+quizNum*10+")\n";
        System.out.println(score);
        sending(score, socket,address,port);
    }

    public static void main(String[] args) throws IOException {
        new UDPQuizServer();
    }

}