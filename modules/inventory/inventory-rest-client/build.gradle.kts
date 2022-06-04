apply(from ="../../../gradle/common/shared-deps.gradle")

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    }
}

plugins {
    id("java-library")
    id("org.ucommerce.codegen")
}

ucommerceRcpClient {
    targetInterface = "org.ucommerce.modules.inventory.services.AtpService"
    moduleName = "inventory"
}

repositories {
    mavenCentral()
}

dependencies {

    //implementation("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    api("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    api("org.ucommerce:rest-client-shared:1.0-SNAPSHOT")
    implementation("org.slf4j:slf4j-api:1.6.1")
    implementation("org.slf4j:slf4j-simple:1.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}