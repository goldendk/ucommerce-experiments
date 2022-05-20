package com.ucommerce.codegen;

import com.ucommerce.codegen.builders.java.JavaSourceBuilder;
import com.ucommerce.codegen.builders.java.JavaSourceFile;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

abstract public class GreetingPlugin implements Plugin<Project> {
    public static final String RPC_CLIENT_EXTENSION_NAME = "ucommerceRcpClient";
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(GreetingPlugin.class);
    private static final String generatedSourceRootDir = "generated/sources/ucommerce";

    @Input
    abstract public Property<String> getCoordinates();

    @Input
    abstract public Property<String> getServerUrl();

    @Override
    public void apply(Project target) {
        target.getExtensions().create("greeting", GreetingExtension.class);
        target.getExtensions().create("restController", RestControllerExtension.class);
        target.getExtensions().create(RPC_CLIENT_EXTENSION_NAME, RpcClientExtension.class);
        target.getPluginManager().apply(JavaPlugin.class);
        prepareGeneratedJavaSourceFolder(target);

        target.beforeEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("before configurations: " + project.getConfigurations().getNames());


        });
        target.afterEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("after configurations: " + project.getConfigurations().getNames());
            RestControllerExtension restControllerExtension = (RestControllerExtension) project.getExtensions().getByName("restController");

            if (restControllerExtension != null) {
                //         attemptToGenerateCode(restControllerExtension, restControllerExtension.getGeneratedFileDir());
            } else {
                logger.info("Not generating any RestController right now.");
            }

        });

        target.getTasks().register("simpleTask", SimpleTask.class).configure((task) -> {
            task.setGroup("UCommerce");
            task.setDescription("Code generation for UCommerce REST endpoints.");
            //FIXME: Add up-to-date never here...
        });
        target.getTasks().register("rpcClient", RpcClientTask.class).configure((task) -> {
            task.setGroup("UCommerce");
            task.setDescription("Generates code for an RPC client (HTTP based).");
            task.getOutputs().upToDateWhen((task1)->false);
        });
    }

    /**
     * Creates a new configuration where dependencies can be added to. Not necessary currently since the buildScript-&gt;dependencies (classpath)
     * can add code dynamically to the plugins.
     *
     * @param target
     */
    private void createNewConfigurationForDependencies(Project target) {
        //FIXME: Remove this if we don't use the ucommerceCodeGen configuration in consumer build files.
        Configuration dataFiles = target.getConfigurations().create("ucommerceCodeGen", c -> {
            c.setVisible(false);
            c.setCanBeConsumed(false);
            c.setCanBeResolved(true);
            c.setDescription("The data artifacts to be processed for this plugin.");
        });
    }

    public static void attemptToGenerateCode(JavaSourceBuilder codeBuilder, String targetInterface, File generatedFileDir) {

        if (targetInterface == null) {
            System.out.println("Target interface is null, returning...");
            return;
        }
        logger.info("Target interface is : " + targetInterface);

        CodegenDirector director = new CodegenDirector(codeBuilder);
        Class cl = null;
        try {
            cl = Class.forName(targetInterface);
        } catch (ClassNotFoundException e) {
            logger.error("Could not load class: " + targetInterface, e);
            return;
        }
        director.construct(cl);
        List<JavaSourceFile> generatedFiles = codeBuilder.getGeneratedFiles();
        for (JavaSourceFile file : generatedFiles) {
            System.out.println(file.fillOutTemplate());
            file.writeToFile(generatedFileDir.toPath());
        }
    }


    public static void prepareGeneratedJavaSourceFolder(Project project) {
        File generatedJavaDir = resolveRootGeneratedJavaFileDirectory(project);
        generatedJavaDir.mkdirs();
        final SourceSet main = project
                .getExtensions().getByType(JavaPluginExtension.class)
                .getSourceSets()
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        main.getJava().srcDir(generatedJavaDir.getAbsolutePath());
    }

    /**
     * Returns the root of the generated file folder. Basically this is the "src/main/java" folder where classes can be
     * outputted.
     *
     * @return
     */
    public static File resolveRootGeneratedJavaFileDirectory(Project project) {
        File rootGeneratedDir = new File(project.getBuildDir(), generatedSourceRootDir);
        rootGeneratedDir.mkdirs();
        File generatedJavaDir = new File(rootGeneratedDir, "src/main/java");
        return generatedJavaDir;
    }
}