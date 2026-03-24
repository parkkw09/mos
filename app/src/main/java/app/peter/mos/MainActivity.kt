package app.peter.mos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.peter.mos.auth.GoogleSignInManager
import app.peter.mos.ui.MainScreen
import app.peter.mos.ui.theme.MosTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var googleSignInManager: GoogleSignInManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        Log.d(MosApplication.TAG, "onCreate()")

        googleSignInManager = GoogleSignInManager(this)
        googleSignInManager.register()

        splashScreen.setKeepOnScreenCondition { !viewModel.isReady.value }

        onBackPressedDispatcher.addCallback(this) {
            Log.d(MosApplication.TAG, "onBackPressed()")
            finish()
        }

        viewModel.initialize()
        startGoogleSignIn()
        observeGoogleAuthState()

        setContent {
            MosTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { MainScreen(viewModel = viewModel) }
            }
        }
    }

    private fun observeGoogleAuthState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.googleAuthState.collect { state ->
                    when (state) {
                        is GoogleAuthState.Authenticated ->
                            Toast.makeText(this@MainActivity, "Google 로그인 성공!", Toast.LENGTH_SHORT).show()
                        is GoogleAuthState.Error ->
                            Toast.makeText(this@MainActivity, "로그인 실패: ${state.message}", Toast.LENGTH_LONG).show()
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun startGoogleSignIn() {
        viewModel.onGoogleSignInStarted()
        lifecycleScope.launch {
            try {
                val token = googleSignInManager.signIn()
                viewModel.onGoogleSignInSuccess(token)
                Log.d(MosApplication.TAG, "Google 로그인 성공")
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Google 로그인 실패"
                viewModel.onGoogleSignInError(message)
                Log.e(MosApplication.TAG, "Google 로그인 실패: $message")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(MosApplication.TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MosApplication.TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(MosApplication.TAG, "onPause()")
    }

    override fun onDestroy() {
        Log.d(MosApplication.TAG, "onDestroy()")
        super.onDestroy()
    }
}
