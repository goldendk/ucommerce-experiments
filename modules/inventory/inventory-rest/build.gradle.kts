
buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
    //    classpath("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    }
}

plugins {
    id("java-library")
    id("org.ucommerce.codegen")
}

restController {
    targetInterface = "org.ucommerce.modules.inventory.AtpService"
}


group "org.ucommerce"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    ucommerceCodeGen("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")

    annotationProcessor(project(":libraries:annotation-processor"))
    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}