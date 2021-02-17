package com.yokoro.terminal_lib.core

sealed class Failure {
    abstract class FeatureClass: Failure()
}