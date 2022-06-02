package com.ucommerce.codegen.builders.java;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;

public class RpcClientBuilder extends JavaSourceBuilder {
    private String moduleName;

    private static String schemeHostAndPortFieldName = "schemeHostAndPort";
    private static String serviceRpcClientFieldName = "serviceRpcClient";

    public RpcClientBuilder(String moduleName) {
        this.moduleName = moduleName;
    }


    @Override
    protected String resolvePackage(Class toConstruct) {
        return super.resolvePackage(toConstruct) + ".rpc";
    }

    @Override
    protected String resolveClassName(Class toConstruct) {
        return toConstruct.getSimpleName() + "RpcClient";
    }

    @Override
    public void buildConstructors(Class toConstruct) {
        currentFile.getConstructors().add(COSTRUCTOR_TEMPLATE);
        currentFile.getImports().add("org.ucommerce.shared.rest.client.ServiceRpcClient");
        currentFile.getFields().add("private ServiceRpcClient " + serviceRpcClientFieldName + " = new ServiceRpcClient();");
        currentFile.getFields().add("private final String " + schemeHostAndPortFieldName + ";");

    }

    @Override
    public void buildMethodBody(Class toConstruct, Method method) {
        String verb = HttpBuilderHelper.resolveHttpVerb(method);
        StringBuilder methodBodyBuilder = new StringBuilder();
        String servicePath = HttpBuilderHelper.buildServiceHttpPath(moduleName, toConstruct);
        String methodPath = HttpBuilderHelper.convertMethodNameToUrlPath(method.getName());

        String uribuilderSection = MessageFormat.format(URI_BUILDER_TEMPLATE,
                servicePath + methodPath);

        methodBodyBuilder.append(NEW_LINE);
        methodBodyBuilder.append(SourceBuilderUtil.addIndentation(uribuilderSection, 4));

        String verbMethodCallValue = null;
        switch (verb) {
            case "GET":
            case "DELETE":
                verbMethodCallValue = verb.toUpperCase() + "()";
                break;
            case "PUT":
            case "POST":
                verbMethodCallValue = MessageFormat.format(SUBMIT_BODY_HTTP_VERB_METHOD_TEMPLATE, verb.toUpperCase());
                break;
        }

        for (Parameter param : method.getParameters()) {
            if (String.class.equals(param.getType())) {
                methodBodyBuilder.append(NEW_LINE);
                methodBodyBuilder.append(NEW_LINE);
                String queryStringGuard = MessageFormat.format(QUERY_STRING_PARAM_GUARD, param.getName());
                queryStringGuard = SourceBuilderUtil.addIndentation(queryStringGuard, 4);
                methodBodyBuilder.append(queryStringGuard);
                currentFile.getImports().add("org.apache.commons.lang3.StringUtils");
            }
        }
        if (method.getParameters().length > 0) {
            methodBodyBuilder.append(NEW_LINE);
            methodBodyBuilder.append(NEW_LINE);
        }


        String requestBuilderSection = MessageFormat.format(REQUEST_BUILDER_TEMPLATE, verbMethodCallValue);
        methodBodyBuilder.append(SourceBuilderUtil.addIndentation(requestBuilderSection, 4));
        currentFile.getImports().add("java.net.http.HttpRequest");
        currentFile.getImports().add("java.time.Duration");

        methodBodyBuilder.append(NEW_LINE);
        methodBodyBuilder.append(NEW_LINE);

        String returnType = method.getReturnType().getSimpleName();

        if ("void".equals(returnType)) {
            returnType = "null";
        } else {
            returnType = returnType + ".class";
            if (MethodHelper.isComplex(method.getReturnType())) {
                currentFile.getImports().add(method.getReturnType().getName());
            }
        }

        methodBodyBuilder.append(SourceBuilderUtil.addIndentation(MessageFormat.format(RETURN_TEMPLATE, returnType), 4));

        methodBuilder.append(methodBodyBuilder);
    }


    private static String SUBMIT_BODY_HTTP_VERB_METHOD_TEMPLATE = """
            {0}(HttpRequest.BodyPublishers.ofString(serviceRpcClient.stringify(record)))""";

    private static String RETURN_TEMPLATE = """
            return serviceRpcClient.execute(request, {0});
            """;

    private static String COSTRUCTOR_TEMPLATE =
            """
                    public CrudBarServiceRpcClient(String schemeHostAndPort) {
                        
                        serviceRpcClient.initialize();
                        this.schemeHostAndPort = schemeHostAndPort;
                                        
                    }""";

    private static String URI_BUILDER_TEMPLATE = """
            URIBuilder builder = new URIBuilder()
                    .schemeAndHost(schemeHostAndPort)
                    .appendPath("{0}");""";

    private static String QUERY_STRING_PARAM_GUARD = """
            if (StringUtils.isNotBlank({0})) '{'
                builder.appendQuery("{0}", {0});
            '}'""";
    private static String REQUEST_BUILDER_TEMPLATE = """
            HttpRequest request = HttpRequest.newBuilder()
                    .{0}
                    .uri(builder.uri())
                    //FIXME: configurable timeouts.
                    .timeout(Duration.ofSeconds(10L))
                    .build();""";
}
