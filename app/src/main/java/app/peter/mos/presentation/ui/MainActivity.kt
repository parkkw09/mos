package app.peter.mos.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.peter.mos.R
import app.peter.mos.application.App
import app.peter.mos.data.repositories.SeoulRepository
import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfoData
import app.peter.mos.presentation.ui.theme.MidstOfTheSeoulTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(App.TAG, "onCreate()")

        onBackPressedDispatcher.addCallback(this) {
            Log.d(App.TAG, "onBackPressed()")
            finish()
        }

        setContent {
            MidstOfTheSeoulTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val key = getString(R.string.seoul_key)
                    Greeting(key)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(App.TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(App.TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(App.TAG, "onPause()")
    }

    override fun onDestroy() {
        Log.d(App.TAG, "onDestroy()")
        super.onDestroy()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }
    var seoulCulturalEventInfoData: List<CulturalEventInfoData> by remember { mutableStateOf(mutableListOf()) }

    LaunchedEffect(true) {
        scope.launch {
            val seoulCulturalEventInfo = SeoulRepository(key = name).getCulturalEvent(pageEnd = 100)
            seoulCulturalEventInfoData = seoulCulturalEventInfo.list

            text = try {
                "Complete"
            } catch (e: Exception) {
                e.localizedMessage ?: "error"
            }
        }
    }

    Column {
        Text(
            text = text,
            modifier = modifier
        )
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = seoulCulturalEventInfoData) { eventInfo ->
                Column {
                    Text(text = eventInfo.title)
                    Text(text = "-----------------------------------------")
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MidstOfTheSeoulTheme {
        Greeting("Android")
    }
}