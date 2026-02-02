package app.peter.mos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.peter.mos.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val events by viewModel.events.collectAsState()
    val loadState by viewModel.loadState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        Text(text = loadState, modifier = Modifier.padding(16.dp))

        if (loadState == "Loading") {
            Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
            ) { CircularProgressIndicator() }
        } else {
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = events) { eventInfo ->
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Text(text = eventInfo.title)
                        Text(text = "-----------------------------------------")
                    }
                }
            }
        }
    }
}
