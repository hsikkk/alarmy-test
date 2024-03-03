@file:OptIn(ExperimentalMaterial3Api::class)

package com.hsikkk.delightroom.browser.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsikkk.delightroom.browser.ui.component.AlbumInfo
import com.hsikkk.delightroom.browser.ui.component.LoadingPlaceholder
import com.hsikkk.delightroom.browser.ui.component.NoPermissionPlaceholder
import com.hsikkk.delightroom.browser.ui.component.TrackItemList
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListIntent
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListSideEffect
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListState
import com.hsikkk.delightroom.browser.ui.preview.AlbumTrackListScreenPreviewProvider
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AlbumTrackListRoute(
    goBack: () -> Unit
) {
    val viewModel: AlbumTrackListViewModel = hiltViewModel()

    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    var isFirstView by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (isFirstView) {
            viewModel.onIntent(AlbumTrackListIntent.Initialize)
            isFirstView = false
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            AlbumTrackListSideEffect.GoBack -> goBack()
        }
    }

    AlbumTrackListScreen(
        uiState = uiState,
        goBack = { viewModel.onIntent(AlbumTrackListIntent.GoBack) },
        onPermissionGranted = { viewModel.onIntent(AlbumTrackListIntent.Initialize) },
        onClickPlay = { viewModel.onIntent(AlbumTrackListIntent.ClickPlayAlbumButton) },
        onClickPlayRandom = { viewModel.onIntent(AlbumTrackListIntent.ClickPlayAlbumRandomButton) },
        onClickItem = { viewModel.onIntent(AlbumTrackListIntent.ClickPlayTrack(trackId = it)) },
    )
}

@Composable
internal fun AlbumTrackListScreen(
    uiState: AlbumTrackListState,
    goBack: () -> Unit,
    onPermissionGranted: () -> Unit,
    onClickPlay: () -> Unit,
    onClickPlayRandom: () -> Unit,
    onClickItem: (albumId: Long) -> Unit,
) {
    BackHandler {
        goBack()
    }

    Scaffold(
        topBar = { AppBar(goBack = goBack) }
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it),
        ) {
            when (uiState) {
                AlbumTrackListState.Loading -> LoadingPlaceholder(modifier = Modifier.fillMaxSize())

                AlbumTrackListState.NoPermission -> NoPermissionPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    onPermissionGranted = { onPermissionGranted() },
                )

                is AlbumTrackListState.FetchSuccess -> AlbumTrackList(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onClickPlay = onClickPlay,
                    onClickPlayRandom = onClickPlayRandom,
                    onClickItem = onClickItem,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    goBack: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
        },
        title = {
            Text(text = "뒤로", color = Color.White)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF4287f5),
        )
    )
}

@Composable
private fun AlbumTrackList(
    modifier: Modifier,
    uiState: AlbumTrackListState.FetchSuccess,
    onClickPlay: () -> Unit,
    onClickPlayRandom: () -> Unit,
    onClickItem: (albumId: Long) -> Unit,
) {
    Column(
        modifier = modifier
            .background(Color(0xFFEEEEEE))
    ) {
        AlbumInfo(
            albumName = uiState.albumName,
            albumArtUri = uiState.albumArtUri,
            artist = uiState.artist,
            onClickPlay = onClickPlay,
            onClickPlayRandom = onClickPlayRandom,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            TrackItemList(
                tracks = uiState.tracks,
                onClickItem = onClickItem,
            )
        }
    }
}

@Preview(name = "album의 track 목록 화면")
@Composable
private fun AlbumTrackListPreview(
    @PreviewParameter(AlbumTrackListScreenPreviewProvider::class) uiState: AlbumTrackListState,
) {
    DelightroomtestTheme {
        AlbumTrackListScreen(
            uiState = uiState,
            goBack = {},
            onPermissionGranted = {},
            onClickPlay = {},
            onClickPlayRandom = {},
            onClickItem = {},
        )
    }
}
