package com.ucommerce.codegen;

import java.io.File;
import java.nio.file.Path;

/**
 * Generated file object.
 */
public abstract class GeneratedFile {

    private String targetPackage;
    private  String content;


    /**
     * Writes this generated file to disk.
     */
    public abstract void writeToFile(Path root);
}
