package com.ucommerce.codegen.builders.java;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains helpful methods used by builders making http related sources files.
 */
public class HttpBuilderHelper {


    /**
     * Converts a method name (Method.getName()) to a RPC friendly version.
     */
    public static String convertServiceNameToRestName(String simpleName) {

        List<String> words = MethodHelper.findWordsInMixedCase(simpleName);

        String serviceRestName = words.stream().map(String::toLowerCase).collect(Collectors.joining("-"));
        return serviceRestName;
    }

    public static String resolveHttpVerb(Method method) {
        String name = method.getName();
        String verb;
        if (StringUtils.startsWithIgnoreCase(name, "get") ||
                StringUtils.startsWithIgnoreCase(name, "load") ||
                StringUtils.startsWithIgnoreCase(name, "fetch")) {
            if (MethodHelper.hasComplexParameters(method) || MethodHelper.hasVoidReturnType(method)) {
                verb = "POST";
            } else {
                verb = "GET";
            }
        } else if (StringUtils.startsWithIgnoreCase(name, "create")) {
            verb = "POST";
        } else if (StringUtils.startsWithIgnoreCase(name, "update")) {
            verb = "PUT";
        } else if (StringUtils.startsWithIgnoreCase(name, "delete")) {
            verb = "DELETE";
        } else {
            if (MethodHelper.hasComplexParameters(method) || MethodHelper.hasVoidReturnType(method)) {
                verb = "POST";
            } else {
                verb = "GET";
            }
        }
        return verb;
    }

    /**
     * Builds the path of the service made for the given interface.
     * @param moduleName
     * @param toConstruct - the interface building source code for.
     * @return
     */
    public static String buildServiceHttpPath(String moduleName, Class toConstruct) {
        String serviceRestName = HttpBuilderHelper.convertServiceNameToRestName(toConstruct.getSimpleName());
        String path = "/ucommerce/api/" + moduleName + "/" + serviceRestName;
        return path;
    }

    /**
     * Converts a Java method naming scheme into a RPC URL friendly one. E.g. "getBar" --&gt; "get-bar".
     *
     * @param name
     * @return
     */
    public static String convertMethodNameToUrlPath(String name) {
        List<String> wordsInMixedCase = MethodHelper.findWordsInMixedCase(name);
        String urlName = wordsInMixedCase.stream().map(StringUtils::lowerCase).collect(Collectors.joining("-"));
        return "/" + urlName;
    }
}
