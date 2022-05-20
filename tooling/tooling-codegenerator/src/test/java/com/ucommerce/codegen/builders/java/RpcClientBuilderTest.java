package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.CodegenDirector;
import com.ucommerce.testapp.BarQuery;
import com.ucommerce.testapp.BarRecord;
import com.ucommerce.testapp.CrudBarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

publx1ic class RpcClientBuilderTest {

    RpcClientBuilder builder;
    private final static Class toBuild = CrudBarService.class;

    @BeforeEach
    public void beforeEach() {
        builder = new RpcClientBuilder("test");
    }

    @Test
    public void givenCrudBarService_whenBuildingClient_shouldBuildGoodClientSource() {

        //WHEN

        CodegenDirector director = new CodegenDirector(builder);
        director.construct(CrudBarService.class);

        //THEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        String s = generatedFiles.get(0).fillOutTemplate();

        assertEquals(clientSource, s);
    }


    @Test
    public void givenCrudBarService_whenBuildingConstructors_shouldBuildCorrectConstructor() {
        Class toConstruct = CrudBarService.class;

        builder.startClass(toConstruct);
        builder.buildConstructors(toConstruct);
        builder.finishClass();
        JavaSourceFile javaSourceFile = builder.getGeneratedFiles().get(0);

        String s = javaSourceFile.getConstructors().get(0);

        assertEquals("""
                public CrudBarServiceRpcClient(String schemeHostAndPort) {
                    
                    serviceRpcClient.initialize();
                    this.schemeHostAndPort = schemeHostAndPort;
                    
                }""", s);

        assertTrue(javaSourceFile.getImports().contains("org.ucommerce.shared.rest.client.ServiceRpcClient"));
        assertTrue(javaSourceFile.getFields().contains("private ServiceRpcClient serviceRpcClient = new ServiceRpcClient();"));
        assertTrue(javaSourceFile.getFields().contains("private final String schemeHostAndPort;"));

    }


    @Test
    public void givenSimpleGetBar_whenBuildingMethodBody_shouldBuildCorrectly() throws NoSuchMethodException {

        Method method = toBuild.getMethod("getBar", String.class);

        builder.startClass(toBuild);
        builder.startMethod(toBuild, method);
        builder.methodBuilder.setLength(0); // just want the method body
        builder.buildMethodBody(toBuild, method);

        assertEquals("""
                    URIBuilder builder = new URIBuilder()
                            .schemeAndHost(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/get-bar");
                        
                    if (StringUtils.isNotBlank(name)) {
                        builder.appendQuery("name", name);
                    }
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();""", builder.methodBuilder.toString());
    }


    String clientSource = """
            package com.ucommerce.testapp;
                        
            import com.ucommerce.testapp.BarQuery;
            import com.ucommerce.testapp.BarRecord;
            import com.ucommerce.testapp.CrudBarService;
            import org.apache.commons.lang3.StringUtils;
            import org.ucommerce.shared.rest.client.ServiceRpcClient;
                        
            import java.net.http.HttpRequest;
            import java.time.Duration;
                        
            public class CrudBarServiceRpcClient implements CrudBarService {
                        
                private ServiceRpcClient serviceRpcClient = new ServiceRpcClient();
                private final String schemeHostAndPort;
                        
                public CrudBarServiceRpcClient(String schemeHostAndPort) {
                        
                    serviceRpcClient.initialize();
                    this.schemeHostAndPort = schemeHostAndPort;
                        
                }
                        
                @Override
                public BarRecord getBar(String name) {
                        
                    URIBuilder builder = new URIBuilder()
                            .schemeAndHost(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/get-bar");
                        
                    if (StringUtils.isNotBlank(name)) {
                        builder.appendQuery("name", name);
                    }
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();
                        
                    return serviceRpcClient.execute(request, BarRecord.class);
                }
                        
                @Override
                public BarRecord getBar(BarQuery query) {
                        
                    URIBuilder builder = new URIBuilder()
                            .hostAndScheme(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/get-bar");
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(serviceRpcClient.stringify(record)))
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();
                        
                    return serviceRpcClient.execute(request, BarRecord.class);
                }
                        
                @Override
                public void deleteBar(String name) {
                    URIBuilder builder = new URIBuilder()
                            .hostAndScheme(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/delete-bar");
                        
                    if (StringUtils.isNotBlank(name)) {
                        builder.appendQuery("name", name);
                    }
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .DELETE()
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();
                        
                    return serviceRpcClient.execute(request, BarRecord.class);
                }
                        
                @Override
                public String createBar(BarRecord record) {
                    URIBuilder builder = new URIBuilder()
                            .hostAndScheme(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/create-bar");
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(serviceRpcClient.stringify(record)))
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();
                        
                    return serviceRpcClient.execute(request, BarRecord.class);
                }
                        
                @Override
                public void updateBar(BarRecord record) {
                    URIBuilder builder = new URIBuilder()
                            .hostAndScheme(schemeHostAndPort)
                            .appendPath("/ucommerce/api/test/crud-bar-service/update-bar");
                        
                    HttpRequest request = HttpRequest.newBuilder()
                            .PUT(HttpRequest.BodyPublishers.ofString(serviceRpcClient.stringify(record)))
                            .uri(builder.uri())
                            //FIXME: configurable timeouts.
                            .timeout(Duration.ofSeconds(10L))
                            .build();
                        
                    serviceRpcClient.execute(request);
                }
            }""";

}