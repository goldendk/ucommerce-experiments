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

    //implementation("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")

    implementation("org.slf4j:slf4j-api:1.6.1")
    implementation("org.slf4j:slf4j-simple:1.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}