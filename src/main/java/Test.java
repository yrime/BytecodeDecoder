import filestructure.FileAnalyze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Test {
    public static void main(String[] argv){
        System.out.println("this test");
        Path path = Paths.get(argv[0]);
        try {
            FileAnalyze fa = new FileAnalyze(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
