package org.onyx.main;

import org.onyx.builder.BytemanBuilder;
import org.onyx.builder.DirectoryException;

public class Start {

    public static void main(String[] args) {
        try {
            start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(String[] args) throws DirectoryException {
        if (args.length == 2) {
            String searchDirectory = args[0];
            String outputDirectory = args[1];
            BytemanBuilder builder = new BytemanBuilder(searchDirectory, outputDirectory);
            builder.build();
        } else {
            System.out.println("Invalid argument. Expected two arguments denoting base search directory and output directory.");
        }
    }

}
