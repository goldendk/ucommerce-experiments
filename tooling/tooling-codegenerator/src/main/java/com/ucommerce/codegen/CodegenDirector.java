package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.JavaSourceBuilder;
import org.ucommerce.shared.kernel.services.ExternalService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        builder.buildClassSignature(toConstruct);
        builder.buildConstructors(toConstruct);


        List<Method> methods = Arrays.stream(toConstruct.getMethods())
                .sorted(Comparator.comparing(Method::getName))
                .collect(Collectors.toList());
        for (Method method : methods) {
            builder.startMethod(toConstruct, method);
            builder.beforeParameters();
            List<Parameter> parameters = Arrays.stream(method.getParameters())
                    .sorted(Comparator.comparing(Parameter::getName))
                    .collect(Collectors.toList());
            for(Parameter param : parameters){
                builder.addParameter(method, param);
            }
            builder.afterParameters();
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
