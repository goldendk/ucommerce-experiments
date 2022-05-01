package com.ucommerce.codegen.builders.java;

import com.ucommerce.codegen.GeneratedFile;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated Java source file. Just a container for the different sections of a Java source file.
 * The calling contexts are responsible for the content of each section. The sections will be put together in {@link #writeToFile(Path)}.
 */
public class JavaSourceFile extends GeneratedFile {

    private String targetPackage;
    private List<String> imports = new ArrayList<>();

    private List<String> classSignature = new ArrayList<>();

    private List<String> fields = new ArrayList<>();
    /**
     * Methods and bodies for this source file.
     */
    private List<String> methods = new ArrayList<>();


    @Override
    public void writeToFile(Path root) {
        String[] split = targetPackage.trim().replace("package", "")
                .replaceAll(";", "").split("\\.");

        Path packagePath = Path.of(split[0], ArrayUtils.subarray(split, 1, split.length));

        Path totalPath = root.resolve(packagePath);
        boolean mkdirs = totalPath.toFile().mkdirs();
        if(!mkdirs){
            throw new RuntimeException("Could not mkDirs() on: " + totalPath.toFile().getAbsolutePath());
        }

        //FIXME: add the class signature to the source file here and put the file together.
        //FileWriter writer = new FileWriter()
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public List<String> getImports() {
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
}
