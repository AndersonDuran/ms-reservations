package com.duranzote.reservations.infrastructure

import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RedissonReactiveClient

class ReservationRepositoryAdapter(
    private val redissonReactiveClient: RedissonReactiveClient
) : ReservationRepositoryPort {

    override suspend fun find(): String {
        val xu = redissonReactiveClient.getMap<String, String>("biroska")
        xu.put("teste", "1").awaitSingleOrNull()

        return xu.get("teste").awaitSingle()
    }
}