package com.ucommerce.codegen.builders.java;

import com.ucommerce.testapp.FooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectMethodProxyBuilderTest {

    DirectMethodProxyBuilder builder;

    @BeforeEach
    public void beforeEach(){
        builder = new DirectMethodProxyBuilder();
    }
    @Test
    public void givenFooService_whenStartingClass_shouldCreateClassSignatureAndField(){

        //WHEN
        builder.startClass(FooService.class);
        builder.buildClassSignature(FooService.class);
        builder.finishClass(); // to get data.

        //THEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
        JavaSourceFile file = generatedFiles.get(0);

        assertEquals("""
                public class FooServiceDirectProxy""", file.getClassSignature().get(0));

        assertEquals("""
                            private FooService delegate;
                            """, file.getFields().get(0));
    }

    @Test
    public void givenFooService_whenBuildingGetBar_shouldCallDelegateCorrectly() throws NoSuchMethodException {
        //GIVEN
        Method getBarMethod = FooService.class.getMethod("getBar", String.class);
        Parameter nameParameter = getBarMethod.getParameters()[0];

        //WHEN
        builder.startClass(FooService.class);
        builder.startMethodSignature(FooService.class, getBarMethod);
        builder.beforeParameters();
        builder.addParameter(getBarMethod, nameParameter);
        builder.afterParameters();
        builder.buildMethodBody(FooService.class, getBarMethod);
        builder.finishMethodBlock(FooService.class, getBarMethod);
        builder.finishClass();

        //THEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals("""
                        public BarRecord getBar(String name){

                            this.delegate.getBar(name);

                        }""", generatedFiles.get(0).getMethods().get(0));

    }

    @Test
    public void givenFooService_whenBuildingConstructors_shouldBuildCorrectConstructor(){
        //GIVEN, WHEN
        builder.startClass(FooService.class);
        builder.buildConstructors(FooService.class);

        //THEN
        String constructor = builder.currentFile.getConstructors().get(0);
        assertEquals("""
                public FooServiceDirectProxy (FooService delegate) {
                    this.delegate = delegate;
                }""", constructor);

    }


}