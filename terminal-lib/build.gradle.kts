import dependencies.Dep
import dependencies.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
}

group = "yokoro.com"
version = "1.0-SNAPSHOT"

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode (Versions.androidVersionCode )
        versionName (Versions.androidVersionName)
    }
}

group = "com.yokoro.terminal-lib"

kotlin {
    android()
    ios() {
        binaries {
            framework()
        }
    }

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
        val commonMain by getting {
            dependencies {
                implementation(Dep.Kotlin.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dep.Test.common)
                implementation(Dep.Test.annotation)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Dep.Kotlin.jvm)
            }
        }
        val jvmTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Dep.Test.jvm)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.0")
            }
        }
        val androidTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Dep.Test.jvm)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(Dep.Kotlin.js)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(Dep.Test.js)
            }
        }
        val nativeMain by getting {
            dependencies {

            }
        }
        val nativeTest by getting {
            dependencies {

            }
        }
        val iosMain by getting {
            dependencies {

            }
        }
        val iosTest by getting {
            dependencies {

            }
        }
    }
}
