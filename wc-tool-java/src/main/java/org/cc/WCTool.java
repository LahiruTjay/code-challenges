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

        File file = new File(fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());

        String result = getResult(bytes, noOfBytes, noOfLines, noOfWords, noOfCharacters);
        System.out.println(result);

        return 0;
    }

    public String getResult(byte[] bytes, boolean noOfBytes, boolean noOfLines, boolean noOfWords, boolean noOfCharacters) {
        StringBuilder result = new StringBuilder();

        if (noOfBytes) {
            return result.append(countNoOfBytes(bytes)).toString();
        }

        if (noOfLines) {
            return result.append(countNoOfLines(bytes)).toString();
        }

        if (noOfWords) {
            return result.append(countNoOfWords(bytes)).toString();
        }

        if (noOfCharacters) {
            return result.append(countNoOfCharacters(bytes)).toString();
        }

        return result.append(countNoOfLines(bytes))
                .append(" ")
                .append(countNoOfWords(bytes))
                .append(" ")
                .append(countNoOfBytes(bytes))
                .toString();
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
        String str = new String(bytes);
        String[] strArray = str.split("\\s+");
        return strArray.length;
    }

    public int countNoOfCharacters(byte[] bytes) {
        String str = new String(bytes);
        return str.toCharArray().length;
    }

}
