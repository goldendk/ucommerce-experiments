plugins {
    id("java-gradle-plugin")
    id("java-library")

}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("ucommerce-codegen") {
            id = "org.ucommerce.codegen"
            implementationClass = "com.ucommerce.codegen.GreetingPlugin"
        }
    }
}

dependencies {
    implementation(project(":tooling-codegenerator"))
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}