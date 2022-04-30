plugins {
    id("java-gradle-plugin")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("ucommerce-codegen") {
            id = "org.ucommerce.codegen"
            implementationClass = "com.ucommerce.codegen.GreetingPlugin"
        }
    }
}