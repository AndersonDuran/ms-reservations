package com.duranzote.reservations.infrastructure

import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import org.redisson.api.RedissonReactiveClient

class ReservationRepositoryAdapter(
    private val redissonReactiveClient: RedissonReactiveClient
) : ReservationRepositoryPort {

    override suspend fun find(): String {

//        val xu = redissonReactiveClient.getMap<String, String>("biroska")
//        return xu.put("key", "value").subscribe {
//            it
//        }
        return ""
    }
}