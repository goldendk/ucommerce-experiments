package com.ucommerce.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface SourceCodeBuilder {

    void startClass(Class toConstruct);

    void startMethodSignature(Class toConstruct, Method method);

    void startParameters();

    void addParameter(Method method, Parameter parameterType);

    void endParameters();

    void buildMethodBody(Class toConstruct, Method method);
    void finishMethodBlock(Class toConstruct, Method method);

    void finishClass();
}
