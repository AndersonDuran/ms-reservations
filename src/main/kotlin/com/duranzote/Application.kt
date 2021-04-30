package com.duranzote

import com.duranzote.reservations.application.ReservationService
import com.duranzote.reservations.application.ports.ReservationRepositoryPort
import com.duranzote.reservations.application.ports.ReservationServicePort
import com.duranzote.reservations.infrastructure.ReservationRepositoryAdapter
import com.duranzote.reservations.userinterface.reservationRoutes
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.gson.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.redisson.Redisson
import org.redisson.api.RedissonReactiveClient
import org.redisson.config.Config

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson()
    }

    install(Koin) {
        modules(koinModule)
    }

    reservationRoutes()
}


val koinModule = module {

    fun redissonProvider(): RedissonReactiveClient {
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        val redisHost = appConfig.property("ktor.redis.host").getString()
        val redisPassword = appConfig.property("ktor.redis.password").getString()

        val config = Config()
        config.useSingleServer().setAddress(redisHost).password = redisPassword
        return Redisson.create(config).reactive()
    }

    single<ReservationServicePort> { ReservationService(get()) }
    single<ReservationRepositoryPort> { ReservationRepositoryAdapter(get()) }
    single { redissonProvider() }
}

