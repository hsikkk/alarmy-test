package com.hsikkk.delightroom.browser.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

@Composable
internal fun EmptyAlbumListPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "재생할 수 있는\n앨범이 없어요",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "album 없는 경우 placeholder")
@Composable
private fun EmptyAlbumListPlaceholderPreview(

) {
    DelightroomtestTheme {
        EmptyAlbumListPlaceholder(
            modifier = Modifier.fillMaxSize()
        )
    }
}
