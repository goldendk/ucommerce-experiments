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
        StringBuilder builder = new StringBuilder();
        String servicePath = HttpBuilderHelper.buildServiceHttpPath(moduleName, toConstruct);
        String methodPath = HttpBuilderHelper.convertMethodNameToUrlPath(method.getName());

        builder.append(MessageFormat.format(URI_BUILDER_TEMPLATE,
                servicePath + methodPath));

        builder.append(NEW_LINE);
        builder.append(NEW_LINE);

        switch (verb) {
            case "GET":

                for (Parameter param : method.getParameters()) {
                    if (String.class.equals(param.getType())) {
                        builder.append(MessageFormat.format(QUERY_STRING_PARAM_GUARD, param.getName()));
                    }
                }
                builder.append(NEW_LINE);
                builder.append(NEW_LINE);
                builder.append(MessageFormat.format(REQUEST_BUILDER_TEMPLATE, "GET()"));
                break;
        }


        methodBuilder.append(builder);
    }

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
