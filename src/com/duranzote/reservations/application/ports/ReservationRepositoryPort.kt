package com.duranzote.reservations.application.ports

import com.duranzote.reservations.domain.ReservedSeat

interface ReservationRepositoryPort {

    suspend fun findByFlightId(flightId: String): Set<ReservedSeat>

    suspend fun findByFlightIdAndSeatNum(flightId: String, seatNum: String): ReservedSeat?

    suspend fun save(flightId: String, reservedSeat: ReservedSeat)
}