package org.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;

abstract public class GreetingPlugin implements Plugin<Project> {

    private static final String generatedSourceRootDir = "generated/sources/ucommerce";

    @Input
    abstract public Property<String> getCoordinates();

    @Input
    abstract public Property<String> getServerUrl();

    @Override
    public void apply(Project target) {

        target.getExtensions().create("greeting", GreetingExtension.class);
//        target.getPlugins().withType(JavaPlugin.class, new Action<JavaPlugin>() {
//            @Override
//            public void execute(final JavaPlugin plugin) {
//                SourceSetContainer sourceSets = (SourceSetContainer)
//                        target.getProperties().get("sourceSets");
//                sourceSets.getByName("main").getResources().getSrcDirs()
//                        .add(task.getOutput().getParentFile());
//                Copy resourcesTask = (Copy) tasks.getByName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);
//                resourcesTask.dependsOn(task);
//            }
//        });

//moved to before evaulate for now.
//        File generatedDir = new File(target.getBuildDir(), "generated/sources/foo/src/main/java");
//        boolean sourceDirSuccess = generatedDir.mkdirs();
//        final Logger logger2 = target.getLogger();
//        logger2.info("Created source dir: " + sourceDirSuccess);
//
//
//        SourceSetContainer sourceSets = (SourceSetContainer)
//                target.getProperties().get("sourceSets");
//        sourceSets.getByName("main").getResources()
//                .srcDir("build/");

        target.getPluginManager().apply(JavaPlugin.class);
        createSourceSets2(target, null);
        target.beforeEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("before configurations: " + project.getConfigurations().getNames());


        });
        target.afterEvaluate((project) -> {
            final Logger logger = project.getLogger();
            logger.info("after configurations: " + project.getConfigurations().getNames());
        //    registerSourceSets(project);

            GreetingExtension greetingExt = (GreetingExtension) project.getExtensions().getByName("greeting");
            logger.info("Starting code generation with settings " + greetingExt.toString());

        });

        target.getTasks().register("simpleTask", SimpleTask.class).configure((task) -> {
            task.setGroup("UCommerce");
            task.setDescription("Code generation for UCommerce REST endpoints.");
        });
    }


    public static void createSourceSets2(Project project, File outputDirectory){
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
    public static void createSourceDir(Project project) {
        final Logger logger2 = project.getLogger();
        File rootGeneratedDir = new File(project.getBuildDir(), generatedSourceRootDir);
        rootGeneratedDir.mkdirs();
        File generatedDir = new File(rootGeneratedDir, "src/main/java");
        logger2.info("Build dir: " + project.getBuildDir().getAbsolutePath());
      //  logger2.info("Build dir2: " + Arrays.stream(project.getBuildDir().list()).map(e -> e.toString()).collect(Collectors.joining(", ")));
        if (!project.getBuildDir().exists()) {
            project.getBuildDir().mkdirs();
        }
        boolean sourceDirSuccess = generatedDir.mkdirs();
        logger2.info("Created source dir: " + sourceDirSuccess + " at " + generatedDir.getAbsolutePath());

//        if (!sourceDirSuccess) {
//            throw new RuntimeException("Source dir not created: " + generatedDir.getAbsolutePath());
//        }
//        if (!generatedDir.exists()) {
//            throw new RuntimeException("Dir does not exist" + generatedDir.getAbsolutePath());
//        }


    }
}