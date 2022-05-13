package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.CodegenDirector;
import com.ucommerce.testapp.BarQuery;
import com.ucommerce.testapp.BarRecord;
import com.ucommerce.testapp.CrudBarService;
import com.ucommerce.testapp.FooService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringRestControllerBuilderTest {


    @Test
    public void givenCrudService_whenMappingSimpleGetBar_thenBuildGetRestMethod() throws NoSuchMethodException {
        //GIVEN
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();

        Class toBuild = CrudBarService.class;
        Method method = toBuild.getMethod("getBar", String.class);

        builder.startClass(toBuild);

        //WHEN
        builder.startMethod(toBuild, method);
        builder.beforeParameters();
        for (Parameter parameter : method.getParameters()) {
            builder.addParameter(method, parameter);
        }
        builder.afterParameters();
        builder.buildMethodBody(toBuild, method);
        builder.finishMethodBlock(toBuild, method);
        builder.finishClass(); // get result

        //THEN
        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);


        String methodSource = sourceFile.getMethods().get(0);


        assertEquals("""
                @GetMapping("/get-bar")
                public BarRecord getBar(@RequestParam("name") String name){
                    
                    return this.delegate.getBar(name);
                                
                }""", methodSource);

        assertEquals(
                "org.springframework.web.bind.annotation.GetMapping",
                sourceFile.getImports().iterator().next());
    }

    @Test
    public void givenCrudService_whenMappingComplexGetBar_thenBuildPostRestMethod() throws NoSuchMethodException {
        //GIVEN
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();

        Class toBuild = CrudBarService.class;
        Method method = toBuild.getMethod("getBar", BarQuery.class);

        builder.startClass(toBuild);

        //WHEN
        builder.startMethod(toBuild, method);
        builder.beforeParameters();
        for (Parameter parameter : method.getParameters()) {
            builder.addParameter(method, parameter);
        }
        builder.afterParameters();

        builder.buildMethodBody(toBuild, method);
        builder.finishMethodBlock(toBuild, method);
        builder.finishClass(); // get result

        //THEN
        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);


        String methodSource = sourceFile.getMethods().get(0);


        assertEquals("""
                @PostMapping("/get-bar")
                public BarRecord getBar(BarQuery query){
                    
                    return this.delegate.getBar(query);
                                
                }""", methodSource);

    }


    @Test
    public void givenCrudService_whenMappingDeleteBar_thenBuildDeleteRestMethod() throws NoSuchMethodException {
        //GIVEN
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();

        Class toBuild = CrudBarService.class;
        Method method = toBuild.getMethod("deleteBar", String.class);

        builder.startClass(toBuild);

        //WHEN
        builder.startMethod(toBuild, method);
        builder.beforeParameters();
        for (Parameter parameter : method.getParameters()) {
            builder.addParameter(method, parameter);
        }
        builder.afterParameters();

        builder.buildMethodBody(toBuild, method);
        builder.finishMethodBlock(toBuild, method);
        builder.finishClass(); // get result

        //THEN
        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);


        String methodSource = sourceFile.getMethods().get(0);


        assertEquals("""
                @DeleteMapping("/delete-bar")
                public void deleteBar(@RequestParam("name") String name){
                    
                    this.delegate.deleteBar(name);
                                
                }""", methodSource);

        assertTrue(sourceFile.getImports().contains("org.springframework.web.bind.annotation.RequestParam"));

    }

    @Test
    public void givenCrudService_whenMappingCreateBar_thenBuildPostRestMethod() throws NoSuchMethodException {
        //GIVEN
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();

        Class toBuild = CrudBarService.class;
        Method method = toBuild.getMethod("createBar", BarRecord.class);

        builder.startClass(toBuild);

        //WHEN
        builder.startMethod(toBuild, method);
        builder.beforeParameters();
        for (Parameter parameter : method.getParameters()) {
            builder.addParameter(method, parameter);
        }
        builder.afterParameters();

        builder.buildMethodBody(toBuild, method);
        builder.finishMethodBlock(toBuild, method);
        builder.finishClass(); // get result

        //THEN
        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);


        String methodSource = sourceFile.getMethods().get(0);


        assertEquals("""
                @PostMapping("/create-bar")
                public String createBar(BarRecord record){
                    
                    return this.delegate.createBar(record);
                                
                }""", methodSource);


    }



    @Test
    public void givenCrudService_whenMappingUpdateBar_thenBuildPutRestMethod() throws NoSuchMethodException {
        //GIVEN
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();

        Class toBuild = CrudBarService.class;
        Method method = toBuild.getMethod("updateBar", BarRecord.class);

        builder.startClass(toBuild);

        //WHEN
        builder.startMethod(toBuild, method);
        builder.beforeParameters();
        for (Parameter parameter : method.getParameters()) {
            builder.addParameter(method, parameter);
        }
        builder.afterParameters();

        builder.buildMethodBody(toBuild, method);
        builder.finishMethodBlock(toBuild, method);
        builder.finishClass(); // get result

        //THEN
        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);


        String methodSource = sourceFile.getMethods().get(0);


        assertEquals("""
                @PutMapping("/update-bar")
                public void updateBar(BarRecord record){
                    
                    this.delegate.updateBar(record);
                                
                }""", methodSource);


    }


    @Test
    public void givenFooService_whenBuildingUsingDirector_thenShouldGenerateGoodControllerSource() {
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();
        CodegenDirector director = new CodegenDirector(builder);

        //WHEN
        director.construct(FooService.class);

        //THEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());

        JavaSourceFile controllerFile = generatedFiles.get(0);

        String controllerSource = controllerFile.fillOutTemplate();
        assertEquals("""     
                package com.ucommerce.testapp.rest;
                                
                                
                import org.springframework.web.bind.annotation.RequestMapping;
                import org.springframework.web.bind.annotation.RestController;
                import org.springframework.web.bind.annotation.PostMapping;
                import org.springframework.web.bind.annotation.GetMapping;
                import org.springframework.web.bind.annotation.RequestParam;
                                
                @RestController
                @RequestMapping("/ucommerce/api/inventory/atp-service")
                public class FooServiceRestController {
                                
                    private FooService delegate;
                                
                    public FooServiceRestController (FooService delegate) {
                        this.delegate = delegate;
                    }
                                
                    @PostMapping("/get-bar")
                    public BarRecord getBar(BarQuery query){
                                
                        return this.delegate.getBar(query);
                                
                    }
                                
                    @GetMapping("/get-bar")
                    public BarRecord getBar(@RequestParam("name") String name){
                                
                        return this.delegate.getBar(name);
                                
                    }
                                
                    @GetMapping("/some-other-command")
                    public BarRecord someOtherCommand(){
                                
                        return this.delegate.someOtherCommand();
                                
                    }
                                
                    @GetMapping("/some-random-command")
                    public void someRandomCommand(){
                                
                        this.delegate.someRandomCommand();
                                
                    }
                                
                }
                """, controllerSource);

    }

}