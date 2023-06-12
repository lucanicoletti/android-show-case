package com.lucanicoletti.data.entity

import com.google.gson.annotations.SerializedName

data class CardEntity(
    @SerializedName("value") val value: String,
    @SerializedName("suit") val suit: String,
)