package com.duranzote.reservations.application.ports

import com.duranzote.reservations.domain.ReservedSeat

interface ReservationServicePort {

    suspend fun getReservedSeats(flightId: String): Set<ReservedSeat>

    suspend fun reserveSeat(input: ReserveSeatInput): Result<Unit>
}

data class ReserveSeatInput(
    val flightId: String,
    val customerId: String,
    val seatNum: String
)

sealed class Result<T> {

    class Success<T>(t: T) : Result<T>()

    class Failure<T>(errorType: String) : Result<T>()
}