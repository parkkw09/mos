package app.peter.mos.data.tool.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Network {

    private const val TAG = "MOS"

    fun getClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                logger =
                        object : Logger {
                            override fun log(message: String) {
                                Log.d(TAG, message)
                            }
                        }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                            encodeDefaults = true
                            prettyPrint = true
                        }
                )
            }
        }
    }
}
