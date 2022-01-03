import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class QuizHandler  implements Runnable {
    //데이터를 어디로 전달해야할지 보내주는 역할.
    private String name;
    private Socket s;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final int quizNum;
    private boolean isSignin;

    public QuizHandler(String name, Socket s, DataInputStream dis, DataOutputStream dos, int quizNum) {
        this.name = name;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.quizNum = quizNum;
        isSignin = true;
    }

    @Override
    public void run() {
        try {
            while (true) {
                dos.writeUTF("Are you ready for quiz test(yes/no)?");
                String answer = dis.readUTF(); //클라이언트 퀴즈 응시여부를 수신

                if (answer.toLowerCase().equals("no")) {
                    dos.writeUTF("See you next time.");
                    System.out.println("'" + name + "' is Disconnected.\n");
                    s.close();
                    break;
                } else {
                    System.out.println("Adding this client to active client list");
                    System.out.println("English server is waiting ...");
                    LoadFile.loadFile("src/data/voca1800.txt");
                    HashMap<String, String> word = LoadFile.getWord();
                    List<String> test = testVoca(word, quizNum);
                    System.out.println("============= Quiz words =============");
                    for (int i = 0; i < quizNum; i++) {
                        String key = test.get(i);
                        System.out.println(key + " : " + word.get(key));
                    }
                    System.out.println("======================================");
                    dos.writeUTF("Quiz test is started.");
                    startTest(word, test, dis, dos, quizNum);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static List<String> testVoca(HashMap<String, String> word, int quizNum) throws IOException {
        Set<String> keys = word.keySet();
        List<String> keyList = new ArrayList<>(keys);
        List<String> testVocas = new ArrayList<>();
        for (int i = 0; i < quizNum; i++) {
            String randomKey = keyList.get(new Random().nextInt(keys.size()));
            testVocas.add(randomKey);
        }
        return testVocas;
    }

    public static void startTest(HashMap<String, String> word, List<String> test, DataInputStream dis, DataOutputStream dos, int quizNum) throws IOException {
        int correct=0;
        int wrong=0;
        for (int i = 0; i < quizNum; i++) {
            String key = test.get(i);
            dos.writeUTF("Question : " + key);
            String answer = dis.readUTF();
            System.out.println("Received : "+answer);
            if(word.get(key).contains(answer)) correct++;
            else wrong++;
            System.out.println(correct+" / "+wrong);
        }
        System.out.println("Sent the final score : "+correct*10 +" / (total : "+quizNum*10+")\n");
    }
}