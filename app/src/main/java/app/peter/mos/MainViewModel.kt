package app.peter.mos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.peter.mos.domain.model.seoul.CulturalEvent
import app.peter.mos.domain.usecase.ClearGoogleTokenUseCase
import app.peter.mos.domain.usecase.SaveGoogleTokenUseCase
import app.peter.mos.domain.usecase.SeoulUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val seoulUseCase: SeoulUseCase,
    private val saveGoogleTokenUseCase: SaveGoogleTokenUseCase,
    private val clearGoogleTokenUseCase: ClearGoogleTokenUseCase
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 50
    }

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _events = MutableStateFlow<List<CulturalEvent>>(emptyList())
    val events = _events.asStateFlow()

    private val _loadState = MutableStateFlow<LoadState>(LoadState.Idle)
    val loadState = _loadState.asStateFlow()

    private val _googleAuthState = MutableStateFlow<GoogleAuthState>(GoogleAuthState.Unauthenticated)
    val googleAuthState = _googleAuthState.asStateFlow()

    private var totalCount = 0
    private var nextStart = 1
    private var isLoading = false

    fun initialize() {
        if (_events.value.isNotEmpty() || isLoading) return
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || (totalCount > 0 && nextStart > totalCount)) return

        isLoading = true
        _loadState.value = if (nextStart == 1) LoadState.Loading else LoadState.LoadingMore

        viewModelScope.launch {
            try {
                val page = seoulUseCase(nextStart, nextStart + PAGE_SIZE - 1)
                totalCount = page.totalCount
                _events.value = _events.value + page.events
                nextStart += PAGE_SIZE
                _loadState.value = LoadState.Success
            } catch (e: Exception) {
                _loadState.value = LoadState.Error(e.localizedMessage ?: "Error")
            } finally {
                isLoading = false
                _isReady.value = true
            }
        }
    }

    fun refresh() {
        _events.value = emptyList()
        totalCount = 0
        nextStart = 1
        isLoading = false
        loadNextPage()
    }

    fun onGoogleSignInStarted() {
        _googleAuthState.value = GoogleAuthState.Authenticating
    }

    fun onGoogleSignInSuccess(token: String) {
        viewModelScope.launch {
            try {
                saveGoogleTokenUseCase(token)
                _googleAuthState.value = GoogleAuthState.Authenticated
            } catch (e: Exception) {
                _googleAuthState.value =
                    GoogleAuthState.Error(e.localizedMessage ?: "토큰 저장에 실패했습니다.")
            }
        }
    }

    fun onGoogleSignInError(message: String) {
        _googleAuthState.value = GoogleAuthState.Error(message)
    }

    fun signOut() {
        viewModelScope.launch {
            clearGoogleTokenUseCase()
            _googleAuthState.value = GoogleAuthState.Unauthenticated
        }
    }
}
