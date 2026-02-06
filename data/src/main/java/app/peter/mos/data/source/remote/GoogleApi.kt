package app.peter.mos.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface GoogleApi {
    @GET("/") suspend fun test(): Response<Unit>
}
