package org.example.project.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

val lightColor = Color(0xffffffff)
val darkColor = Color(0xff000000)

class ColorTheme(
    private val dataStore: DataStore<Preferences>
) {
    enum class Theme {
        Light, Dark
    }
    var currentTheme: Theme = Theme.Dark

    fun getTheme(): Flow<Color> {
        return dataStore.data.map {
            val theme = it[stringPreferencesKey("theme")] ?: Theme.Dark.name
            if (theme == Theme.Light.name) {
                currentTheme = Theme.Light
                lightColor
            } else {
                currentTheme = Theme.Dark
                darkColor
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun setTheme(theme: Theme) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[stringPreferencesKey("theme")] = theme.name
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun rememberColorTheme(
    dataStore: DataStore<Preferences>
): ColorTheme = remember {
    ColorTheme(dataStore)
}
