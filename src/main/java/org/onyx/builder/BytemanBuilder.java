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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.onyx.info.ClassInfo;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class BytemanBuilder {

    private final File searchDirectory;
    private final File outputDirectory;
    private final ArrayList<File> fileList  = new ArrayList<>();
    private final ArrayList<ClassInfo> classList = new ArrayList<>();
    private String entryTemplate;
    private String exitTemplate;
    private String allRules = "";

    public BytemanBuilder(String searchDirectory, String outputDirectory) {
        this(new File(searchDirectory), new File(outputDirectory));
    }

    public BytemanBuilder(File searchDirectory, File outputDirectory) {
        this.searchDirectory = searchDirectory;
        this.outputDirectory = outputDirectory;
    }

    public void build() throws DirectoryException, IOException, URISyntaxException {
        checkDirectory(searchDirectory);
        checkDirectory(outputDirectory);

        loadTemplates();

        buildRules();
    }

    private void loadTemplates() throws URISyntaxException, IOException {
        URL entryURL = this.getClass().getResource("/org/onyx/template/BytemanEntryRule.template");
        Path entryPath = Paths.get(entryURL.toURI());
        entryTemplate = new String(Files.readAllBytes(entryPath));

        URL exitURL = this.getClass().getResource("/org/onyx/template/BytemanExitRule.template");
        Path exitPath = Paths.get(exitURL.toURI());
        exitTemplate = new String(Files.readAllBytes(exitPath));
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

        createRules();

        outputRules();

        System.out.println("ELAPSED: " + (System.nanoTime() - startTime) / 1E9);
    }

    private void createRules() {
        for (ClassInfo classInfo : classList) {
            String entryRules = createEntryRules(classInfo);
            String exitRules = createExitRules(classInfo);

            allRules = allRules + entryRules + "\n" + exitRules + "\n";
        }
    }

    private String createExitRules(ClassInfo classInfo) {
        return createRules(classInfo, exitTemplate,"EXIT:");
    }

    private String createEntryRules(ClassInfo classInfo) {
        return createRules(classInfo, entryTemplate, "ENTRY:");
    }

    private String createRules(ClassInfo classInfo, String template, String message) {
        String rule = "";
        String className = classInfo.getClassName();
        for (String method : classInfo.getMethodList()) {
            String methodRule = template;
            methodRule = methodRule.replaceAll("@RULE_MESSAGE", "Rule : " + className);
            methodRule = methodRule.replaceAll("@CLASS_NAME", className);
            methodRule = methodRule.replaceAll("@METHOD_NAME", method);
            methodRule = methodRule.replaceAll("@MESSAGE", "BM:" + message + className + "." + method);
            rule = rule + methodRule + "\n\n";
        }
        return rule;
    }

    private void outputRules() throws IOException {
        File output = new File(outputDirectory, "bytemanRules.btm");
        output.createNewFile();

        FileOutputStream fos = new FileOutputStream(output);
        fos.write(allRules.getBytes());
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
        if (f.getName().endsWith(".java") && !f.getName().contains("Test")) {
            return true;
        }
        return false;
    }

    private void parseJavaFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        try {
            CompilationUnit cu = JavaParser.parse(fis);

            new FileVisitor(cu.getPackage().getName().toString()).visit(cu, null);
        } catch (NullPointerException | ParseException e) {
            System.out.println("ERROR: " + file.getAbsolutePath());
        } finally {
            fis.close();
        }

    }

    private class FileVisitor extends VoidVisitorAdapter {
        private final String packageName;
        private ClassInfo currentClass;

        public FileVisitor(String packageName) {
            this.packageName = packageName;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            currentClass = new ClassInfo(packageName, n.getName());
            classList.add(currentClass);
            super.visit(n, arg);
        }
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            if (currentClass != null) {
                currentClass.addMethod(n.getName());
            }
            super.visit(n, arg);
        }
    }

}
