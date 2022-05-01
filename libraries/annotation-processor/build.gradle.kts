plugins {
    id ("java" )
}

group "org.ucommerce"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {


    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
    implementation("com.google.auto.service:auto-service:1.0.1")
    testImplementation("com.google.testing.compile:compile-testing:0.19")
    testImplementation("com.google.guava:guava:16.0.1")
    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}