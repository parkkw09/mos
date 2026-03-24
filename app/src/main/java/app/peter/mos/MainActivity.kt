package app.peter.mos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.peter.mos.ui.MainScreen
import app.peter.mos.ui.theme.MosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        Log.d(MosApplication.TAG, "onCreate()")

        splashScreen.setKeepOnScreenCondition { !viewModel.isReady.value }

        onBackPressedDispatcher.addCallback(this) {
            Log.d(MosApplication.TAG, "onBackPressed()")
            finish()
        }

        viewModel.initialize()

        setContent {
            MosTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { MainScreen(viewModel = viewModel) }
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
