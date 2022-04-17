plugins {
    id("java")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":dual-service-impl:dual-service-shared"))
    implementation(project(":dual-service-impl:service-a"))
    implementation(project(":dual-service-impl:service-b"))
    implementation(project(":dual-service-impl:dual-service-a-rest"))
    implementation(project(":dual-service-impl:dual-service-a-api"))
    implementation("org.slf4j:slf4j-api:1.6.1")
    implementation("org.slf4j:slf4j-simple:1.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}