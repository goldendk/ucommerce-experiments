package com.example.shared.configuration;

import java.util.List;

/**
 * Configuration for a module.
 */
public abstract class ModuleConfig {
    private static ModuleConfig INSTANCE = null;

    public static ModuleConfig loadConfiguration() {
        if (INSTANCE == null) {
            INSTANCE = new ApacheCommonsModuleConfig("application.properties");
        }
        return INSTANCE;
    }

    public abstract List<String> getStringList(PropertyName propertyName);

    public abstract List<Integer> getIntegerList(PropertyName propertyName);

}
