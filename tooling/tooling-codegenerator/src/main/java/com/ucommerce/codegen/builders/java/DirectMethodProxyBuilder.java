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
        currentFile.getFields().add("private " + toConstruct.getSimpleName() + SPACE + "delegate" + END);
    }

    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName() + "DirectProxy";
    }


}
