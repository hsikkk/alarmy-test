package com.hsikkk.delightroom.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.player.PlayerBottomSheetScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(Color(0f,0f,0f).toArgb())
        )
        setContent {
            DelightroomtestTheme {
                Box(modifier = Modifier.navigationBarsPadding()){
                    PlayerBottomSheetScaffold() {
                        AppNavHost()
                    }
                }
            }
        }
    }
}
