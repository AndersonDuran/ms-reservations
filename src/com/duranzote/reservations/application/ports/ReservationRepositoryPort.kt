package com.duranzote.reservations.application.ports

interface ReservationRepositoryPort {

    suspend fun find(): String
}