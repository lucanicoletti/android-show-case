package com.lucanicoletti.domain.usecase

import com.lucanicoletti.domain.repository.CardsRepository
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val repository: CardsRepository,
) {

    suspend operator fun invoke() = repository.getAllCards()
}