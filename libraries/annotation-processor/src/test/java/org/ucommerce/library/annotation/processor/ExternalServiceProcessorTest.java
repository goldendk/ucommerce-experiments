package org.ucommerce.library.annotation.processor;

import com.google.common.collect.ImmutableList;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class ExternalServiceProcessorTest {

    @Test
    public void shouldCompileServiceWithoutIgnoreAnnotationWithoutErrors() throws IOException {

        /**
         * Should try with compiler args for output location for sources
         * def generatedSources = "$buildDir/generated-src"
         * options.compilerArgs = [
         *                 '-s', "${generatedSources}"
         *         ]
         */

        Compilation compilation =
                javac()
                        .withOptions(List.of("-s",  "\\d\\sandbox\\workspaces\\ucommerce-experiments\\"))
                        .withProcessors(new ExternalServiceProcessor())
                        .compile(JavaFileObjects.forResource("test-data/annotation-processor/CoreFooService.java"));
        System.out.println(compilation.compiler().options());
        assertThat(compilation).succeededWithoutWarnings();
        ImmutableList<JavaFileObject> javaFileObjects = compilation.generatedFiles();
        for(JavaFileObject jfo : javaFileObjects){
            System.out.println("Java file generated: " +jfo.toUri());
            System.out.println(new String(jfo.getCharContent(true).toString()));
        }

//        ASSERT.about(javaSource())
//                .that(JavaFileObjects.forResource("test-data/annotation-processor/CoreFooService.java"))
//                .processedWith(new ExternalServiceProcessor())
//                .compilesWithoutError();
    }
}