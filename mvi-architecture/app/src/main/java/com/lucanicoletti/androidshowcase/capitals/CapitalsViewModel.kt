package com.lucanicoletti.androidshowcase.capitals

import androidx.lifecycle.viewModelScope
import com.lucanicoletti.androidshowcase.base.BaseViewModel
import com.lucanicoletti.domain.usecase.GetCapitalsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapitalsViewModel @Inject constructor(
    private val getCapitalsListUseCase: GetCapitalsListUseCase
) : BaseViewModel<CapitalsViewState, CapitalsIntention>(CapitalsViewState.Loading) {
    override fun handleIntention(intention: CapitalsIntention) {
        when (intention) {
            CapitalsIntention.ScreenStarted -> getCapitals()
        }
    }

    private fun getCapitals() {
        viewModelScope.launch {
            val capitals = getCapitalsListUseCase()
            _viewState.emit(CapitalsViewState.CapitalsList(capitals))
        }
    }
}