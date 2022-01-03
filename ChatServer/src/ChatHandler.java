import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ChatHandler implements Runnable{  //데이터를 어디로 전달해야할지 보내주는 역할.
    private String name;
    private Socket s;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private boolean isSignin;

    public ChatHandler(String name, Socket s, DataInputStream dis, DataOutputStream dos){
        this.name = name;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        isSignin = true;
    }

    @Override
    public void run() {
        System.out.println(name + " is chatting...");
        String received;
        while (true){
            try {
                received = dis.readUTF();
                System.out.println("Message : "+ received+ " from "+name);
                if(received.equals("Bye")) break;
                if(!received.contains("#")) continue;
                StringTokenizer tokenizer = new StringTokenizer(received, "#");
                String who = tokenizer.nextToken();
                String msg = tokenizer.nextToken();
                for(ChatHandler c : ChatServer.clients){
                    if(c.name.equals(who)&&c.isSignin){
                        c.dos.writeUTF(who + ">> "+msg);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}