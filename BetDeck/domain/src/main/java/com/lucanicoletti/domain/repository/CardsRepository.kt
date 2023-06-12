package com.lucanicoletti.domain.repository

import com.lucanicoletti.domain.model.CardModel

interface CardsRepository {

    suspend fun getAllCards(): List<CardModel>
}