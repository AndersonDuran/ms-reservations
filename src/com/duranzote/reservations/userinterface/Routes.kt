package com.duranzote.reservations.userinterface

import com.duranzote.reservations.application.ports.ReservationServicePort
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.reservationRoutes() {
    val reservationServicePort: ReservationServicePort by inject()

    routing {
        reservationsByFlightId(reservationServicePort)
    }
}

fun Route.reservationsByFlightId(reservationServicePort: ReservationServicePort) {
    get("/reservations/{flightId}") {
        call.respondText(reservationServicePort.sayHello())
    }
}

fun Route.addReservation(reservationServicePort: ReservationServicePort) {
    put("/reservations/") {
        val input = call.receive<AddReservationRequest>()
        reservationServicePort.reserveSeat(input.flightId, input.customerId, input.seatNum)
    }
}

data class AddReservationRequest(
    val flightId: String,
    val customerId: String,
    val seatNum: String
)