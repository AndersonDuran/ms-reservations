package com.duranzote.reservations.application.ports

interface ReservationServicePort {

    suspend fun sayHello(): String

    suspend fun reserveSeat(flightId: String, customerId: String, seatNum: String)

}