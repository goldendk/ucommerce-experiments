plugins {
    id("java-library")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    api("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    api(project(":inventory:inventory-core"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}