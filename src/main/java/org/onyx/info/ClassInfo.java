package org.onyx.info;

import java.util.ArrayList;

public class ClassInfo {
    private final ArrayList<String> methodList = new ArrayList<>();
    private String className;
    private String packageName = "";

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
