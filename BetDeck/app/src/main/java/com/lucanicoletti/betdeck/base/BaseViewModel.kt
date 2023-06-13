package com.lucanicoletti.betdeck.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewState, Intention, ViewAction>(initialState: ViewState) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private val _viewActions = Channel<ViewAction>(Channel.BUFFERED)
    val viewActions
        get() = _viewActions.receiveAsFlow()

    private val _intentions = Channel<Intention>(Channel.BUFFERED)
    fun submitIntention(intention: Intention) {
        viewModelScope.launch(Dispatchers.IO) {
            _intentions.send(intention)
        }
    }

    fun updateState(body: ((ViewState) -> ViewState)) {
        viewModelScope.launch() {
            _viewState.emit(body(_viewState.value))
        }
    }

    fun sendViewAction(viewAction: ViewAction) {
        viewModelScope.launch() {
            _viewActions.send(viewAction)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _intentions.consumeAsFlow().collectLatest { intention ->
                intention?.let {
                    handleIntention(it)
                }
            }
        }
    }

    abstract fun handleIntention(intention: Intention)
}