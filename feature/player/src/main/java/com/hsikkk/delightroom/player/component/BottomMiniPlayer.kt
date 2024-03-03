package com.hsikkk.delightroom.player.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.hsikkk.delightroom.common.util.clickableSingle
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.player.R
import java.lang.Float.min
import java.net.URI

@Composable
fun BottomMiniPlayer(
    modifier: Modifier,
    isInPlaying: Boolean,
    currentTrack: Track,
    currentPosition: Long,
    onClick: () -> Unit,
    onClickPlayButton: () -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            ProgressBar(
                modifier = Modifier.fillMaxWidth(),
                currentTrack = currentTrack,
                currentPosition = currentPosition,
            )

            Player(
                modifier = Modifier.fillMaxWidth(),
                isInPlaying = isInPlaying,
                currentTrack = currentTrack,
                onClick = onClick,
                onClickPlayButton = onClickPlayButton,
            )
        }
    }
}

@Composable
private fun ProgressBar(
    modifier: Modifier,
    currentTrack: Track,
    currentPosition: Long,
) {
    val progress by derivedStateOf {
        min(currentPosition / currentTrack.duration.toFloat(), 1f)
    }

    Row(
        modifier = modifier
            .height(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(progress)
                .background(MaterialTheme.colorScheme.primary)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1 - progress)
                .background(Color.LightGray)
        )
    }
}

@Composable
private fun Player(
    modifier: Modifier,
    isInPlaying: Boolean,
    currentTrack: Track,
    onClick: () -> Unit,
    onClickPlayButton: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .background(Color.White)
            .clickableSingle { onClick() }
            .padding(8.dp),
    ) {
        IconButton(
            onClick = onClickPlayButton,
            modifier = Modifier.size(60.dp)
        ) {
            val icSize = 40.dp

            if (isInPlaying) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pause),
                    contentDescription = "pause",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(icSize),
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "play",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(icSize),
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = currentTrack.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${currentTrack.artist} - ${currentTrack.albumName}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

        AsyncImage(
            model = currentTrack.albumArtUri.toString(),
            contentDescription = currentTrack.albumName,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview("재생중")
@Composable
private fun BottomMiniPlayerInPlayingPreview() {
    DelightroomtestTheme {
        BottomMiniPlayer(
            modifier = Modifier.fillMaxWidth(),
            isInPlaying = true,
            currentTrack = Track(
                id = 1,
                name = "track1",
                duration = 141414,
                albumId = 1,
                albumName = "album",
                artist = "artist",
                cdTrackNumber = 2,
                albumArtUri = URI.create("test"),
                contentUri = URI.create("test"),
            ),
            currentPosition = 11424,
            onClick = {},
            onClickPlayButton = {},
        )
    }
}
@Preview("정지")
@Composable
private fun BottomMiniPlayerInPausePreview() {
    DelightroomtestTheme {
        BottomMiniPlayer(
            modifier = Modifier.fillMaxWidth(),
            isInPlaying = false,
            currentTrack = Track(
                id = 1,
                name = "track1",
                duration = 141414,
                albumId = 1,
                albumName = "album",
                artist = "artist",
                cdTrackNumber = 2,
                albumArtUri = URI.create("test"),
                contentUri = URI.create("test"),
            ),
            currentPosition = 11424,
            onClick = {},
            onClickPlayButton = {},
        )
    }
}
