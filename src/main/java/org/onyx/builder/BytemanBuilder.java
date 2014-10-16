package org.onyx.builder;

import java.io.File;

public class BytemanBuilder {

    private final String searchDirectory;
    private final String outputDirectory;

    public BytemanBuilder(String searchDirectory, String outputDirectory) {
        this.searchDirectory = searchDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void build() throws DirectoryException {
        File base = new File(searchDirectory);
        checkDirectory(base);

        File output = new File(outputDirectory);
        checkDirectory(output);

        build(base, output);
    }

    private void checkDirectory(File f) throws DirectoryException {
        if (!f.exists()) {
            throw new DirectoryException("Directory: " + f.getAbsolutePath() + " does not exist.");
        }
        if (!f.isDirectory()) {
            throw new DirectoryException("Directory: " + f.getAbsolutePath() + " is not a directory");
        }
    }

    private void build(File base, File output) {

    }
}
