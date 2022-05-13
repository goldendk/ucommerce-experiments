package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.JavaSourceFile;
import com.ucommerce.codegen.builders.java.SpringRestControllerBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

abstract public class GreetingPlugin implements Plugin<Project> {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(GreetingPlugin.class);
    private static final String generatedSourceRootDir = "generated/sources/ucommerce";

    @Input
    abstract public Property<String> getCoordinates();

    @Input
    abstract public Property<String> getServerUrl();

    @Override
    public void apply(Project target) {
        target.getExtensions().create("greeting", GreetingExtension.class);
        target.getExtensions().create("restController", RestControllerExtension.class);
        target.getPluginManager().apply(JavaPlugin.class);

        Configuration dataFiles = target.getConfigurations().create("ucommerceCodeGen", c -> {
            c.setVisible(false);
            c.setCanBeConsumed(false);
            c.setCanBeResolved(true);
            c.setDescription("The data artifacts to be processed for this plugin.");
        });
        createSourceSets2(target, null);


        target.getTasks().withType(SimpleTask.class).configureEach(
                dataProcessing -> dataProcessing.getDataFiles().from(dataFiles));

        target.beforeEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("before configurations: " + project.getConfigurations().getNames());


        });
        target.afterEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("after configurations: " + project.getConfigurations().getNames());
            RestControllerExtension restControllerExtension = (RestControllerExtension) project.getExtensions().getByName("restController");
            logger.info("Starting code generation with settings " + restControllerExtension.toString());

            if (restControllerExtension != null) {
                attemptToGenerateCode(restControllerExtension);
            } else {
                logger.info("Not generating any RestController right now.");
            }

        });

        target.getTasks().register("simpleTask", SimpleTask.class).configure((task) -> {
            task.setGroup("UCommerce");
            task.setDescription("Code generation for UCommerce REST endpoints.");
        });
    }

    private void attemptToGenerateCode(RestControllerExtension restControllerExtension) {
        if (restControllerExtension.getTargetInterface() == null) {
            return;
        }
        SpringRestControllerBuilder builder = new SpringRestControllerBuilder();
        CodegenDirector director = new CodegenDirector(builder);
        Class cl = null;
        try {
            cl = Class.forName(restControllerExtension.getTargetInterface());
        } catch (ClassNotFoundException e) {
            logger.error("Could not load class: " + restControllerExtension.getTargetInterface(), e);
            return;
        }
        director.construct(cl);
        List<JavaSourceFile> generatedFiles = builder.getGeneratedFiles();
        for (JavaSourceFile file : generatedFiles) {
            file.writeToFile(restControllerExtension.getGeneratedFileDir().get().getAsFile().toPath());
        }
    }


    public static void createSourceSets2(Project project, File outputDirectory) {
        File rootGeneratedDir = new File(project.getBuildDir(), generatedSourceRootDir);
        rootGeneratedDir.mkdirs();
        File generatedDir = new File(rootGeneratedDir, "src/main/java");
        generatedDir.mkdirs();
        final SourceSet main = project
                .getConvention()
                .getPlugin(JavaPluginConvention.class)
                .getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        main.getJava().srcDir(generatedDir.getAbsolutePath());
    }
}