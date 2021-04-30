package com.duranzote.reservations.userinterface

import com.duranzote.reservations.application.ports.ReservationServicePort
import com.duranzote.reservations.application.ports.ReserveSeatInput
import com.duranzote.reservations.application.ports.Result.Failure
import com.duranzote.reservations.application.ports.Result.Success
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.reservationRoutes() {
    val reservationServicePort: ReservationServicePort by inject()

    routing {
        addReservation(reservationServicePort)
        getReservationsByFlightId(reservationServicePort)
    }
}

fun Route.getReservationsByFlightId(reservationServicePort: ReservationServicePort) {
    get("/reservations/{flightId}") {
        val input = call.parameters["flightId"]

        if (input == null) {
            call.respond(HttpStatusCode.BadRequest, Error("1", "Parametro flightId é obrigatório!"))
        } else {
            call.respond(HttpStatusCode.OK, reservationServicePort.getReservedSeats(input))
        }
    }
}

fun Route.addReservation(reservationServicePort: ReservationServicePort) {
    put("/reservations") {
        val input = call.receive<ReserveSeatInput>()
        when (reservationServicePort.reserveSeat(input)) {
            is Success -> call.respond(HttpStatusCode.Accepted)
            is Failure -> call.respond(HttpStatusCode.PreconditionFailed)
        }
    }
}

data class Error(val code: String, val message: String)
