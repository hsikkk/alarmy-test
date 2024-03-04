@file:OptIn(ExperimentalMaterial3Api::class)

package com.hsikkk.delightroom.player

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsikkk.delightroom.common.util.clickableSingle
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.model.valueobject.RepeatMode
import com.hsikkk.delightroom.player.component.BottomMiniPlayer
import com.hsikkk.delightroom.player.component.PlayerBottomSheetBody
import com.hsikkk.delightroom.player.contract.PlayerBottomSheetScaffoldIntent
import com.hsikkk.delightroom.player.contract.PlayerBottomSheetScaffoldState
import kotlinx.coroutines.launch
import java.net.URI

@Composable
fun PlayerBottomSheetScaffold(
    content: @Composable () -> Unit
) {
    val viewModel: PlayerBottomSheetScaffoldViewModel = hiltViewModel()

    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = true)
    )

    PlayerBottomSheetScaffoldBody(
        uiState = uiState,
        content = content,
        scaffoldState = scaffoldState,
        onClickPlay = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ClickPlay) },
        onClickGoPrev = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ClickPrev) },
        onClickGoNext = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ClickNext) },
        onClickRepeatMode = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ClickRepeatMode) },
        onClickShuffle = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ClickShuffle) },
        onVolumeChanged = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ChangeVolume(it)) },
        onProgressChanged = { viewModel.onIntent(PlayerBottomSheetScaffoldIntent.ChangeProgress(it)) },
    )
}

@Composable
private fun PlayerBottomSheetScaffoldBody(
    uiState: PlayerBottomSheetScaffoldState,
    scaffoldState: BottomSheetScaffoldState,
    onClickPlay: () -> Unit,
    onClickGoPrev: () -> Unit,
    onClickGoNext: () -> Unit,
    onClickRepeatMode: () -> Unit,
    onClickShuffle: () -> Unit,
    onVolumeChanged: (Float) -> Unit,
    onProgressChanged: (Long) -> Unit,
    content: @Composable () -> Unit
) {
    val isExpanded by remember {
        derivedStateOf { scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded }
    }

    var sheetPeekHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val coroutineScope = rememberCoroutineScope()

    if (isExpanded) {
        BackHandler {
            coroutineScope.launch { scaffoldState.bottomSheetState.partialExpand() }
        }
    }

    BottomSheetScaffold(
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = sheetPeekHeight)
            ) {
                content()

                if (isExpanded) {
                    BottomSheetScrim(scaffoldState = scaffoldState)
                }
            }
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            if (uiState.currentTrack != null) {
                Box(
                    modifier = Modifier.onSizeChanged {
                        if (!isExpanded)
                            sheetPeekHeight = with(density) { it.height.toDp() }
                    }.let{
                        if(isExpanded) it.animateContentSize()
                        else it
                    }
                ) {
                    PlayerBottomSheetContent(
                        uiState = uiState,
                        scaffoldState = scaffoldState,
                        expandBottomSheet = {
                            coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                        },
                        onClickPlay = onClickPlay,
                        onClickGoPrev = onClickGoPrev,
                        onClickGoNext = onClickGoNext,
                        onClickRepeatMode = onClickRepeatMode,
                        onClickShuffle = onClickShuffle,
                        onVolumeChanged = onVolumeChanged,
                        onProgressChanged = onProgressChanged,
                    )
                }
            }
        },
        sheetPeekHeight = if (uiState.currentTrack != null) sheetPeekHeight else 0.dp,
        containerColor = Color.White,
        sheetDragHandle = null,
        sheetShape = if (isExpanded) RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ) else RectangleShape,
        sheetSwipeEnabled = isExpanded,
        sheetShadowElevation = 4.dp
    )
}

@Composable
private fun PlayerBottomSheetContent(
    uiState: PlayerBottomSheetScaffoldState,
    scaffoldState: BottomSheetScaffoldState,
    expandBottomSheet: () -> Unit,
    onClickPlay: () -> Unit,
    onClickGoPrev: () -> Unit,
    onClickGoNext: () -> Unit,
    onClickRepeatMode: () -> Unit,
    onClickShuffle: () -> Unit,
    onVolumeChanged: (Float) -> Unit,
    onProgressChanged: (Long) -> Unit,
) {

    val isExpanded by remember {
        derivedStateOf { scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded }
    }

    val isCollapsed by remember {
        derivedStateOf { scaffoldState.bottomSheetState.targetValue == SheetValue.PartiallyExpanded }
    }

    val expandAnimation by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        label = "expandAnim",
    )

    val collapseAnimation by animateFloatAsState(
        targetValue = if (isCollapsed) 1f else 0f,
        label = "collapseAnim",
    )

    if(collapseAnimation != 0f){
        BottomMiniPlayer(
            isInPlaying = uiState.isInPlaying,
            currentTrack = uiState.currentTrack!!,
            currentPosition = uiState.currentPosition,
            onClick = expandBottomSheet,
            onClickPlayButton = onClickPlay,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(collapseAnimation)
        )
    }

    if(expandAnimation != 0f){
        PlayerBottomSheetBody(
            currentTrack = uiState.currentTrack!!,
            isInPlaying = uiState.isInPlaying,
            currentPosition = uiState.currentPosition,
            volume = uiState.volume,
            repeatMode = uiState.repeatMode,
            isShuffleEnabled = uiState.isShuffleEnabled,
            canGoNext = uiState.canGoNext,
            onClickPlay = onClickPlay,
            onClickGoPrev = onClickGoPrev,
            onClickGoNext = onClickGoNext,
            onClickRepeatMode = onClickRepeatMode,
            onClickShuffle = onClickShuffle,
            onVolumeChanged = onVolumeChanged,
            onProgressChanged = onProgressChanged,
            modifier = Modifier
                .alpha(expandAnimation)
        )
    }

}

@Composable
private fun BottomSheetScrim(
    scaffoldState: BottomSheetScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickableSingle(enableRipple = false) {
                coroutineScope.launch { scaffoldState.bottomSheetState.partialExpand() }
            },
    )
}

@Preview(name = "하단 player 및 bottomSheet가 포함된 scaffold")
@Composable
private fun PlayerBottomSheetScaffoldPreview() {
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.expand()
    }

    DelightroomtestTheme {
        PlayerBottomSheetScaffoldBody(
            uiState = PlayerBottomSheetScaffoldState(
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
                isInPlaying = true,
                currentPosition = 14242,
                volume = 0.7f,
                repeatMode = RepeatMode.REPEAT_ONE,
                isShuffleEnabled = true,
                canGoNext = false,
            ),
            scaffoldState = scaffoldState,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
            },
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
