package com.ucommerce.codegen;

import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.nativeplatform.Repositories;
import org.gradle.work.InputChanges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class CompileSourceTask extends JavaCompile {
    private static Logger logger = LoggerFactory.getLogger(CompileSourceTask.class);

    public CompileSourceTask() {
        setupSource();
        setupOutput();
        setupDummyClasspath();
    }

    private void setupDummyClasspath() {
        FileCollection classPath = getProject().files();
        this.setClasspath(classPath.getAsFileTree());
    }

    private void setupClasspath() {
        ConfigurableFileCollection result = getProject().getObjects().fileCollection();

        JavaPluginExtension javaExtension = getProject().getExtensions().getByType(JavaPluginExtension.class);
        SourceSet main = javaExtension.getSourceSets().getByName("main");
        Set<File> javaMainOutput = main.getOutput().getFiles()
                .stream().collect(Collectors.toSet());
        result.getFiles().addAll(javaMainOutput);
        result.from(getProject().files(javaMainOutput));

        Set<File> compileClasspath = main.getCompileClasspath().getFiles().stream().collect(Collectors.toSet());
        result.from(getProject().files(compileClasspath));

        this.setClasspath(result);
    }

    @Override
    @TaskAction
    protected void compile(InputChanges inputs) {


        setupClasspath();
        super.compile(inputs);
    }

    private void setup(){
        System.out.println("Running custom compile!");

        JavaPluginExtension javaExtension = getProject().getExtensions().getByType(JavaPluginExtension.class);

        SourceSet main = javaExtension.getSourceSets().getByName("main");
        Set<File> mainClasspath = main.getCompileClasspath().getFiles().stream().collect(Collectors.toSet());
        //      main.getRuntimeClasspath().forEach(e->logger.warn(e.getAbsolutePath()));


        //set compiler options.
        this.getOptions().getCompilerArgs().add("-parameters");
    }

    private void setupSource(){
        File file = new File(getProject().getBuildDir(), "generated/sources/ucommerce/src/main/java");
        ConfigurableFileTree source = getProject().fileTree(file);
        source.include("**/*.java");
        this.setSource(source);
    }

    private void setupOutput() {
        this.getDestinationDirectory().set(new File(getProject().getBuildDir(), "classes/java/main"));
    }



    @Override
    public DirectoryProperty getDestinationDirectory() {
        return super.getDestinationDirectory();
    }
}
