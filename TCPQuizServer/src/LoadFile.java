import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadFile {
    private static HashMap<String, String> word = new HashMap<>();
    public static boolean loadFile(String path) throws IOException {
        ArrayList<String> values = new ArrayList<String>();
        word.clear();
        File file = new File(path);
        if(!file.exists()) return false;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null) {
            line = line.replaceFirst(",", "/");  //첫 ,를 /로 바꾼 후 /기준으로 자름
            String[] split = line.split("/");
            if(!addVoca(split[0].toLowerCase(), split[1]))
                System.out.println(split[0] + " is duplicated!!");
            else {
                word.put(split[0],split[1]);
            }
        }
        reader.close();
        return true;
    }

    private static boolean addVoca(String toLowerCase, String s) {
        return !toLowerCase.equals(s);
    }

    public static HashMap<String, String> getWord() {
        return word;
    }
}