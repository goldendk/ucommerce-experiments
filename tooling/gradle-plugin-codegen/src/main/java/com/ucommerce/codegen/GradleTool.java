package com.ucommerce.codegen;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradleTool {
    private static Logger logger = LoggerFactory.getLogger(GradleTool.class);

    //FROM: https://melix.github.io/blog/2022/01/understanding-provider-api.html
    // and https://discuss.gradle.org/t/how-to-import-just-compiled-classes-in-build-gradle/30425/2
    public static ClassLoader loadProjectClassPath(Project project) {
        JavaPluginExtension javaExt = project.getExtensions()
                .getByType(JavaPluginExtension.class);
        List<URL> urls = new ArrayList<>();
        javaExt.getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getOutput().getFiles()
                .stream()
                .forEach(dir -> {
                    if (!dir.exists()) {
                        logger.warn("Does not exist: " + dir.getAbsolutePath());
                        return;
                    }
                    try (Stream<Path> walk = Files.walk(dir.toPath())) {
                        List<URL> classes = walk.filter(e -> {
                                    if (e.toFile().isDirectory()) {
                                        logger.info("Adding directory to classpath : " + e.toFile().getAbsolutePath());
                                    }
                                    return e.toFile().isDirectory();
                                })
                                .map(e -> e.toFile())
                                .map(e -> {
                                    try {
                                        return e.toURI().toURL();
                                    } catch (MalformedURLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }).collect(Collectors.toList());
                        urls.addAll(classes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        logger.warn("URL Contains: " + urls.toString());

        ClassLoader classloader = new URLClassLoader(urls.toArray(new URL[0]), GradleTool.class.getClassLoader());
        try {
            Class<?> aClass = classloader.loadClass("org.ucommerce.apps.TestInterface");
            logger.info("Loaded class: " + aClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return classloader;
    }
}
