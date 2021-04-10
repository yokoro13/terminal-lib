package core

abstract class UseCase<out Type, in Params> where Type: Any{
    abstract fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params) {
        run(params)
    }

    class None

    /*
    TODO implement using coroutines

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async(Dispatchers.IO) { run(params) }
        GlobalScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }
     */


}