package com.hsikkk.delightroom.player.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.player.R

@Composable
internal fun PlayButton(
    modifier: Modifier,
    iconSize: Dp,
    isInPlaying: Boolean,
    onClick:() -> Unit,
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        if (isInPlaying) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "pause",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(iconSize),
            )
        } else {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "play",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}

@Preview
@Composable
private fun PlayButtonPreview(){
    DelightroomtestTheme {
        PlayButton(
            isInPlaying = true,
            iconSize = 40.dp,
            modifier = Modifier.size(60.dp),
            onClick = {}
        )
    }
}
