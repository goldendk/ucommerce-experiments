plugins {
    id ("java-library" )
    id ("maven-publish")
}

group = "org.ucommerce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {


    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
    implementation("com.google.auto.service:auto-service:1.0.1")
    testImplementation("com.google.testing.compile:compile-testing:0.19")
    testImplementation("com.google.guava:guava:16.0.1")
    implementation("org.ucommerce:shared-kernel:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    group = "org.ucommerce"
    version = "1.0-SNAPSHOT"
    from(sourceSets.main.get().allSource)
}


publishing {
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            mavenLocal()
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            version = project.property("version").toString()
            groupId = project.property("group").toString()
            from(components["java"])
        }
    }
}
//tasks.named("compileJava") { finalizedBy("jar") }
//tasks.named("build") { finalizedBy("jar") }
//tasks.named("jar") { finalizedBy("publishToMavenLocal") }
