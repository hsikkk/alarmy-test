package com.hsikkk.delightroom.browser.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsikkk.delightroom.browser.ui.R
import com.hsikkk.delightroom.designsystem.component.AlbumArt
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme

@Composable
internal fun AlbumInfo(
    modifier: Modifier = Modifier,
    albumName: String,
    albumArtUri: String,
    artist: String,
    onClickPlay: () -> Unit,
    onClickPlayRandom: () -> Unit,
) {
    Box(
        modifier = modifier.shadow(1.dp, clip = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp),
        ) {
            AlbumInfoSection(
                albumName = albumName,
                albumArtUri = albumArtUri,
                artist = artist,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlayButtonSection(
                onClickPlay = onClickPlay,
                onClickPlayRandom = onClickPlayRandom,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun AlbumInfoSection(
    modifier: Modifier = Modifier,
    albumName: String,
    albumArtUri: String,
    artist: String,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        AlbumArt(
            uri = albumArtUri,
            description = albumName,
            modifier = Modifier.size(80.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = albumName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = artist,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )
        }
    }
}

@Composable
private fun PlayButtonSection(
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit,
    onClickPlayRandom: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onClickPlay,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "play",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }

        Button(
            onClick = onClickPlayRandom,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shuffle),
                contentDescription = "shuffle",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(name = "album 정보")
@Composable
private fun AlbumInfoPreview(

) {
    DelightroomtestTheme {
        AlbumInfo(
            modifier = Modifier.fillMaxWidth(),
            albumName = "album",
            albumArtUri = "test",
            artist = "artist",
            onClickPlay = {},
            onClickPlayRandom = {},
        )
    }
}
