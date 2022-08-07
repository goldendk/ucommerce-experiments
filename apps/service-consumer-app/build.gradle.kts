apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")

plugins {
    id("java")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
}