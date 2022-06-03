apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")


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

    api("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.9.0")

}