package com.ucommerce.codegen;

import java.io.File;
import java.nio.file.Path;

/**
 * Generated file object.
 */
public abstract class GeneratedFile {

    private String targetPackage;
    private String fileName;
    private  String content;

    public GeneratedFile(String targetPackage, String fileName){
        this.targetPackage = targetPackage;
        this.fileName = fileName;
    }

    /**
     * Writes this generated file to disk.
     */
    public abstract void writeToFile(Path root);

    public String getTargetPackage() {
        return targetPackage;
    }

    public String getFileName() {
        return fileName;
    }


}
