package org.cc;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "WC Tool",
        description = ""
)
public class WCTool implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Input File")
    private String fileName;

    @CommandLine.Option(names = {"-c"}, description = "Number of Bytes in the File")
    private boolean noOfBytes;

    @CommandLine.Option(names = {"-l"}, description = "Number of Lines in the File")
    private boolean noOfLines;

    @CommandLine.Option(names = {"-w"}, description = "Number of Words in the File")
    private boolean noOfWords;

    @CommandLine.Option(names = {"-m"}, description = "Number of Characters in the File")
    private boolean noOfCharacters;

    @Override
    public Integer call() throws IOException {

        System.out.println(noOfBytes);
        System.out.println(noOfLines);
        System.out.println(noOfWords);
        System.out.println(noOfCharacters);

        File file = new File(fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());

        System.out.println(countNoOfBytes(bytes));
        System.out.println(countNoOfLines(bytes));
        System.out.println(countNoOfWords(bytes));
        System.out.println(countNoOfCharacters(bytes));

        return 0;
    }

    public void getResult() {

    }

    public int countNoOfBytes(byte[] bytes) {
        return bytes.length;
    }

    public int countNoOfLines(byte[] bytes) {
        int count = 0;
        for (byte fileByte : bytes) {
            if (fileByte == '\n') {
                count++;
            }
        }
        return count;
    }

    public int countNoOfWords(byte[] bytes) {
        int count = 0;
        for (byte fileByte : bytes) {
            if (fileByte == ' ') {
                count++;
            }
        }
        return count;
    }

    public int countNoOfCharacters(byte[] bytes) {
        String str = new String(bytes);
        return str.toCharArray().length;
    }

}
