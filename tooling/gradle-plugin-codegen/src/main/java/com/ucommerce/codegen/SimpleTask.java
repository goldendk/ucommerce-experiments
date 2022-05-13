package com.ucommerce.codegen;

import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;

public abstract class SimpleTask extends DefaultTask {


    @InputFiles
    abstract public ConfigurableFileCollection getDataFiles();

    @Suppress(names = {"unused"})
    @TaskAction
    public void doWork() {


        System.out.println(getDataFiles().getFiles());

        //getDataFiles()

    }

}
