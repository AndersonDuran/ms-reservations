package com.duranzote.reservations.application

import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import com.duranzote.reservations.application.ports.ReservationServicePort

class ReservationService(
    private val reservationRepositoryPort: ReservationRepositoryPort
) : ReservationServicePort {

    override suspend fun sayHello(): String {
        return reservationRepositoryPort.find()
    }

    override suspend fun reserveSeat(flightId: String, customerId: String, seatNum: String) {
        TODO("Not yet implemented")
    }
}