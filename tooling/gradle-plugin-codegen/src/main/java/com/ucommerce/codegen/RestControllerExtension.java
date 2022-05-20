package com.ucommerce.codegen;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

import java.util.List;

public class RestControllerExtension {

    private String targetInterface;

    @Input
    private List<Bar>  bars;
    @OutputDirectory
    private RegularFileProperty generatedFileDir;
    private String moduleName;

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
