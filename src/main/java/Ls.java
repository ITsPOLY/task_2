import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

public class Ls {
    @Option(name = "-l")
    private boolean isLong;
    @Option(name = "-h")
    private boolean isHumanReadable;
    @Option(name = "-r")
    private boolean isReverse;
    @Option(name = "-o")
    private String outputFile = null;
    @Argument()
    private String directoryOrFile;

    private ArrayList<String> fileParam = new ArrayList<>();
    private String[] content;

    public void basic(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
        }

        if (isDirectory()) {
            outputInfoFromDirectory();
        } else outputInfoFromFile();

        if (isLong || isHumanReadable) {
            getRWX();
        }

        if (outputFile == null) {
            writeToConsole();
        } else writeToFile();

    }

    public boolean isDirectory() {
        File file = new File(directoryOrFile);
        if (file.isDirectory()) {
            return true;
        } else return false;
    }

    public void outputInfoFromFile() throws IOException {
        File file = new File(directoryOrFile);
        Path path = file.toPath();
        fileParam.add(path.toString());
        String probeContentType = Files.probeContentType(path);
        fileParam.add("type " + probeContentType);
        BasicFileAttributes attrib = Files.readAttributes(path, BasicFileAttributes.class);
        fileParam.add("creationTime: " + attrib.creationTime().toString());
        fileParam.add("lastAccessTime: " + attrib.lastAccessTime().toString());
        fileParam.add("lastModifiedTime: " + attrib.lastModifiedTime().toString());
        fileParam.add("size: " + Long.toString(attrib.size()));

    }

    public void outputInfoFromDirectory() {
        File directory = new File(directoryOrFile);
        content = directory.list();
        Arrays.stream(content).sorted();
        if (isReverse) {
            invertInfo();
        }
    }

    public void writeToConsole() {
        if (!isDirectory()) {
            for (int i = 0; i < fileParam.size(); i++) {
                System.out.println(fileParam.get(i));
            }
        } else {
            for (int i = 0; i < content.length; i++) {
                System.out.println(content[i]);
                if (isLong || isHumanReadable) {
                    System.out.println(fileParam.get(i));
                    System.out.println();
                }
            }
        }
    }

    public void writeToFile() {
        try (PrintWriter pw = new PrintWriter(new File(outputFile))) {
            File outFile = new File(outputFile);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            if (isDirectory()) {
                for (int i = 0; i < content.length; i++) {
                    pw.println(content[i]);
                    if (isLong || isHumanReadable) {
                        pw.println(fileParam.get(i));
                        pw.println();
                    }
                }
            } else {
                for (int i = 0; i < fileParam.size(); i++) {
                    pw.println(fileParam.get(i));
                }
            }

        } catch (IOException e) {
            System.out.println("ERROR " + e);
        }
    }

    public void invertInfo() {
        for (int i = 0; i < content.length / 2; i++) {
            String tmp = content[i];
            content[i] = content[content.length - i - 1];
            content[content.length - i - 1] = tmp;
        }
    }

    public void getRWX() throws IOException {
        try {
            for (int i = 0; i < content.length; i++) {
                StringBuilder res = new StringBuilder();
                File file = new File(directoryOrFile + "/" + content[i]);
                if (!file.exists()) {
                    file.createNewFile();
                }
                Path path = file.toPath();
                BasicFileAttributes attrib = Files.readAttributes(path, BasicFileAttributes.class);
                if (isLong) {
                    if (file.canRead()) {
                        res.append(1);
                    } else res.append(0);
                    if (file.canWrite()) {
                        res.append(1);
                    } else res.append(0);
                    if (file.canExecute()) {
                        res.append(1);
                    } else res.append(0);
                    fileParam.add(res + " " + attrib.lastModifiedTime().toString() + " " + attrib.size());
                } else {
                    if (file.canRead()) {
                        res.append("R");
                    } else res.append("-");
                    if (file.canWrite()) {
                        res.append("W");
                    } else res.append("-");
                    if (file.canExecute()) {
                        res.append("X");
                    } else res.append("-");
                    fileParam.add(res.toString() + " " + setSizeAttrib(attrib.size()));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String setSizeAttrib(long size) {
        StringBuilder res = new StringBuilder();
        if (size >= 1073741824) {
            res.append((double) size / 1073741824.0 + "GB");
        } else if (size >= 1048576) {
            res.append((double) size / 1048576.0 + "MB");
        } else {
            res.append((double) size / 1024.0 + "KB");
        }
        return res.toString();
    }
}

