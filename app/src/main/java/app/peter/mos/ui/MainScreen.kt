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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.peter.mos.MainViewModel
import app.peter.mos.domain.model.seoul.CulturalEvent

@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val events by viewModel.events.collectAsState()
    val loadState by viewModel.loadState.collectAsState()

    MainScreenContent(events = events, loadState = loadState, modifier = modifier)
}

@Composable
fun MainScreenContent(
        events: List<CulturalEvent>,
        loadState: String,
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = loadState, modifier = Modifier.padding(16.dp))

        if (loadState == "Loading") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(
            events =
                    listOf(
                            CulturalEvent(
                                    title = "Preview Event 1",
                                    date = "2023-10-01",
                                    place = "Seoul Plaza",
                                    mainImage = "",
                                    orgName = "Seoul City",
                                    useFee = "Free"
                            ),
                            CulturalEvent(
                                    title = "Preview Event 2",
                                    date = "2023-10-02",
                                    place = "Gangnam",
                                    mainImage = "",
                                    orgName = "Gangnam District",
                                    useFee = "10000"
                            )
                    ),
            loadState = "Complete"
    )
}
