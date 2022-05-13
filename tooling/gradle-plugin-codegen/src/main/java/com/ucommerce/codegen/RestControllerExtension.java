package com.ucommerce.codegen;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputDirectory;

public class RestControllerExtension {

    private String targetInterface;

    @OutputDirectory
    private RegularFileProperty generatedFileDir;


    public String getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(String targetInterface) {
        this.targetInterface = targetInterface;
    }

    public RegularFileProperty getGeneratedFileDir() {
        return generatedFileDir;
    }

    public void setGeneratedFileDir(RegularFileProperty generatedFileDir) {
        this.generatedFileDir = generatedFileDir;
    }
}
