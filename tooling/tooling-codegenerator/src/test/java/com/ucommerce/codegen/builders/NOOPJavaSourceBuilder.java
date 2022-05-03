package com.ucommerce.codegen.builders;

import com.ucommerce.codegen.builders.java.JavaSourceBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class NOOPJavaSourceBuilder extends JavaSourceBuilder {


    @Override
    public void buildClassSignature(Class toConstruct) {

    }

    @Override
    public void buildConstructors(Class toConstruct) {

    }

    @Override
    public void buildMethodBody(Class toConstruct, Method method) {

    }

    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName()+"NOOP";
    }
}
