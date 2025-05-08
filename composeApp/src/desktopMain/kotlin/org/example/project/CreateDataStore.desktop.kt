package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

val appDataDir by lazy {
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    val folderName = "kmp-datastore-example-delete-me-after"

    val appDataDir = when {
        os.contains("win") -> File(System.getenv("APPDATA"), folderName)
        os.contains("mac") -> File(userHome, "Library/Application Support/$folderName")
        else -> File(userHome, ".local/share/$folderName")
    }

    if (!appDataDir.exists()) {
        appDataDir.mkdirs()
    }

    appDataDir.path
}

@Composable
actual fun rememberDataStore(): DataStore<Preferences> {
    return remember {
        createDataStore(
            producePath = { "$appDataDir/cmp.preferences_pb" }
        )
    }
}
