package com.lucanicoletti.data

import com.lucanicoletti.data.entity.CardEntity
import retrofit2.http.GET

interface CardsApi {
    @GET("json") //ideally the path
    suspend fun getAllCards(): List<CardEntity>
}