package org.example;

import kotlin.Suppress;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class SimpleTask extends DefaultTask {

    @Suppress(names = {"unused"})
    @TaskAction
    public void doWork() {

    //    GreetingPlugin.createSourceDir(getProject());

    }

}
