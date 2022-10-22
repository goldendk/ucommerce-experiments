plugins {
    id("java-library")
    id("maven-publish")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    //annotationProcessor("org.ucommerce:annotation-processor:1.0-SNAPSHOT")
    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<JavaCompile>("compileJava") {
    options.compilerArgs.add( "-parameters")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}