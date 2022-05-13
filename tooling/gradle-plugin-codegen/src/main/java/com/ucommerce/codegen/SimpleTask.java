package com.ucommerce.codegen;

import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class SimpleTask extends DefaultTask {

    private static Logger logger = LoggerFactory.getLogger(SimpleTask.class);

    @OutputDirectory
    public File getGeneratedFileDir() {
        File file = GreetingPlugin.resolveRootGeneratedJavaFileDirectory(getProject());
        return file;
    }

    @Suppress(names = {"unused"})
    @TaskAction
    public void doWork() {
        Project project = getProject();
        logger.info("after configurations: " + project.getConfigurations().getNames());
        RestControllerExtension restControllerExtension = (RestControllerExtension) project.getExtensions().getByName("restController");
        System.out.println(getGeneratedFileDir());
        GreetingPlugin.attemptToGenerateCode(restControllerExtension, getGeneratedFileDir());

    }

}
