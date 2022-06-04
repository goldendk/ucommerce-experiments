package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.FQRef;
import com.ucommerce.codegen.SourceCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Builder class for Java source files. See subclasses for specific target file types.
 */
public abstract class JavaSourceBuilder implements SourceCodeBuilder {
    private static Logger logger = LoggerFactory.getLogger(JavaSourceBuilder.class);

    protected static String END = ";";
    protected static String NEW_LINE = "\n";
    private List<JavaSourceFile> generatedFiles = new ArrayList<>();
    protected JavaSourceFile currentFile = null;

    protected StringBuilder methodBuilder;
    int paramCount = 0;
    protected static final String SPACE = " ";

    @Override
    public void startClass(Class toConstruct) {
        currentFile = new JavaSourceFile(resolvePackage(toConstruct), resolveClassName(toConstruct) + ".java");
        currentFile.setTargetPackage("package " + resolvePackage(toConstruct) + END);
    }

    /**
     * Resolve and return the interfaces the generated class should implement.
     *
     * @param toConstruct
     * @return
     */
    public List<FQRef> resolveImplements(Class toConstruct) {
        List<FQRef> result = List.of(new FQRef(toConstruct.getPackageName(), toConstruct.getSimpleName()));
        return result;
    }

    @Override
    public void buildClassSignature(Class toConstruct) {
        List<String> classAnnotations = resolveClassAnnotations(toConstruct);
        currentFile.getClassSignature().addAll(classAnnotations);

        List<FQRef> implementList = resolveImplements(toConstruct);

        String signature = "public class " + resolveClassName(toConstruct);
        if (!implementList.isEmpty()) {
            signature += " implements ";
            String targetPackage = resolvePackage(toConstruct);
            implementList.stream().filter(e -> !e.packagePath().equals(targetPackage))
                    .forEach(e -> currentFile.getImports().add(e.packagePath() + "." + e.className()));
            signature += implementList.stream().map(e -> e.className()).collect(Collectors.joining(", "));
        }
        currentFile.getClassSignature().add(signature);
    }

    /**
     * Resolves the class level annotations which are added to the class signature.
     *
     * @param toConstruct
     * @return
     */
    protected List<String> resolveClassAnnotations(Class toConstruct) {
        return Collections.emptyList();
    }

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
        List<String> annotations = resolveParameterAnnotations(method, parameter);
        String annotationString = annotations.stream().collect(Collectors.joining(" "));
        if (!StringUtils.isBlank(annotationString)) {
            annotationString += SPACE;
        }
        methodBuilder.append(annotationString);
        methodBuilder.append(this.resolveParameterTypeAndName(method, parameter));
        checkAndImport(parameter.getType());
    }

    /**
     * Checks if the type needs to be imported, and if so, adds it to the list of imports.
     *
     * @param toCheck
     */
    protected void checkAndImport(Class<?> toCheck) {
        String fullyQualifiedName = toCheck.getName();
        logger.info("Check and import " + fullyQualifiedName);
        if(toCheck.isPrimitive()){
            logger.info("Param is primitive, no import needed: " + fullyQualifiedName);
            return;
        }
        String paramPackage = fullyQualifiedName;
        int index = paramPackage.lastIndexOf(".");
        paramPackage = fullyQualifiedName.substring(0, index);

        Objects.requireNonNull(currentFile.getTargetPackage(), "package statement should have been set by now!");
        if (!Objects.equals(paramPackage, currentFile.getTargetPackage())
                && !Objects.equals("java.lang", paramPackage)) { // never import java.lang
            currentFile.getImports().add(fullyQualifiedName);
        }
    }

    @Override
    public void beforeParameters() {
        methodBuilder.append("(");
    }

    @Override
    public void afterParameters() {
        methodBuilder.append(") {" + NEW_LINE);
        paramCount = 0;
    }

    public void buildMethodBody(Class toConstruct, Method method) {
        String arguments = Arrays.stream(method.getParameters()).map(e -> e.getName()).collect(Collectors.joining(", "));

        // somehow the getReturnType().equals(Void.class) is false when return type is void
        boolean shouldReturn = !method.getReturnType().getName().equals("void");

        methodBuilder.append(NEW_LINE);
        methodBuilder.append(indent(1) + (shouldReturn ? "return " : "") + "this.delegate." + resolveMethodName(method) + "(" + arguments + ")" + END);
        methodBuilder.append(NEW_LINE);
    }

    public void startMethod(Class toConstruct, Method method) {
        methodBuilder = new StringBuilder();
        List<String> annotations = resolveMethodAnnotations(method);

        for (String annotation : annotations) {
            methodBuilder.append(annotation + NEW_LINE);
        }

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


    /**
     * Resolve the annotations for the given method. Each annotation should be on a separate entry in the returned list.
     * Default implementation returns an empty list.
     *
     * @param method the method to generate annotations for.
     * @return list of strings, each a valid, compiling annotation.
     */
    protected List<String> resolveMethodAnnotations(Method method) {
        return List.of("@Override");
    }

    /**
     * Resolves the annoations for the provided method and parameter.
     * Default implementation returns an empty list.
     *
     * @return
     */
    protected List<String> resolveParameterAnnotations(Method method, Parameter parameter) {
        return Collections.emptyList();
    }

    public void finishMethodBlock(Class toConstruct, Method method) {
        methodBuilder.append(NEW_LINE + "}");
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

    @Override
    public void buildConstructors(Class toConstruct) {
        String className = resolveClassName(toConstruct);
        String paramType = toConstruct.getSimpleName();
//        if (!resolvePackage(toConstruct).equals(toConstruct.getPackageName())) {
//            currentFile.getImports().add(resolvePackage(toConstruct));
//        }
        String paramName = "delegate";
        String constructor = MessageFormat.format("""
                public {0} ({1} {2}) '{'
                    this.delegate = {2};
                '}'""", className, paramType, paramName);
        currentFile.getConstructors().add(constructor);
        checkAndImport(toConstruct);
    }


    /**
     * Return 4x&lt;space&gt; characters. Used for indenting a source file.
     *
     * @param count
     * @return
     */
    protected String indent(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += "    ";
        }
        return result;
    }


    /**
     * Resolves the <strong>target</strong> class name. The interface being generated from is provided as argument.
     *
     * @param toConstruct the interface to construct code from.
     * @return the name of the generated class.
     */
    protected abstract String resolveClassName(Class toConstruct);

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
