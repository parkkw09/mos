package app.peter.mos.data.tool.network

import android.util.Log
import app.peter.mos.application.App
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson

object Network {
    fun getClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        Log.d(App.TAG, message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                gson()
            }
        }
    }
}