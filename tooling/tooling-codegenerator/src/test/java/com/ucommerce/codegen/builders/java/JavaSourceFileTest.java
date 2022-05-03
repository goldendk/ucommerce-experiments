package com.ucommerce.codegen.builders.java;

import com.google.common.base.Charsets;
import com.ucommerce.codegen.CodegenDirector;
import com.ucommerce.testapp.FooService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaSourceFileTest {

    @TempDir
    Path root;

    @Test
    public void shouldBuildNiceFile() throws IOException {

        DirectMethodProxyBuilder builder = new DirectMethodProxyBuilder();
        CodegenDirector director = new CodegenDirector(builder);
        director.construct(FooService.class);

        //WHEN
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        assertEquals(1, generatedFiles.size());
        JavaSourceFile source = generatedFiles.get(0);
        source.writeToFile(root);


        //THEN
        Path resolve = root.resolve(Paths.get("com", "ucommerce", "testapp"));
        boolean mkdirs = root.toFile().mkdirs();
        assertTrue(root.toFile().exists());
        assertTrue(resolve.toFile().exists());

        try(FileInputStream input = new FileInputStream(resolve.toFile().getAbsolutePath() + "/" + source.getFileName())){
            String fileContent = IOUtils.toString(input, Charsets.UTF_8);
            assertEquals(FOO_SERVICE_RESULT, fileContent);
        }

    }

    private static String FOO_SERVICE_RESULT = """
package com.ucommerce.testapp;

public class FooServiceDirectProxy {

    private FooService delegate;
    
    public FooServiceDirectProxy (FooService delegate) {
        this.delegate = delegate;
    }

    public BarRecord getBar(BarQuery query){

        this.delegate.getBar(query);

    }

    public BarRecord getBar(String name){

        this.delegate.getBar(name);

    }

    public BarRecord someOtherCommand(){

        this.delegate.someOtherCommand();

    }

    public void someRandomCommand(){

        this.delegate.someRandomCommand();

    }

}
""";

}