package app.beelabs.coconut.mvvm.base.helper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

abstract class BaseUseCase<T> {

    /**
     * Trigger for the action which can be done in this request
     */
    private val _trigger = MutableStateFlow(true)

    /**
     * Exposes result of this use case
     */
    val resultFlow: Flow<T> = _trigger.flatMapLatest {
        performAction()
    }

    protected abstract suspend fun performAction(): Flow<T>

    /**
     * Triggers the execution of this use case
     */
    suspend fun launch() {
        _trigger.emit(!(_trigger.value))
    }
}