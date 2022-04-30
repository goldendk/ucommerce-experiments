plugins {
    id("java-library")
    //id("org.ucommerce.codegen")
}



group "org.ucommerce"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor(project(":libraries:annotation-processor"))
    implementation(project(":modules:shared-kernel"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}