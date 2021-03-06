buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")
    }
}

plugins {
    id("java-library")
    id("org.ucommerce.codegen")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencyManagement {
    dependencies {
        dependency("org.springframework:spring-core:4.0.3.RELEASE")
    }
}


ucommerceRestController {
    targetInterface = "org.ucommerce.modules.inventory.services.AtpService"
    moduleName = "inventory"
}


//restController {
//    targetInterface = "org.ucommerce.modules.inventory.services.AtpService"
//    //foos = null;
//  //FIXME: make the extension class in rest controller generator take a list of configs, each with a module name, target package and java @ExternalService interface
////    bars = listOf(
////        Bar("someValue"),
////        Bar("otherValue")
////    )
//}


group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.ucommerce:ucommerce-inventory-api:1.0-SNAPSHOT")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
tasks.getByName<org.gradle.jvm.tasks.Jar>("jar") {
    enabled = true
}