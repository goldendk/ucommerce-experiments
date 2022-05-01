package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.SourceCodeBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for Java source files. See subclasses for specific target file types.
 */
public abstract class JavaSourceBuilder implements SourceCodeBuilder {
    protected static String END = ";";
    protected static String NEW_LINE = "\n";
    private List<JavaSourceFile> generatedFiles = new ArrayList<>();
    protected JavaSourceFile currentFile = null;

    protected StringBuilder methodBuilder;
    int paramCount = 0;
    protected static final String SPACE = " ";

    @Override
    public void startClass(Class toConstruct) {
        currentFile = new JavaSourceFile();
        currentFile.setTargetPackage("package " + resolvePackage(toConstruct) + END);
    }

    @Override
    public void finishClass() {
        generatedFiles.add(currentFile);
        currentFile = null;
    }

    @Override
    public void addParameter(Method method, Parameter parameter) {
        if (paramCount > 0) {
            methodBuilder.append("," + NEW_LINE);
        }
        paramCount++;
        methodBuilder.append(this.resolveParameterTypeAndName(method, parameter));
    }

    @Override
    public void startParameters() {
        methodBuilder.append("(");
    }

    @Override
    public void endParameters() {
        methodBuilder.append("){" + NEW_LINE);
    }

    public abstract void buildMethodBody(Class toConstruct, Method method);

    public void startMethodSignature(Class toConstruct, Method method) {
        methodBuilder = new StringBuilder();
        String visibility = resolveVisibility(method);
        String returnType = resolveReturnType(method);
        String methodName = resolveMethodName(method);
        methodBuilder.append(visibility);
        if (visibility.length() > 0) {
            methodBuilder.append(SPACE);
        }
        methodBuilder.append(returnType);
        methodBuilder.append(SPACE);
        methodBuilder.append(methodName);
    }


    public void finishMethodBlock(Class toConstruct, Method method) {
        methodBuilder.append( NEW_LINE +"}");
        currentFile.getMethods().add(methodBuilder.toString());
        methodBuilder = null;
    }


    private String resolveVisibility(Method method) {
        String result = "";
        int modifiers = method.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            result += "public";
        } else if (Modifier.isPrivate(modifiers)) {
            result += "private";
        } else if (Modifier.isProtected(modifiers)) {
            result += "protected";
        }
        return result;
    }


    /**
     * Return 4x&lt;space&gt; characters. Used for indenting a source file.
     * @param count
     * @return
     */
    protected String indent(int count) {
        String result = "";
        for(int i = 0; i < count; i++){
            result += "    ";
        }
        return result;
    }

    protected String resolveMethodName(Method method) {
        return method.getName();
    }

    public String resolveParameterTypeAndName(Method method, Parameter parameter) {
        return parameter.getType().getSimpleName() + " " + parameter.getName();
    }


    protected String resolveReturnType(Method method) {
        return method.getReturnType().getSimpleName();
    }

    protected String resolvePackage(Class toConstruct) {
        return toConstruct.getPackageName();
    }

    public List<JavaSourceFile> getGeneratedFiles() {
        return generatedFiles;
    }
}
