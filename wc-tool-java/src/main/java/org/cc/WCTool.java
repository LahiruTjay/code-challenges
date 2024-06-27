package org.cc;

import picocli.CommandLine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "WC Tool",
        description = "Word Count Tool In Java",
        version = "1.0",
        mixinStandardHelpOptions = true
)
public class WCTool implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Input File", arity = "0..1")
    private File file;

    @CommandLine.Option(names = {"-i"}, description = "Use Buffered Input Stream To Read File")
    private boolean useBufferedInputStream;

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

        String result;
        try {

            // Start measuring execution time
            long startTime = System.nanoTime();

            if (file != null) {
                if (useBufferedInputStream) {
                    result = getResultByBufferedInputStream();
                } else {
                    result = getResultByFileReadBytes();
                }
            } else {
                result = getResultBySystemIn();
            }
            System.out.println(result);

            // Stop measuring execution time
            long endTime = System.nanoTime();

            // Calculate the execution time in milliseconds
            long executionTime = (endTime - startTime) / 1000000;
            System.out.println("Method Execution takes " + executionTime + "ms");

        } catch (IOException e) {
            System.out.printf("Exception Occurred. %s", e);
        }

        return 0;
    }

    public String getResultByBufferedInputStream() throws IOException {
        byte[] bytes;
        try (FileInputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is)) {
            bytes = bis.readAllBytes();
        }
        return getResult(file.getName(), bytes, noOfBytes, noOfLines, noOfWords, noOfCharacters);
    }

    public String getResultByFileReadBytes() throws IOException {
        byte[] bytes = new byte[0];
        String fileName = "";
        if (file != null) {
            bytes = Files.readAllBytes(file.toPath());
            fileName = file.getName();
        }
        return getResult(fileName, bytes, noOfBytes, noOfLines, noOfWords, noOfCharacters);
    }

    public String getResultBySystemIn() throws IOException {
        byte[] bytes = System.in.readAllBytes();
        return getResult("", bytes, noOfBytes, noOfLines, noOfWords, noOfCharacters);
    }

    public String getResult(String fileName, byte[] bytes, boolean noOfBytes, boolean noOfLines,
                            boolean noOfWords, boolean noOfCharacters) {

        if (noOfBytes) {
            return String.format("%s %s", countNoOfBytes(bytes), fileName);
        }

        if (noOfLines) {
            return String.format("%s %s", countNoOfLines(bytes), fileName);
        }

        if (noOfWords) {
            return String.format("%s %s", countNoOfWords(bytes), fileName);
        }

        if (noOfCharacters) {
            return String.format("%s %s", countNoOfCharacters(bytes), fileName);
        }

        return String.format("%s %s %s %s", countNoOfLines(bytes), countNoOfWords(bytes),
                countNoOfBytes(bytes), fileName);
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
