package app.peter.mos.data.tool.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val Context.dataStore by preferencesDataStore(name = "mos_preferences")

@Singleton
class Preference @Inject constructor(@param:ApplicationContext private val context: Context) {
    companion object {
        private val KEY_GOOGLE_ACCESS_TOKEN = stringPreferencesKey("google_access_token")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val googleAccessTokenFlow: StateFlow<String?> = context.dataStore
            .data
            .map { preferences -> preferences[KEY_GOOGLE_ACCESS_TOKEN] }
            .stateIn(scope = scope, started = SharingStarted.Eagerly, initialValue = null)

    suspend fun saveGoogleAccessToken(token: String) {
        context.dataStore.edit { preferences -> preferences[KEY_GOOGLE_ACCESS_TOKEN] = token }
    }

    suspend fun clearGoogleAccessToken() {
        context.dataStore.edit { preferences -> preferences.remove(KEY_GOOGLE_ACCESS_TOKEN) }
    }
}
