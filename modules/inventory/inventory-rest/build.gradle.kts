buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    }
}

plugins {
    id("java-library")
    id("org.ucommerce.codegen")
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

restController {
    targetInterface = "org.ucommerce.modules.inventory.services.AtpService"
}


group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}