package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.RpcClientBuilder;
import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class RpcClientTask extends DefaultTask {

    private static Logger logger = LoggerFactory.getLogger(RpcClientTask.class);

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
        RpcClientExtension extension = (RpcClientExtension) project.getExtensions().getByName(CodegenPlugin.RPC_CLIENT_EXTENSION_NAME);

        if (extension.getModuleName() == null || extension.getModuleName().length() == 0) {
            getLogger().error("Missing configuration: moduleName.");
            throw new RuntimeException("Not working...");
        }

        if (extension.getTargetInterface() == null || extension.getTargetInterface().length() == 0) {
            getLogger().error("Missing configuration: targetInterface.");
            throw new RuntimeException("Not working...");
        }

        System.out.println(getGeneratedFileDir());
        RpcClientBuilder builder = new RpcClientBuilder(extension.getModuleName());
        CodegenPlugin.attemptToGenerateCode(project, builder, extension.getTargetInterface(), getGeneratedFileDir());

        logger.info("Finished building RcpClient code for " + extension.getTargetInterface() + " with module " + extension.getModuleName());
    }

}
