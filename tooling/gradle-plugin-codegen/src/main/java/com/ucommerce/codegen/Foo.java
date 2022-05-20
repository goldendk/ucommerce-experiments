package com.ucommerce.codegen;

import org.gradle.api.tasks.Input;

public class Foo {
    private String name;
    private int age;

    @Input
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Input
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
