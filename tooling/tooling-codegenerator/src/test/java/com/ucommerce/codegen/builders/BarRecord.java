package com.ucommerce.codegen.builders;


import com.google.common.base.Preconditions;

public record BarRecord(String name, int age) {
    public BarRecord {
        Preconditions.checkNotNull(name, "Name cannot be null");
        Preconditions.checkArgument(age > 0, "Age must be greater than zero.");
    }
}
