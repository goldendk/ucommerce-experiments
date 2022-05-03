package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.NOOPJavaSourceBuilder;
import com.ucommerce.codegen.builders.java.JavaSourceBuilder;
import com.ucommerce.testapp.BarRecord;
import com.ucommerce.testapp.FooRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ucommerce.shared.kernel.services.ExternalService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodegenDirectorTest {


    JavaSourceBuilder builderSpy;

    @BeforeEach
    public void beforeEach(){
        builderSpy = spy(new NOOPJavaSourceBuilder());
    }

    @Test
    public void givenBadInterface_whenConstructing_shouldThrow() {

        //GIVEN
        CodegenDirector codegenDirector = new CodegenDirector(new NOOPJavaSourceBuilder());

        //WHEN, THEN
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> codegenDirector.construct(BadInterface.class));
        assertTrue(illegalArgumentException.getMessage().contains("@ExternalService"));

    }


    @Test
    public void givenGoodInterface_whenConstructing_shouldNotThrow() {

        //GIVEN
        CodegenDirector codegenDirector = new CodegenDirector(new NOOPJavaSourceBuilder());

        //WHEN, THEN
        assertDoesNotThrow(() -> codegenDirector.construct(GoodInterface.class));
    }

    @Test
    public void givenGoodInterface_shouldCallMethods() {
        //GIVEN
        CodegenDirector director = new CodegenDirector(builderSpy);

        //when
        director.construct(GoodInterface.class);

        //then
        Optional<Method> getBarMethod = ReflectionUtils.findMethod(GoodInterface.class, "getBar", String.class);
        assertNotNull(getBarMethod, "expected to find getBar(...)");
        Parameter nameParameter = getBarMethod.get().getParameters()[0];

        verify(builderSpy).startClass(eq(GoodInterface.class));
        verify(builderSpy).buildClassSignature(eq(GoodInterface.class));
        verify(builderSpy).startMethodSignature(eq(GoodInterface.class), eq(getBarMethod.get()));
        verify(builderSpy).addParameter(eq(getBarMethod.get()), eq(nameParameter));
        verify(builderSpy).finishMethodBlock(eq(GoodInterface.class), eq(getBarMethod.get()));

        verify(builderSpy).finishClass();

    }

    @ExternalService
    private interface GoodInterface {

        BarRecord getBar(String name);
    }


    private interface BadInterface {
        FooRecord getFoo(String name);
    }
}