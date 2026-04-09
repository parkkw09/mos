package app.peter.mos.data.tool.network

import app.peter.mos.data.tool.preference.Preference
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class GoogleAuthInterceptor @Inject constructor(private val preference: Preference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preference.googleAccessTokenFlow.value

        val newRequest =
                if (!token.isNullOrEmpty()) {
                    chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
                } else {
                    chain.request()
                }

        return chain.proceed(newRequest)
    }
}
