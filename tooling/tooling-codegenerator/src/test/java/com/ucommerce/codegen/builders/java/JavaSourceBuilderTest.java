package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.CodegenDirector;
import com.ucommerce.codegen.builders.NOOPJavaSourceBuilder;
import com.ucommerce.testapp.FooService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class JavaSourceBuilderTest {


    @Test
    public void givenFooService_startingClass_generatePackageStatement() {

        //GIVEN
        Class toBuild = FooService.class;

        //WHEN
        NOOPJavaSourceBuilder spy = spy(new NOOPJavaSourceBuilder());
        spy.startClass(toBuild);
        spy.finishClass(); // to get result.

        //THEN
        JavaSourceFile javaSourceFile = spy.getGeneratedFiles().get(0);
        assertEquals("package com.ucommerce.testapp;", javaSourceFile.getTargetPackage());
        assertEquals("FooServiceNOOP.java", javaSourceFile.getFileName());
    }

    @Test
    public void givenFooService_whenClassSignature_generateClassSignature() {

        //GIVEN
        Class toBuild = FooService.class;

        //WHEN
        NOOPJavaSourceBuilder spy = spy(new NOOPJavaSourceBuilder());
        spy.startClass(toBuild);
        spy.finishClass(); // to get result.

        //THEN
        JavaSourceFile javaSourceFile = spy.getGeneratedFiles().get(0);
        assertEquals("package com.ucommerce.testapp;", javaSourceFile.getTargetPackage());
    }


    @Test
    public void givenFooService_whenBuildingSomeRandomCommand_thenShouldGenerateMethod() throws NoSuchMethodException {

        //GIVEN
        Class toBuild = FooService.class;
        Method someRandomCommand = toBuild.getMethod("someRandomCommand");
        assertNotNull(someRandomCommand, "someRandomCommand not found");

        //WHEN
        NOOPJavaSourceBuilder spy = spy(new NOOPJavaSourceBuilder());
        spy.startClass(toBuild);
        spy.startMethod(toBuild, someRandomCommand);
        spy.beforeParameters();
        spy.afterParameters();
        spy.finishMethodBlock(toBuild, someRandomCommand); // to get result.
        spy.finishClass(); // to get result.

        //THEN
        JavaSourceFile javaSourceFile = spy.getGeneratedFiles().get(0);
        assertEquals(javaSourceFile.getMethods().size(), 1);
        String s = javaSourceFile.getMethods().get(0);
        assertEquals("""
                @Override
                public void someRandomCommand(){
                                                              
                }""", s); // no implementation in abstract super class.
    }

    @Test
    public void givenMethodWithReturnType_whenBuildingSomeOtherCommand_shouldGenerateGoodMethod() throws NoSuchMethodException {
        //GIVEN
        Class toBuild = FooService.class;
        Method someRandomCommand = toBuild.getMethod("someOtherCommand");
        assertNotNull(someRandomCommand, "someOtherCommand not found");

        //WHEN
        NOOPJavaSourceBuilder spy = spy(new NOOPJavaSourceBuilder());
        spy.startClass(toBuild);
        spy.startMethod(toBuild, someRandomCommand);
        spy.beforeParameters();
        spy.afterParameters();
        spy.finishMethodBlock(toBuild, someRandomCommand); // to get result.
        spy.finishClass(); // to get result.

        //THEN
        JavaSourceFile javaSourceFile = spy.getGeneratedFiles().get(0);
        assertEquals(javaSourceFile.getMethods().size(), 1);
        String s = javaSourceFile.getMethods().get(0);
        assertEquals("""
                @Override
                public BarRecord someOtherCommand(){
                          
                }""", s); // no implementation in abstract super class.
    }

    @Test
    public void givenMethodWithSimpleParam_whenBuildingSomeOtherCommand_shouldGenerateGoodMethod() throws NoSuchMethodException {
        //GIVEN
        Class toBuild = FooService.class;
        Method methodToBuild = toBuild.getMethod("getBar", String.class);
        assertNotNull(methodToBuild, "getBar not found");

        //WHEN
        NOOPJavaSourceBuilder spy = spy(new NOOPJavaSourceBuilder());
        spy.startClass(toBuild);
        spy.startMethod(toBuild, methodToBuild);
        spy.beforeParameters();
        spy.addParameter(methodToBuild, methodToBuild.getParameters()[0]);
        spy.afterParameters();
        spy.finishMethodBlock(toBuild, methodToBuild); // to get result.
        spy.finishClass(); // to get result.

        //THEN
        JavaSourceFile javaSourceFile = spy.getGeneratedFiles().get(0);
        assertEquals(javaSourceFile.getMethods().size(), 1);
        String s = javaSourceFile.getMethods().get(0);
        assertEquals("""
                @Override
                public BarRecord getBar(String name){
                                
                }""", s); // no implementation in abstract super class.
    }


    @Test
    public void givenBuilderThatAddsAnnotations_whenAddingParam_shouldWriteCorrectMethodSignature() {

        AnnotationsSourceBuilder builder = new AnnotationsSourceBuilder();
        CodegenDirector codegenDirector = new CodegenDirector(builder);

        //WHEN
        codegenDirector.construct(FooService.class);

        //THEN

        JavaSourceFile sourceFile = builder.getGeneratedFiles().get(0);

        List<String> classSignature = sourceFile.getClassSignature();
        assertEquals(3, classSignature.size());
        assertEquals("@Deprecated(since = \"a long time ago\")", classSignature.get(0));
        assertEquals("@DoesNotExist", classSignature.get(1));

        String method = sourceFile.getMethods().get(1);//getBar(String name)...

        assertEquals("""
                @Override
                public BarRecord getBar(@Nullable @Deprecated String name){
                    
                    return this.delegate.getBar(name);
                    
                }""", method);

    }


}