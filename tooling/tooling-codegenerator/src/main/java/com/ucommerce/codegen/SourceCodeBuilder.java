package com.ucommerce.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface SourceCodeBuilder {

    void startClass(Class toConstruct);

    void buildClassSignature(Class toConstruct);

    void buildConstructors(Class toConstruct);

    void startMethodSignature(Class toConstruct, Method method);

    void beforeParameters();

    void addParameter(Method method, Parameter parameterType);

    void afterParameters();

    void buildMethodBody(Class toConstruct, Method method);
    void finishMethodBlock(Class toConstruct, Method method);

    void finishClass();
}
