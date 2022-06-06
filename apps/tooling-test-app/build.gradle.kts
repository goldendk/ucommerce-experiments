import com.ucommerce.codegen.RpcClientTask
import org.gradle.kotlin.dsl.support.classFilePathCandidatesFor

apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")

repositories {
    mavenLocal()
    mavenCentral()
}


plugins {
    id("java")
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.ucommerce.codegen")

}

ucommerceRcpClient {
    targetInterface = "org.ucommerce.apps.TestInterface"
    moduleName = "test"
}

ucommerceRestController{
    targetInterface = "org.ucommerce.apps.TestInterface"
    moduleName = "test"
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
    finalizedBy("rpcClient", "springController", "compileGeneratedSource")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

