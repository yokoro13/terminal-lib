package com.yokoro.terminal_lib.core

sealed class Failure {
    object UninitializedException : Failure()

    abstract class FeatureClass: Failure()
}