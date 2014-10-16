/*
    Onyx Alpha
    Copyright (C) 2014  Jie Kang

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.onyx.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class BytemanBuilderTest {

    @Test(expected = DirectoryException.class)
    public void testDirectoryDoesNotExist() throws IOException, DirectoryException {
        File file = new File("doesNotExist");

        BytemanBuilder builder = new BytemanBuilder(file, file);
        builder.build();
    }

    @Test(expected = DirectoryException.class)
    public void testInputIsNotDirectory() throws IOException, DirectoryException {
        File file = Files.createTempFile("temp", ".file").toFile();
        file.deleteOnExit();

        BytemanBuilder builder = new BytemanBuilder(file, file);
        builder.build();
    }
}
