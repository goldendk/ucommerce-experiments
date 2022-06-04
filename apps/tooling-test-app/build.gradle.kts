import com.ucommerce.codegen.RpcClientTask
import org.gradle.kotlin.dsl.support.classFilePathCandidatesFor

apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")

plugins {
    id("java")
    id("org.ucommerce.codegen")
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

}

ucommerceRcpClient {
    targetInterface = "org.ucommerce.apps.TestInterface"
    moduleName = "test"
}

ucommerceRestController{
    targetInterface = "org.ucommerce.apps.TestInterface"
    moduleName = "test"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {

    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    implementation("org.ucommerce:rest-client-shared:1.0-SNAPSHOT")


    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/net.bytebuddy/byte-buddy
    testImplementation("net.bytebuddy:byte-buddy:1.12.10")


}

tasks.getByName<JavaCompile>("compileJava") {
    options.compilerArgs.add( "-parameters")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}