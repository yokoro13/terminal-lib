import controller.TerminalViewController
import interactor.TerminalInteractor
import presenter.TerminalPresenter
import usecase.UseCaseOutputPort

class TerminalLib {
    private val controller: TerminalViewController = TerminalViewController()
    private val presenter: UseCaseOutputPort = TerminalPresenter()
    private val interactor: TerminalInteractor = TerminalInteractor()

    init {
        controller.input = interactor
        interactor.output = presenter
    }
}