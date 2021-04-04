package core

sealed class Failure {
    object UninitializedException : Failure()

    abstract class FeatureClass: Failure()
}