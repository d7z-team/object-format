group = "com.github.d7z-team.object-format"

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.open-edgn.cn/maven/")
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.2.1")
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    for (childProject in childProjects.values) {
        delete(childProject.buildDir)
    }
}
