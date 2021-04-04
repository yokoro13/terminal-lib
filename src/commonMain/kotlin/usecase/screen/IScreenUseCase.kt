package usecase.screen

interface IScreenUseCase {
    suspend fun scrollUp()
    suspend fun scrollDown()
}