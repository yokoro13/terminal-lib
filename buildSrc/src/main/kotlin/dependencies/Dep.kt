package dependencies

object Dep {
    private const val kotlin = "1.4.30"

    object Kotlin {
        const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin"
    }

    object Test {
        const val common = "org.jetbrains.kotlin:kotlin-test:$kotlin"
        const val annotation = "org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlin"
        const val jvm = "org.jetbrains.kotlin:kotlin-test-junit:$kotlin"
        const val js = "org.jetbrains.kotlin:kotlin-test-js:$kotlin"
    }

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
    }
}