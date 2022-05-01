package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.JavaSourceBuilder;
import org.ucommerce.shared.kernel.services.ExternalService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Top level class in a builder pattern (See "Gang of four, Builder Pattern").
 * This CodegenDirector is meant to build different outputs for interfaces with @ExternalService annotation.
 */
public class CodegenDirector {
    private final JavaSourceBuilder builder;

    public CodegenDirector(JavaSourceBuilder builder) {
        this.builder = builder;
    }


    /**
     * Constructs the source files needed by invoking builder part methods on the {@link JavaSourceBuilder} interface.
     *
     * @param toConstruct must be annotated with {@link ExternalService}
     */
    public void construct(Class toConstruct) {
        validateToConstruct(toConstruct);

        builder.startClass(toConstruct);

        for (Method method : toConstruct.getMethods()) {
            builder.startMethodSignature(toConstruct, method);
            builder.startParameters();
            for(Parameter param : method.getParameters()){
                builder.addParameter(method, param);
            }
            builder.endParameters();
            builder.buildMethodBody(toConstruct, method);
            builder.finishMethodBlock(toConstruct, method);
        }

        builder.finishClass();

    }

    private void validateToConstruct(Class toConstruct) {
        Annotation[] annotationsByType = toConstruct.getAnnotationsByType(ExternalService.class);
        if (annotationsByType == null || annotationsByType.length == 0) {
            throw new IllegalArgumentException("Interface provided must have @ExternalService annotation");
        }
    }
}
