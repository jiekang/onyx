package org.onyx.info;

import java.util.ArrayList;

public class ClassInfo {
    private final ArrayList<String> methodList = new ArrayList<>();
    private final String className;
    private final String packageName;

    public ClassInfo(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public void addMethod(String methodName) {
        this.methodList.add(methodName);
    }

    public String getClassName() {
        return this.packageName + "." + this.className;
    }

    public ArrayList<String> getMethodList() {
        return this.methodList;
    }
}
