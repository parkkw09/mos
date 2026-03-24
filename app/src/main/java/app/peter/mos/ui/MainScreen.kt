package app.peter.mos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.peter.mos.LoadState
import app.peter.mos.MainViewModel
import app.peter.mos.domain.model.seoul.CulturalEvent

@Composable
fun MainScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val events by viewModel.events.collectAsState()
    val loadState by viewModel.loadState.collectAsState()

    MainScreenContent(
            events = events,
            loadState = loadState,
            onLoadMore = viewModel::loadNextPage,
            modifier = modifier
    )
}

@Composable
fun MainScreenContent(
        events: List<CulturalEvent>,
        loadState: LoadState,
        onLoadMore: () -> Unit,
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (loadState) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = loadState.message, modifier = Modifier.padding(16.dp))
                }
            }
            else -> {
                val listState = rememberLazyListState()

                val reachedBottom by remember {
                    derivedStateOf {
                        val lastVisible =
                                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                        val total = listState.layoutInfo.totalItemsCount
                        total > 0 && lastVisible >= total - 3
                    }
                }

                LaunchedEffect(reachedBottom) {
                    if (reachedBottom) onLoadMore()
                }

                LazyColumn(state = listState, modifier = Modifier.padding(vertical = 4.dp)) {
                    itemsIndexed(items = events) { index, eventInfo ->
                        Column(
                                modifier =
                                        Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "${index + 1}. ${eventInfo.title}")
                            Text(text = "-----------------------------------------")
                        }
                    }

                    if (loadState is LoadState.LoadingMore) {
                        item {
                            Box(
                                    modifier =
                                            Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                    contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
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
            loadState = LoadState.Success,
            onLoadMore = {},
            events =
                    listOf(
                            CulturalEvent(
                                    codeName = "콘서트",
                                    guName = "중구",
                                    title = "Preview Event 1",
                                    date = "2023-10-01",
                                    place = "Seoul Plaza",
                                    orgName = "Seoul City",
                                    useTarget = "전체",
                                    useFee = "무료",
                                    inquiry = "02-1234-5678",
                                    player = "",
                                    program = "",
                                    etcDesc = "",
                                    orgLink = "",
                                    mainImage = "",
                                    registrationDate = "2023-09-01",
                                    ticket = "시민",
                                    startDate = "2023-10-01",
                                    endDate = "2023-10-01",
                                    themeCode = "문화일반",
                                    lot = "126.9784",
                                    lat = "37.5665",
                                    isFree = "무료",
                                    homepageAddr = "",
                                    proTime = "14:00~16:00"
                            ),
                            CulturalEvent(
                                    codeName = "전시",
                                    guName = "강남구",
                                    title = "Preview Event 2",
                                    date = "2023-10-02",
                                    place = "Gangnam",
                                    orgName = "Gangnam District",
                                    useTarget = "전체",
                                    useFee = "10000",
                                    inquiry = "02-9876-5432",
                                    player = "",
                                    program = "",
                                    etcDesc = "",
                                    orgLink = "",
                                    mainImage = "",
                                    registrationDate = "2023-09-15",
                                    ticket = "기관",
                                    startDate = "2023-10-02",
                                    endDate = "2023-10-15",
                                    themeCode = "전시",
                                    lot = "127.0495",
                                    lat = "37.5145",
                                    isFree = "유료",
                                    homepageAddr = "",
                                    proTime = "10:00~18:00"
                            )
                    ),
    )
}
