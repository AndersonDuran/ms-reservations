package com.duranzote.reservations.infrastructure

import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import com.duranzote.reservations.domain.ReservedSeat
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RMapReactive
import org.redisson.api.RedissonReactiveClient

class ReservationRepositoryAdapter(
    private val redissonReactiveClient: RedissonReactiveClient
) : ReservationRepositoryPort {

    override suspend fun findByFlightId(flightId: String): Set<ReservedSeat> {
        return getFlightMap(flightId)
            .readAllEntrySet()
            .awaitSingleOrNull()
            .map {
                ReservedSeat(it.key, it.value)
            }.toSet()
    }

    override suspend fun findByFlightIdAndSeatNum(flightId: String, seatNum: String): ReservedSeat? {
        return getFlightMap(flightId)
            .get(seatNum)
            .awaitSingleOrNull()?.let { customerId ->
                ReservedSeat(seatNum, customerId)
            }
    }

    override suspend fun save(flightId: String, reservedSeat: ReservedSeat) {
        getFlightMap(flightId)
            .put(reservedSeat.seatNum, reservedSeat.customerId)
            .awaitSingleOrNull()
    }

    private fun getFlightMap(flightId: String): RMapReactive<String, String> {
        return redissonReactiveClient.getMap(flightId)
    }

}