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

public class BytemanBuilder {

    private final File searchDirectory;
    private final File outputDirectory;

    public BytemanBuilder(String searchDirectory, String outputDirectory) {
        this(new File(searchDirectory), new File(outputDirectory));
    }

    public BytemanBuilder(File searchDirectory, File outputDirectory) {
        this.searchDirectory = searchDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void build() throws DirectoryException {
        checkDirectory(searchDirectory);
        checkDirectory(outputDirectory);

        buildRules();
    }

    private void checkDirectory(File f) throws DirectoryException {
        if (!f.exists()) {
            throw new DirectoryException("Directory: " + f.getAbsolutePath() + " does not exist.");
        }
        if (!f.isDirectory()) {
            throw new DirectoryException("Directory: " + f.getAbsolutePath() + " is not a directory");
        }
    }

    private void buildRules() {
    }
}
