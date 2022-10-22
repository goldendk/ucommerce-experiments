plugins {
    id("java-library")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {

    api("org.ucommerce:ucommerce-orders-api-internal:1.0-SNAPSHOT")
    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")


    implementation("org.slf4j:slf4j-api:1.6.1")
    testImplementation("org.slf4j:slf4j-simple:1.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation(project(":inventory:inventory-test"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}