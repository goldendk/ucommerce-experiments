apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")

plugins {
    id("java")
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    //spring
    implementation("org.springframework.boot:spring-boot-starter-web")


    implementation("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    implementation("org.ucommerce:inventory-core:1.0-SNAPSHOT")
    testImplementation("org.ucommerce:inventory-test:1.0-SNAPSHOT")


}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}