package app.peter.mos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.peter.mos.domain.model.seoul.CulturalEvent
import app.peter.mos.domain.usecase.SeoulUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val seoulUseCase: SeoulUseCase) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _events = MutableStateFlow<List<CulturalEvent>>(emptyList())
    val events = _events.asStateFlow()

    private val _loadState = MutableStateFlow("Loading")
    val loadState = _loadState.asStateFlow()

    fun initialize() {
        if (_isReady.value) return

        viewModelScope.launch {
            try {
                val events = seoulUseCase()
                _events.value = events
                _loadState.value = "Complete"
            } catch (e: Exception) {
                _loadState.value = e.localizedMessage ?: "Error"
            } finally {
                _isReady.value = true
            }
        }
    }
}
