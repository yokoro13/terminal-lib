plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "yokoro.com"
version = "1.0-SNAPSHOT"

android {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(14)
        targetSdkVersion(30)
    }

    sourceSets.forEach {
        it.manifest.srcFile("src/andtoidMain/AndtoidManifest.xml")
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://plugins.gradle.org/m2/")
}

group = "com.yokoro.terminal-lib"

kotlin {
    android {
        publishLibraryVariants()
    }
    ios() {
        binaries {
            framework()
        }
    }
    iosArm32("iosArm32")

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(LEGACY) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        hostOs == "iOS" -> iosArm64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependsOn(commonTest)
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
        val iosMain by getting
        val iosTest by getting
    }
}
