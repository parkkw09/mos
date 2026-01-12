package app.peter.mos.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.peter.mos.data.repositories.SeoulRepository
import app.peter.mos.data.source.model.seoul.cultural.CulturalEventInfoData
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    seoulKey: String,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }
    var seoulCulturalEventInfoData: List<CulturalEventInfoData> by remember { 
        mutableStateOf(mutableListOf()) 
    }

    LaunchedEffect(true) {
        scope.launch {
            val seoulCulturalEventInfo = SeoulRepository(key = seoulKey)
                .getCulturalEvent(pageEnd = 100)
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
