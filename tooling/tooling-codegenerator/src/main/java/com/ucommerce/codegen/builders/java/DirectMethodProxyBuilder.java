package com.ucommerce.codegen.builders.java;

import com.google.common.base.Strings;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DirectMethodProxyBuilder extends JavaSourceBuilder {

    @Override
    public void startClass(Class toConstruct) {
        super.startClass(toConstruct);
        currentFile.getFields().add("private " + toConstruct.getSimpleName() + SPACE + "delegate" + END + NEW_LINE);
    }

    @Override
    public void buildClassSignature(Class toConstruct) {
        String result = "public class " + resolveClassName(toConstruct);
        currentFile.getClassSignature().add(result);
    }

    @Override
    public void buildConstructors(Class toConstruct) {
        String className = resolveClassName(toConstruct);
        String paramType = toConstruct.getSimpleName();
        if(!resolvePackage(toConstruct).equals(toConstruct.getPackageName())){
            currentFile.getImports().add(resolvePackage(toConstruct));
        }
        String paramName = "delegate";
        String constructor = MessageFormat.format("""
                public {0} ({1} {2}) '{'
                    this.delegate = {2};
                '}'""", className, paramType, paramName );
        currentFile.getConstructors().add(constructor);
    }

    @Override
    public void buildMethodBody(Class toConstruct, Method method) {


        String arguments = Arrays.stream(method.getParameters()).map(e -> e.getName()).collect(Collectors.joining(", "));
        methodBuilder.append(NEW_LINE);
        methodBuilder.append(indent(1) + "this.delegate." + resolveMethodName(method) + "(" + arguments + ")" + END);
        methodBuilder.append(NEW_LINE);
    }

    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName() + "DirectProxy";
    }


}
