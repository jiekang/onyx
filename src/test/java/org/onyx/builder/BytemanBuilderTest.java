package org.onyx.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class BytemanBuilderTest {

    @Test(expected = DirectoryException.class)
    public void testDirectoryDoesNotExist() throws IOException, DirectoryException {
        String file = "doesNotExist.file";

        BytemanBuilder builder = new BytemanBuilder(file, file);
        builder.build();
    }

    @Test(expected = DirectoryException.class)
    public void testInputIsNotDirectory() throws IOException, DirectoryException {
        File file = Files.createTempFile("temp", ".file").toFile();
        file.deleteOnExit();

        BytemanBuilder builder = new BytemanBuilder(file.getAbsolutePath(), file.getAbsolutePath());
        builder.build();
    }
}
