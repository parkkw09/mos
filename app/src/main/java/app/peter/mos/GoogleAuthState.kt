package app.peter.mos

sealed interface GoogleAuthState {
    data object Unauthenticated : GoogleAuthState
    data object Authenticating : GoogleAuthState
    data object Authenticated : GoogleAuthState
    data class Error(val message: String) : GoogleAuthState
}
