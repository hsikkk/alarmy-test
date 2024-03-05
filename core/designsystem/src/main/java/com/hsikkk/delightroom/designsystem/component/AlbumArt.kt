package com.hsikkk.delightroom.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hsikkk.delightroom.designsystem.R
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

@Composable
fun AlbumArt(
    modifier: Modifier = Modifier,
    uri: String,
    description: String = ""
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = R.drawable.img_album_art),
            contentDescription = null,
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = uri,
            contentDescription = description,
            modifier = modifier,
            placeholder = painterResource(id = R.drawable.img_album_art)
        )
    }

}

@Preview(name = "albumArt 이미지")
@Composable
private fun AlbumArtPreview(

) {
    DelightroomtestTheme {
        AlbumArt(
            modifier = Modifier.size(60.dp),
            uri = "",
        )
    }
}
