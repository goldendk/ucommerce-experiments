package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.GeneratedFile;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generated Java source file. Just a container for the different sections of a Java source file.
 * The calling contexts are responsible for the content of each section. The sections will be put together in {@link #writeToFile(Path)}.
 */
public class JavaSourceFile extends GeneratedFile {
    private static String SPACES = "                                                                      ";
    private String targetPackage;
    private Set<String> imports = new LinkedHashSet<>();

    private List<String> classSignature = new ArrayList<>();

    private List<String> fields = new ArrayList<>();
    /**
     * Methods and bodies for this source file.
     */
    private List<String> methods = new ArrayList<>();
    private List<String> constructors = new ArrayList<>();

    public JavaSourceFile(String targetPackage, String fileName) {
        super(targetPackage, fileName);
    }


    @Override
    public void writeToFile(Path root) {
        String[] split = targetPackage.trim().replace("package ", "")
                .replaceAll(";", "").split("\\.");
        List<String> list = Arrays.asList(ArrayUtils.subarray(split, 1, split.length));
        Path packagePart = Path.of(split[0], list.toArray(new String[0]));

        Path packagePath = root.resolve(packagePart);
        packagePath.toFile().mkdirs();

        if (!packagePath.toFile().exists()) {
            throw new RuntimeException("Could not mkDirs() on: " + packagePath.toFile().getAbsolutePath());
        }
        Path filePath = packagePath.resolve(Path.of(this.getFileName()));


        String sourceFile = fillOutTemplate();

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write(sourceFile);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not write " + this.getFileName(), e);
        }

    }

    public String fillOutTemplate() {

        String importSection = this.imports.stream().map(e->"import " + e + ";").collect(Collectors.joining("\n"));
        String classSignatureSection = this.classSignature.stream().collect(Collectors.joining("\n"));

        String fieldsSection = this.fields.stream().map(e -> "    " + e).collect(Collectors.joining("\n\n"));
        String constructorSection = this.constructors.stream()
                .map(e -> addIndentation(e, 4))
                .collect(Collectors.joining("\n\n"));
        String methodSection = this.methods.stream().map(e -> addIndentation(e, 4))
            .collect(Collectors.joining("\n\n"));

        return MessageFormat.format(JavaClassTemplate,
                targetPackage,
                importSection,
                classSignatureSection,
                fieldsSection,
                constructorSection,
                methodSection);
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public Set<String> getImports() {
        return imports;
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public List<String> getClassSignature() {
        return classSignature;
    }

    public List<String> getConstructors() {
        return this.constructors;
    }

    private static String addIndentation(String s, int spacesCount) {
        String spaces = SPACES.substring(0, spacesCount);
        String[] split = s.split("\n");
        String indentedMethod = Arrays.stream(split)
                .map(methodLine -> methodLine.length() == 0 ? methodLine : spaces + methodLine)
                .collect(Collectors.joining("\n"));
        return indentedMethod;
    }

    private static String JavaClassTemplate = """
            {0}
            
            {1}
            
            {2} '{'
                     
            {3}
            
            {4}
            
            {5}
                        
            '}'
            """;


}
