package com.ucommerce.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface SourceCodeBuilder {

    void startClass(Class toConstruct);

    /**
     * Responsible for building the class signature. This includes any annotations that might be assigne to the class.
     * @param toConstruct
     */
    void buildClassSignature(Class toConstruct);

    /**
     * Responsible for building any constructor the output source file might need.
     * @param toConstruct
     */
    void buildConstructors(Class toConstruct);

    /**
     * Responsible for building the start of the method. Usually this wil be used to build the method name and return type.
     * This should also include any annotations for the method.
     * @param toConstruct
     * @param method
     */
    void startMethod(Class toConstruct, Method method);

    /**
     * Callback indicating that the Director will be calling {@link #addParameter(Method, Parameter)} for each parameter next.
     */
    void beforeParameters();

    void addParameter(Method method, Parameter parameterType);

    /**
     * Callback indicating that no parameters will be added to the current method.
     */
    void afterParameters();

    /**
     * Builds the method body, meaning what is contained with-in the opening and closing curly braces. Should include any
     * indentation required to make the code readable.
     * @param toConstruct
     * @param method
     */
    void buildMethodBody(Class toConstruct, Method method);

    /**
     * Responsible for finishing the method block. Usually this would just add a '}' character and line-feed to the output.
     * @param toConstruct
     * @param method
     */
    void finishMethodBlock(Class toConstruct, Method method);

    void finishClass();
}
