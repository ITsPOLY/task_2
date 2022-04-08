import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TestLs {
    @Test
    public void test1() throws IOException {
        File expected = new File("expected.txt");
        expected.createNewFile();
        try (PrintWriter pw = new PrintWriter(expected)) {
            pw.println("file1.txt");
            pw.println("type text/plain");
            pw.println("creationTime: 2022-04-06T12:57:57.9935804Z");
            pw.println("lastAccessTime: 2022-04-07T22:33:44.8245664Z");
            pw.println("lastModifiedTime: 2022-04-06T12:58:43.5412714Z");
            pw.println("size: 12");
        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
        File out = new File("out.txt");
        out.createNewFile();

        Main.main("file1.txt -o out.txt".trim().split(" "));

        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2 = br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }

    @Test
    public void test2() throws IOException {
        File expected = new File("expected.txt");
        expected.createNewFile();
        try (PrintWriter pw = new PrintWriter(expected)) {
            pw.println("rth.txt");
            pw.println("111 2022-04-06T13:23:08.1337998Z 396");
            pw.println("trhc.txt");
            pw.println("111 2022-04-06T13:22:32.7243932Z 0");
            pw.println("yjt.xlsx");
            pw.println("111 2022-04-06T13:22:16.8486934Z 6571");
        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
        File out = new File("out.txt");
        out.createNewFile();

        Main.main("files -l -o out.txt".trim().split(" "));

        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2 = br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }

    @Test
    public void test3() throws IOException {
        File expected = new File("expected.txt");
        expected.createNewFile();
        try (PrintWriter pw = new PrintWriter(expected)) {
            pw.println("rth.txt");
            pw.println("RWX 0.38671875KB");
            pw.println("trhc.txt");
            pw.println("RWX 0.0KB");
            pw.println("yjt.xlsx");
            pw.println("RWX 6.4169921875KB");
        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
        File out = new File("out.txt");
        out.createNewFile();

        Main.main("files -h -o out.txt".trim().split(" "));

        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2 = br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }

    @Test
    public void test4() throws IOException {
        File expected = new File("expected.txt");
        expected.createNewFile();
        try (PrintWriter pw = new PrintWriter(expected)) {
            pw.println("yjt.xlsx");
            pw.println("RWX 6.4169921875KB");
            pw.println("trhc.txt");
            pw.println("RWX 0.0KB");
            pw.println("rth.txt");
            pw.println("RWX 0.38671875KB");
        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
        File out = new File("out.txt");
        out.createNewFile();

        Main.main("files -h -r -o out.txt".trim().split(" "));

        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2 = br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }

    @Test
    public void test5() throws IOException {
        File expected = new File("expected.txt");
        expected.createNewFile();
        try (PrintWriter pw = new PrintWriter(expected)) {
            pw.println("file2.xlsx");
            pw.println("type application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            pw.println("creationTime: 2022-04-06T12:58:17.7740455Z");
            pw.println("lastAccessTime: 2022-04-08T00:18:15.5075507Z");
            pw.println("lastModifiedTime: 2022-04-06T12:58:18.2137194Z");
            pw.println("size: 6571");
        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
        File out = new File("out.txt");
        out.createNewFile();

        Main.main("file2.xlsx -o out.txt".trim().split(" "));

        BufferedReader br = null;
        BufferedReader bt = null;
        br = new BufferedReader(new FileReader("expected.txt"));
        bt = new BufferedReader(new FileReader("out.txt"));
        String line1;
        String line2;
        while ((line1 = bt.readLine()) != null){
            line2 = br.readLine();
            Assertions.assertEquals(line1,line2);
        }
    }
}
