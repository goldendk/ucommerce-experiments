package com.example.shared.configuration;

public class PropertyName {
    private final String value;

    private PropertyName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PropertyName of(String value) {
        return new PropertyName(value);
    }
}
