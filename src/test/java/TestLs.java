import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestLs {
    @Test
    void autoTest() throws IOException {
        Main.main("file1.txt -o out.txt".trim().split("\s+"));
        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("Expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2=br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }
}
