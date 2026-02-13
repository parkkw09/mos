package app.peter.mos.data.tool.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "mos_preferences")

@Singleton
class Preference @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val KEY_GOOGLE_ACCESS_TOKEN = stringPreferencesKey("google_access_token")
    }

    suspend fun saveGoogleAccessToken(token: String) {
        context.dataStore.edit { preferences -> preferences[KEY_GOOGLE_ACCESS_TOKEN] = token }
    }

    suspend fun getGoogleAccessToken(): String? {
        return context.dataStore
                .data
                .map { preferences -> preferences[KEY_GOOGLE_ACCESS_TOKEN] }
                .first()
    }

    suspend fun clearGoogleAccessToken() {
        context.dataStore.edit { preferences -> preferences.remove(KEY_GOOGLE_ACCESS_TOKEN) }
    }
}
