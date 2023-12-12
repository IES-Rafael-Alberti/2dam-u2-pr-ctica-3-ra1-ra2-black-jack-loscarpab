package com.ccormor392.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ccormor392.blackjack.cardgames.ui.MuestraCarta
import com.ccormor392.blackjack.cardgames.ui.UnoVsUno
import com.ccormor392.blackjack.cardgames.ui.UnovsunoViewModel
import com.ccormor392.blackjack.ui.theme.BlackjackTheme

class MainActivity : ComponentActivity() {
    private val viewModel:UnovsunoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BlackjackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnoVsUno(viewModel = viewModel)
                }
            }
        }
    }
}
