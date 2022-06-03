import com.ucommerce.codegen.RpcClientTask
import org.gradle.kotlin.dsl.support.classFilePathCandidatesFor

apply(from = "../../gradle/common/shared-deps.gradle")
apply(from = "../../gradle/common/shared-test-deps.gradle")

plugins {
    id("java")
    id("org.ucommerce.codegen")
}

ucommerceRcpClient {
    targetInterface = "org.ucommerce.apps.TestInterface"
    moduleName = "inventory"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {

    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    implementation("org.ucommerce:rest-client-shared:1.0-SNAPSHOT")

}

tasks.getByName<JavaCompile>("compileJava") {
    options.compilerArgs.add( "-parameters")
}
