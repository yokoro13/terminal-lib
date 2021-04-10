buildscript {

    repositories {
        google()
        mavenCentral()
        maven("https://plugin.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
    }
}

repositories {
    google()
}

allprojects {
    repositories {
        google()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}