package com.lucanicoletti.androidshowcase.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewState, Intention>(initialState: ViewState) : ViewModel() {

    internal val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState>
        get() = _viewState

    internal val _intentions: MutableStateFlow<Intention?> = MutableStateFlow(null)
    fun submitIntention(intention: Intention) {
        viewModelScope.launch {
            _intentions.emit(intention)
        }
    }

    init {
        viewModelScope.launch {
            _intentions.collect { intention ->
                intention?.let {
                    handleIntention(it)
                }
            }
        }
    }

    abstract fun handleIntention(intention: Intention)
}