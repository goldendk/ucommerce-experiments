package org.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

abstract public class GreetingPlugin implements Plugin<Project> {

    @Input
    abstract public Property<String> getCoordinates();

    @Input
    abstract public Property<String> getServerUrl();

    @Override
    public void apply(Project target) {
        target.getExtensions().create("greeting", GreetingExtension.class);
        target.afterEvaluate((project) -> {
            Logger logger = project.getLogger();
            GreetingExtension greetingExt = (GreetingExtension) project.getExtensions().getByName("greeting");
            logger.info("Starting code generation with settings " + greetingExt.toString());

        });
    }
}