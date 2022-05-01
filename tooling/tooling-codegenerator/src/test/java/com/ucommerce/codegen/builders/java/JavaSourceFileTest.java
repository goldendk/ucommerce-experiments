package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.CodegenDirector;
import com.ucommerce.testapp.FooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaSourceFileTest {

    @TempDir
    Path root;

    @Test
    public void shouldBuildNiceFile(){

        DirectMethodProxyBuilder builder = new DirectMethodProxyBuilder();
        CodegenDirector director = new CodegenDirector(builder);
        director.construct(FooService.class);

        //WHEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
        JavaSourceFile source = generatedFiles.get(0);
        source.writeToFile(root);


        //THEN
        Path resolve = root.resolve(Paths.get("com", "ucommerce", "testapp", "FooService"));
        assertTrue(resolve.toFile().exists());


    }

}