package com.duranzote.reservations.application

import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import com.duranzote.reservations.application.ports.ReservationServicePort
import com.duranzote.reservations.application.ports.ReserveSeatInput
import com.duranzote.reservations.application.ports.Result
import com.duranzote.reservations.application.ports.Result.*
import com.duranzote.reservations.domain.ReservedSeat

class ReservationService(
    private val reservationRepositoryPort: ReservationRepositoryPort
) : ReservationServicePort {

    override suspend fun getReservedSeats(flightId: String): Set<ReservedSeat> {
        return reservationRepositoryPort.findByFlightId(flightId)
    }

    override suspend fun reserveSeat(input: ReserveSeatInput): Result<Unit> {
        return with(reservationRepositoryPort) {
            findByFlightIdAndSeatNum(input.flightId, input.seatNum)?.let {
                Failure("Este lugar já está reservado")
            } ?: kotlin.run {
                val reservedSeat = ReservedSeat(input.seatNum, input.customerId)
                reservationRepositoryPort.save(input.flightId, reservedSeat)
                Success(Unit)
            }
        }
    }
}