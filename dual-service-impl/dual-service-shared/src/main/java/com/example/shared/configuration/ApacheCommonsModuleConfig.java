package com.example.shared.configuration;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;
import java.util.stream.Collectors;

public class ApacheCommonsModuleConfig extends ModuleConfig {
    private final PropertiesConfiguration configuration;

    public ApacheCommonsModuleConfig(String configurationFile) {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setFileName(configurationFile)
                                .setThrowExceptionOnMissing(true)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                                .setIncludesAllowed(false));
        try {
            configuration = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException("Could not load configuration file : " + configurationFile, e);
        }
    }

    @Override
    public List<String> getStringList(PropertyName propertyName) {
        List<String> collect = configuration.getList(propertyName.getValue()).stream().map(e -> e.toString())
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<Integer> getIntegerList(PropertyName propertyName) {
        List<Integer> collect = configuration.getList(propertyName.getValue()).stream().map(e -> Integer.parseInt(e.toString()))
                .collect(Collectors.toList());
        return collect;
    }
}
