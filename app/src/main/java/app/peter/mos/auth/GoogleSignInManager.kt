package app.peter.mos.auth

import android.app.Activity
import android.content.IntentSender
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleSignInManager(private val activity: ComponentActivity) {

    companion object {
        private const val TAG = "GoogleSignIn"
        private const val YOUTUBE_SCOPE = "https://www.googleapis.com/auth/youtube.readonly"
    }

    private var authorizationLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    private var pendingAuthContinuation: ((Result<String>) -> Unit)? = null

    fun register() {
        authorizationLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            Log.d(TAG, "Authorization 결과 수신 — resultCode=${result.resultCode}, data=${result.data}")
            val continuation = pendingAuthContinuation
            pendingAuthContinuation = null

            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val authResult = Identity.getAuthorizationClient(activity)
                            .getAuthorizationResultFromIntent(result.data!!)
                        val token = authResult.accessToken
                        Log.d(TAG, "RESULT_OK — token 있음=${!token.isNullOrEmpty()}")
                        if (!token.isNullOrEmpty()) {
                            continuation?.invoke(Result.success(token))
                        } else {
                            continuation?.invoke(Result.failure(Exception("RESULT_OK이지만 액세스 토큰이 없습니다.")))
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "getAuthorizationResultFromIntent 실패", e)
                        continuation?.invoke(Result.failure(e))
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.w(TAG, "RESULT_CANCELED — 사용자 취소 또는 OAuth 설정 문제")
                    continuation?.invoke(Result.failure(Exception("인증이 취소되었습니다. (Logcat 'GoogleSignIn' 태그 확인)")))
                }
                else -> {
                    Log.w(TAG, "알 수 없는 resultCode=${result.resultCode}")
                    continuation?.invoke(Result.failure(Exception("예상치 못한 인증 결과: ${result.resultCode}")))
                }
            }
        }
    }

    suspend fun signIn(): String = suspendCancellableCoroutine { cont ->
        val authorizationRequest = AuthorizationRequest.builder()
            .setRequestedScopes(listOf(Scope(YOUTUBE_SCOPE)))
            .build()

        Log.d(TAG, "authorize() 호출 — scope=$YOUTUBE_SCOPE")

        Identity.getAuthorizationClient(activity)
            .authorize(authorizationRequest)
            .addOnSuccessListener { authResult ->
                val token = authResult.accessToken
                Log.d(TAG, "authorize 성공 — hasResolution=${authResult.hasResolution()}, token 있음=${!token.isNullOrEmpty()}")
                if (!token.isNullOrEmpty()) {
                    cont.resume(token)
                } else if (authResult.hasResolution()) {
                    val intentSender: IntentSender = authResult.pendingIntent!!.intentSender
                    pendingAuthContinuation = { result ->
                        result.fold(
                            onSuccess = { cont.resume(it) },
                            onFailure = { cont.resumeWithException(it) }
                        )
                    }
                    Log.d(TAG, "hasResolution=true → 인증 UI 실행")
                    authorizationLauncher?.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    ) ?: cont.resumeWithException(
                        Exception("AuthorizationLauncher가 등록되지 않았습니다.")
                    )
                } else {
                    Log.w(TAG, "token 없음 + hasResolution=false")
                    cont.resumeWithException(Exception("액세스 토큰을 받지 못했습니다."))
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "authorize() 실패", e)
                cont.resumeWithException(e)
            }
    }
}
