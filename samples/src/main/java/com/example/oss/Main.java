package com.example.oss;

import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //java -jar target\oss-example-1.0.jar ExampleName args...
        // arg[0] is class Name
        Options opts = null;

        try {
            // new
            Class<?> clazz = Class.forName("com.example.oss." + args[0]);
            Constructor<?> constructor = clazz.getConstructor();
            Example example = (Example) constructor.newInstance();

            // parser args
            String[] args1 = Arrays.stream(args, 1, args.length).toArray(String[]::new);
            CommandLineParser parser = new DefaultParser();
            opts = example.getOptions();
            CommandLine cmd = parser.parse(opts, args1);

            // run example
            example.runCmd(cmd);

        } catch (ParseException e) {
            printHelp(args[0], opts);
        } catch (Exception e) {
            System.out.println("usage: java -jar target/oss-example-1.0.jar ExampleName args...");
            System.out.println("For example: java -jar target/oss-example-1.0.jar ListBuckets --region cn-hangzhou");
        }
    }

    private static void printHelp(String name, Options opts) {
        try {
            HelpFormatter helpFormater = HelpFormatter.builder().get();
            helpFormater.printHelp(
                    "java -jar target/oss-example-1.0.jar " + name,
                    "",
                    opts,
                    "",
                    true
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
