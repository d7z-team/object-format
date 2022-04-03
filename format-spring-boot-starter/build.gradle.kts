import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion: String by rootProject

plugins {
    kotlin("jvm")
    `maven-publish`
}
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    api(project(":format-core"))
    compileOnly(project(":format-extra-jackson"))
    compileOnly(project(":format-extra-gson"))
    api("org.springframework.boot", "spring-boot-autoconfigure", springBootVersion)
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor", springBootVersion)
    testImplementation(project(":format-extra-jackson"))
    testImplementation("org.springframework.boot", "spring-boot-starter-test", springBootVersion)
    testImplementation("org.springframework.boot", "spring-boot-starter-webflux", springBootVersion)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
    repositories {
        mavenLocal()
    }
}
