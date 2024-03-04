package com.hsikkk.delightroom.player.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.model.valueobject.RepeatMode
import com.hsikkk.delightroom.player.R
import java.net.URI
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun PlayerBottomSheetBody(
    modifier: Modifier = Modifier,
    currentTrack: Track,
    isInPlaying: Boolean,
    currentPosition: Long,
    volume: Float,
    repeatMode: RepeatMode,
    isShuffleEnabled: Boolean,
    onClickPlay: () -> Unit,
    onClickGoPrev: () -> Unit,
    onClickGoNext: () -> Unit,
    onClickRepeatMode: () -> Unit,
    onClickShuffle: () -> Unit,
    onVolumeChanged: (Float) -> Unit,
    onProgressChanged: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        AlbumArt(
            uri = currentTrack.albumArtUri.toString(),
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        TrackInfo(
            currentTrack = currentTrack,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Controller(
            modifier = Modifier,
            isInPlaying = isInPlaying,
            repeatMode = repeatMode,
            isShuffleEnabled = isShuffleEnabled,
            onClickPlay = onClickPlay,
            onClickGoPrev = onClickGoPrev,
            onClickGoNext = onClickGoNext,
            onClickRepeatMode = onClickRepeatMode,
            onClickShuffle = onClickShuffle,
        )

        Spacer(modifier = Modifier.height(8.dp))

        VolumeSlider(
            volume = volume,
            onVolumeChanged = onVolumeChanged,
        )

        Spacer(modifier = Modifier.height(4.dp))

        ProgressSlider(
            currentDuration = currentTrack.duration,
            currentPosition = currentPosition,
            onProgressChanged = onProgressChanged,
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun AlbumArt(
    uri: String,
    modifier: Modifier,
) {
    AsyncImage(
        model = uri,
        contentDescription = uri,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
    )
}

@Composable
private fun TrackInfo(
    modifier: Modifier,
    currentTrack: Track
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = currentTrack.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${currentTrack.artist} - ${currentTrack.albumName}",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
private fun Controller(
    modifier: Modifier,
    isInPlaying: Boolean,
    repeatMode: RepeatMode,
    isShuffleEnabled: Boolean,
    onClickPlay: () -> Unit,
    onClickGoPrev: () -> Unit,
    onClickGoNext: () -> Unit,
    onClickRepeatMode: () -> Unit,
    onClickShuffle: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ControllerButton(
            resId = if (repeatMode == RepeatMode.REPEAT_ONE) R.drawable.ic_repeat_one else R.drawable.ic_repeat,
            tint = if (repeatMode == RepeatMode.REPEAT_OFF) Color.LightGray else MaterialTheme.colorScheme.primary,
            onClick = onClickRepeatMode
        )

        ControllerButton(
            resId = R.drawable.ic_prev,
            tint = MaterialTheme.colorScheme.primary,
            onClick = onClickGoPrev
        )

        PlayButton(
            modifier = Modifier.size(48.dp),
            iconSize = 40.dp,
            isInPlaying = isInPlaying,
            onClick = onClickPlay,
        )

        ControllerButton(
            resId = R.drawable.ic_next,
            tint = MaterialTheme.colorScheme.primary,
            onClick = onClickGoNext
        )

        ControllerButton(
            resId = R.drawable.ic_shuffle,
            tint = if (isShuffleEnabled) Color.LightGray else MaterialTheme.colorScheme.primary,
            onClick = onClickShuffle
        )
    }
}

@Composable
private fun ControllerButton(
    modifier: Modifier = Modifier,
    resId: Int,
    tint: Color,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(36.dp)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
private fun VolumeSlider(
    modifier: Modifier = Modifier,
    volume: Float,
    onVolumeChanged: (Float) -> Unit,
) {
    Slider(
        value = volume,
        onValueChange = onVolumeChanged,
        modifier = modifier.padding(horizontal = 40.dp, vertical = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProgressSlider(
    modifier: Modifier = Modifier,
    currentDuration: Long,
    currentPosition: Long,
    onProgressChanged: (Long) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DurationText(duration = currentPosition)
            DurationText(duration = currentDuration)
        }

        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { onProgressChanged(it.toLong()) },
            valueRange = 0f..currentDuration.toFloat(),
            interactionSource = interactionSource,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    thumbSize = DpSize.Zero
                )
            },
        )
    }
}

@Composable
private fun DurationText(
    duration: Long
) {
    duration.toDuration(DurationUnit.MILLISECONDS).let {
        val hours = it.inWholeHours
        val minutes = it.inWholeMinutes % 60
        val seconds = it.inWholeSeconds % 60

        val text = if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }

        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun PlayerBottomSheetBodyPreview() {
    DelightroomtestTheme {
        PlayerBottomSheetBody(
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
            volume = 0.5f,
            repeatMode = RepeatMode.REPEAT_OFF,
            isShuffleEnabled = false,
            onClickPlay = {},
            onClickGoPrev = {},
            onClickGoNext = {},
            onClickRepeatMode = {},
            onClickShuffle = {},
            onVolumeChanged = {},
            onProgressChanged = {},
        )
    }
}

