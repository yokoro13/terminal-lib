import entity.TerminalArray
import repository.TerminalRepository
import usecase.cursor.*
import usecase.escapesequence.EscapeSequenceUseCase
import usecase.screen.ScreenUseCase
import usecase.terminal.*
import usecase.terminalbuffer.*
import viewmodel.TerminalViewModel

class TerminalViewController {
    private val terminalRepository: TerminalRepository = TerminalRepository()
    private val terminalBufferUseCase = TerminalBufferUseCase(
        AddNewRow(terminalRepository),
        GetTerminalBuffer(terminalRepository),
        SetText(terminalRepository),
        SetForeColor(terminalRepository),
        SetBackColor(terminalRepository)
    )

    private val cursorUseCase = CursorUseCase(
        SetCursor(terminalRepository),
        GetCursor(terminalRepository),
        SetDisplayingState(terminalRepository),
        IsDisplaying(terminalRepository)
    )

    private val terminalUseCase = TerminalUseCase(
        CreateTerminal(terminalRepository),
        Resize(terminalRepository),
        GetScreenSize(terminalRepository),
        SetTopRow(terminalRepository),
        GetTopRow(terminalRepository),
        terminalBufferUseCase
    )

    private val screenUseCase = ScreenUseCase(
        terminalBufferUseCase,
        terminalUseCase,
        cursorUseCase
    )

    private val escapeSequenceUseCase = EscapeSequenceUseCase(
        GetScreenSize(terminalRepository),
        GetTopRow(terminalRepository),
        SetTopRow(terminalRepository),
        cursorUseCase,
        terminalBufferUseCase
    )




    private val terminalViewModel = TerminalViewModel(
        escapeSequenceUseCase,
        terminalUseCase,
        terminalBufferUseCase,
        screenUseCase,
        cursorUseCase
    )


    fun getScreenText(): ArrayList<TerminalArray>{
        return terminalViewModel.getTerminalBuffer()
    }

    fun getCursor(){}

}