package com.semihbg.gorun.server.integration;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class RunCode {

    public static void main(String[] args) throws IOException, InterruptedException {
        new RunCode().runCode();
    }

    static final String TEST_CODE_PATH="test-codes";
    static final String GO_FILE_NAME="RunCode.go";

    void runCode() throws IOException, InterruptedException {
        String fullPath=System.getProperty("user.dir")
                .concat(File.separator).concat(TEST_CODE_PATH)
                .concat(File.separator).concat(GO_FILE_NAME);
        ProcessBuilder builder = new ProcessBuilder()
                .command("go", "run",fullPath)
                .redirectErrorStream(true);
        Process process=builder.start();
        Thread printOutputThread=new Thread(()->{
            try(InputStream inputStream=process.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream)){
                char[] b=new char[1];
                while(inputStreamReader.read(b)!=-1)
                    System.out.print(b[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"printOutputThread");

        Thread getInputThread=new Thread(()->{
            Scanner scanner=new Scanner(System.in);
            PrintWriter printWriter=new PrintWriter(process.getOutputStream(),true);
            while(true){
                String input=scanner.nextLine();
                printWriter.println(input);
            }
        },"getInputThread");

        printOutputThread.setDaemon(true);
        getInputThread.setDaemon(true);

        printOutputThread.start();
        getInputThread.start();

        process.waitFor();

    }

}
