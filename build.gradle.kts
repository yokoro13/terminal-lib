buildscript {

    repositories {
        google()
        mavenCentral()
        maven("https://plugin.gradle.org/m2/")
    }

    dependencies {
        classpath(GradlePlugin.kotlin)
        classpath(GradlePlugin.android)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }
}