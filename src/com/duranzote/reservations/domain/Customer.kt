package com.duranzote.reservations.domain

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val name: String
)
