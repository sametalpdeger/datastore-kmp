package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.project.theme.ColorTheme
import org.example.project.theme.rememberColorTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val dataStore = rememberDataStore()
        val colorTheme = rememberColorTheme(dataStore)
        val theme by colorTheme.getTheme().collectAsStateWithLifecycle(Color.White)
        val scope = rememberCoroutineScope()

        Column(
            Modifier
                .fillMaxSize()
                .background(theme),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                scope.launch {
                    colorTheme.setTheme(if (colorTheme.currentTheme == ColorTheme.Theme.Light) ColorTheme.Theme.Dark else ColorTheme.Theme.Light)
                }
            }) {
                Text("Click me!")
            }
        }
    }
}