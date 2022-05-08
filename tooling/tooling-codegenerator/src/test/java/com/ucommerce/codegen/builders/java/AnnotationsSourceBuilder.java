package com.ucommerce.codegen.builders.java;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class AnnotationsSourceBuilder extends JavaSourceBuilder {

    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName();
    }

    @Override
    protected List<String> resolveClassAnnotations(Class toConstruct) {
        return List.of("@Deprecated(since = \"a long time ago\")", "@DoesNotExist");
    }

    @Override
    protected List<String> resolveParameterAnnotations(Method method, Parameter parameter) {
        return List.of("@Nullable", "@Deprecated");
    }
}
