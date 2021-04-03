package com.yokoro.terminal_lib.core

sealed class Either<out L, out R> {
    data class Left<out L>(val a: L): Either<L, Nothing>()

    data class Right<out R>(val b: R): Either<Nothing, R>()

    val isLeft get() = this is Left<L>

    val isRight get() = this is Right<R>
}

inline fun <R> Either<*, R>.getOrElse(default: () -> R): R =
    when (this) {
        is Either.Left -> default()
        is Either.Right -> b
    }