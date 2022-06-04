package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.SpringRestControllerBuilder;
import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public abstract class SpringControllerTask extends DefaultTask {

    @OutputDirectory
    public File getGeneratedFileDir() {
        File file = CodegenPlugin.resolveRootGeneratedJavaFileDirectory(getProject());
        return file;
    }

    @Suppress(names = {"unused"})
    @TaskAction
    public void doWork() {
        Project project = getProject();
        getLogger().info("after configurations: " + project.getConfigurations().getNames());
        RestControllerExtension extension = (RestControllerExtension) project.getExtensions().getByName("ucommerceRestController");

        System.out.println(getGeneratedFileDir());
        if(extension.getTargetInterface() == null || extension.getTargetInterface().length() == 0){
            getLogger().error("Target interface not set - not attempting to build source code.");
            return;
        }

        SpringRestControllerBuilder builder = new SpringRestControllerBuilder(extension.getModuleName());
        CodegenPlugin.attemptToGenerateCode(project, builder, extension.getTargetInterface(), getGeneratedFileDir());
        getLogger().info("Finished building Spring RestController source for " + extension.getTargetInterface());
    }

}
