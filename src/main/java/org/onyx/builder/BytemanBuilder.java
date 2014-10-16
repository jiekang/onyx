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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class BytemanBuilder {

    private final File searchDirectory;
    private final File outputDirectory;

    private final ArrayList<File> fileList  = new ArrayList<File>();

    public BytemanBuilder(String searchDirectory, String outputDirectory) {
        this(new File(searchDirectory), new File(outputDirectory));
    }

    public BytemanBuilder(File searchDirectory, File outputDirectory) {
        this.searchDirectory = searchDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void build() throws DirectoryException, IOException {
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

    private void buildRules() throws IOException {
        long startTime = System.nanoTime();
        searchDir(searchDirectory);

        parseFiles();

        System.out.println("ELAPSED: " + (System.nanoTime() - startTime) / 1E9);
    }

    private void parseFiles() throws IOException {
        for (File f : fileList) {
            parseJavaFile(f);
        }
    }

    private void searchDir(File dir) {
        File[] list = dir.listFiles();

        for (File f : list) {
            if (f.isDirectory()) {
                searchDir(f);
            } else if (isJavaFile(f)) {
                fileList.add(f);
            }
        }
    }

    private boolean isJavaFile(File f) {
        if (f.getName().endsWith(".java")) {
            return true;
        }
        return false;
    }

    private void parseJavaFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        try {
            CompilationUnit cu = JavaParser.parse(fis);

            new FileVisitor().visit(cu, null);
        } catch (ParseException e) {
            System.out.println("ERROR: " + file.getAbsolutePath());
        } finally {
            fis.close();
        }

    }

    private static class FileVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            System.out.println("Class: " + n.getName());
            super.visit(n, arg);
        }
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            System.out.println("Method: " + n.getName());
            super.visit(n, arg);
        }
    }

}
