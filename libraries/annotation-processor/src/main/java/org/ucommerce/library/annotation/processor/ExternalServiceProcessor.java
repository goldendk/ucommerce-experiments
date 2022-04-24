package org.ucommerce.library.annotation.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes(
        "org.ucommerce.shared.kernel.services.ExternalService")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class ExternalServiceProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        System.out.println("Starting ExternalServiceProcessor");

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, processingEnv.getOptions().toString() + "\n");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Current working directory: " + new File(".").getAbsolutePath() + "\n");

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, System.getenv().toString() + "\n");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, System.getProperties().toString() + "\n");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : annotatedElements) {
                System.out.println(element);
                try {
                    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("org.ucommerce.generated.SomeFile", element);
                    try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {
                        out.println("package org.ucommerce.generated; \n public class SomeFile{}");
                    }
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, sourceFile.toUri().toString());
                    //Class<?> aClass = Class.forName("org.ucommerce.library.annotation.processor.MethodAData");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, sourceFile.toUri().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Finishing ExternalServiceProcessor");
        return true;
    }
}