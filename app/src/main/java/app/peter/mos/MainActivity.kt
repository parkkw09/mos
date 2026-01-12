package app.peter.mos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.peter.mos.presentation.ui.MainScreen
import app.peter.mos.presentation.ui.theme.MidstOfTheSeoulTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MosApplication.TAG, "onCreate()")

        onBackPressedDispatcher.addCallback(this) {
            Log.d(MosApplication.TAG, "onBackPressed()")
            finish()
        }

        setContent {
            MidstOfTheSeoulTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val key = getString(R.string.seoul_key)
                    MainScreen(seoulKey = key)
                }
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
