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
) : BaseViewModel<CapitalsViewState, CapitalsIntention, CapitalsViewAction>(CapitalsViewState.Loading) {
    override fun handleIntention(intention: CapitalsIntention) {
        when (intention) {
            CapitalsIntention.ScreenStarted -> getCapitals()
            is CapitalsIntention.CapitalClicked -> sendViewAction(
                CapitalsViewAction.NavigateToCurrentWeather(intention.capital)
            )

            is CapitalsIntention.Searched -> filterResults(intention.query)
        }
    }

    private fun filterResults(query: String) {
        // ideally, if it was an API call, the usecase would save locally, so following request wouldn't fire API calls
        val filtered = getCapitalsListUseCase().filter { capital ->
            capital.name.contains(query, ignoreCase = true)
        }
        updateState(CapitalsViewState.CapitalsList(filtered))
    }

    private fun getCapitals() {
        val capitals = getCapitalsListUseCase()
        updateState(CapitalsViewState.CapitalsList(capitals))
    }
}