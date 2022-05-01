package com.ucommerce.codegen.builders.java;

import com.google.common.base.Strings;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DirectMethodProxyBuilder extends JavaSourceBuilder {

    @Override
    public void startClass(Class toConstruct) {
        super.startClass(toConstruct);
        currentFile.getFields().add("private " + toConstruct.getSimpleName() + SPACE + "delegate" + END + NEW_LINE);
    }

    @Override
    public void buildMethodBody(Class toConstruct, Method method) {


        String arguments = Arrays.stream(method.getParameters()).map(e -> e.getName()).collect(Collectors.joining(", "));
        methodBuilder.append(NEW_LINE);
        methodBuilder.append(indent(1) +"this.delegate." + resolveMethodName(method) + "(" + arguments + ")" +  END );
        methodBuilder.append(NEW_LINE);
    }


}
