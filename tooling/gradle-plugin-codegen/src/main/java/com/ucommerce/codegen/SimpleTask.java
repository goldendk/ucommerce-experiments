package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.SpringRestControllerBuilder;
import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public abstract class SimpleTask extends DefaultTask {

    @OutputDirectory
    public File getGeneratedFileDir() {
        File file = GreetingPlugin.resolveRootGeneratedJavaFileDirectory(getProject());
        return file;
    }

    @Suppress(names = {"unused"})
    @TaskAction
    public void doWork() {
        Project project = getProject();
        getLogger().info("after configurations: " + project.getConfigurations().getNames());
        RestControllerExtension extension = (RestControllerExtension) project.getExtensions().getByName("restController");

        System.out.println(getGeneratedFileDir());
        if(extension.getTargetInterface() == null || extension.getTargetInterface().length() == 0){
            getLogger().error("Target interface not set - not attempting to build source code.");
            return;
        }

        SpringRestControllerBuilder builder = new SpringRestControllerBuilder(extension.getModuleName());
        GreetingPlugin.attemptToGenerateCode(builder, extension.getTargetInterface(), getGeneratedFileDir());
        getLogger().info("Finished building Spring RestController source for " + extension.getTargetInterface());
    }

}
