package com.yokoro.terminal_lib.usecase.screen

interface IScreenUseCase {
    suspend fun scrollUp()
    suspend fun scrollDown()
}