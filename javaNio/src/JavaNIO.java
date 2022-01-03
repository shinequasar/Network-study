import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class JavaNIO {
    public static void printBuffer(ByteBuffer buffer,String task){
        //버퍼에 있는 데이터 출력하기
        for(int i=0; i<buffer.limit() -1; i++)
            System.out.print(buffer.get(i)+", ");
        System.out.println(buffer.get(buffer.limit()-1));

        System.out.print("Position : " + buffer.position()+", ");
        System.out.print("limit : "+buffer.limit()+", ");
        System.out.print("Capacity : "+buffer.capacity());
        System.out.println("  -->  "+task);
    }

    public static void main(String[] args){
        //버퍼생성 - allocate로 생성
        ByteBuffer buf = ByteBuffer.allocate(20); //Non direct buffer, JVM
        ByteBuffer bufDir = ByteBuffer.allocateDirect(20); //Direct buffer. Windows
        printBuffer(buf, "버퍼생성");
        CharBuffer cbuf = CharBuffer.allocate(20);
        IntBuffer ibuf = IntBuffer.allocate(30);
        CharBuffer cbufDir = ByteBuffer.allocateDirect(20).asCharBuffer(); //다이랙트버퍼인데 캐릭터버퍼로 사용하려고
//        //버퍼생성 - wrap로 생성 (이미 있는 배열로 랩핑해 버퍼 만들기)
        byte[] bytes = new byte[50];
        ByteBuffer buf2 = ByteBuffer.wrap(bytes);
        // Writing / Reading
        buf.put((byte) 7);
        buf.put((byte) 9);
        buf.put((byte) 13);
        buf.put((byte) 16);
        printBuffer(buf,"데이터삽입");

        buf.flip(); // writing --> reading. 플립을 하고나면 쓸 수 없음. 읽어야함
        printBuffer(buf,"flip");
        buf.get(new byte[2]); //읽기
        printBuffer(buf, "get 2byte");
        buf.mark(); //현재 powition 위치에 mark 기록
        buf.get(new byte[2]);
        printBuffer(buf,"현재위치에 mark, get 2byte");
        buf.reset(); //posion을 mark 위치로 이동
        printBuffer(buf, "reset");
        buf.rewind(); //처음으로 돌아감
        printBuffer(buf, "rewind");
        buf.get(new byte[2]);
        buf.compact(); //아직 읽지 않은 데이터를 앞쪽으로 옮기고 기록모드로 변경
        printBuffer(buf, "compact.읽지 않은 데이터를 앞쪽으로");
        buf.put((byte) 22);
        buf.put((byte) 22);
        buf.put((byte) 22);
        buf.put((byte) 22);
        printBuffer(buf, "추가데이터 삽입");
    }
}