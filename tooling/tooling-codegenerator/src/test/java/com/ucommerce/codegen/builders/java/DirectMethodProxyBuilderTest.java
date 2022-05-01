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
    public void givenFooService_whenStartingClass_shouldCreateField(){

        //WHEN
        builder.startClass(FooService.class);
        builder.finishClass(); // to get data.

        //THEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
        JavaSourceFile file = generatedFiles.get(0);

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
        builder.startParameters();
        builder.addParameter(getBarMethod, nameParameter);
        builder.endParameters();
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


}