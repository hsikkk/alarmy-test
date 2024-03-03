package com.hsikkk.delightroom.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DelightroomtestTheme {
                AppNavHost()
            }
        }
    }
}
