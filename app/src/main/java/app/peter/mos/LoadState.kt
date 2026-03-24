package app.peter.mos

sealed interface LoadState {
    data object Idle : LoadState
    data object Loading : LoadState
    data object LoadingMore : LoadState
    data object Success : LoadState
    data class Error(val message: String) : LoadState
}
