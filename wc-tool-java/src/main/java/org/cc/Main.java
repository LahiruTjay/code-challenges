package org.cc;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new WCTool());
        commandLine.execute(args);

    }

}