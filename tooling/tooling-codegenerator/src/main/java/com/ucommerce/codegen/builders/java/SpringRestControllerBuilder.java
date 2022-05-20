package com.ucommerce.codegen.builders.java;

import org.apache.commons.text.CaseUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builds an REST controller for the UCommerce framework. Since this is generated from a standard Java interface certain
 * conventions are necessary. This builder does not build a *REST* API in the format, classic sense but rather allows the
 * {@link org.ucommerce.shared.kernel.services.ExternalService} to be invoked using HTTP calls. Client applications and modules
 * should not call the generated API directly but rather use the generated REST adapter which implements the original @ExternalService
 * interface.
 * <p>
 * This means the programming model of the client application or module can be kept Object-Oriented and never has to deal with the
 * REST implementation directly.
 */
public class SpringRestControllerBuilder extends JavaSourceBuilder {


    private final String moduleName;

    public SpringRestControllerBuilder(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    protected String resolvePackage(Class toConstruct) {
        return super.resolvePackage(toConstruct) + ".rest"; //convention for now.
    }

    @Override
    public void startClass(Class toConstruct) {
        super.startClass(toConstruct);
        currentFile.getFields().add("private " + toConstruct.getSimpleName() + SPACE + "delegate" + END);
    }

    @Override
    public List<String> resolveImplements(Class toConstruct) {
        return Collections.emptyList();
    }

    @Override
    protected List<String> resolveClassAnnotations(Class toConstruct) {

        currentFile.getImports().add("org.springframework.web.bind.annotation.RequestMapping");
        currentFile.getImports().add("org.springframework.web.bind.annotation.RestController");
        String servicePath = HttpBuilderHelper.buildServiceHttpPath(moduleName, toConstruct);
        return List.of("@RestController", "@RequestMapping(\"" + servicePath + "\")");
    }

    @Override
    protected List<String> resolveParameterAnnotations(Method method, Parameter parameter) {
        List<String> result = new ArrayList<>();

        boolean isSimple = MethodHelper.isSimple(parameter);

        if (isSimple) {
            currentFile.getImports().add("org.springframework.web.bind.annotation.RequestParam");
            result.add("@RequestParam(\"" + parameter.getName() + "\")");
        }

        return result;
    }

    @Override
    protected List<String> resolveMethodAnnotations(Method method) {
        String verb = HttpBuilderHelper.resolveHttpVerb(method);

        String rpcPath = HttpBuilderHelper.convertMethodNameToUrlPath(method.getName());

        String mappingPrefix = CaseUtils.toCamelCase(verb, true);
        String mappingClassName = mappingPrefix + "Mapping";
        String mappingAnnotation = "@" + mappingClassName + "(\"" + rpcPath + "\")";

        currentFile.getImports().add("org.springframework.web.bind.annotation." + mappingClassName);

        return List.of(mappingAnnotation);
    }


    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName() + "RestController";
    }

}
