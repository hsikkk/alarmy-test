package com.hsikkk.delightroom.browser.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.hsikkk.delightroom.browser.ui.component.AlbumItem
import com.hsikkk.delightroom.browser.ui.component.EmptyAlbumListPlaceholder
import com.hsikkk.delightroom.browser.ui.component.LoadingPlaceholder
import com.hsikkk.delightroom.browser.ui.component.NoPermissionPlaceholder
import com.hsikkk.delightroom.browser.ui.contract.AlbumIntent
import com.hsikkk.delightroom.browser.ui.contract.AlbumSideEffect
import com.hsikkk.delightroom.browser.ui.contract.AlbumState
import com.hsikkk.delightroom.browser.ui.preview.AlbumScreenPreviewProvider
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Album
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AlbumRoute(
    goBack: () -> Unit,
    goAlbumDetail: (albumId: Long) -> Unit,
) {
    val viewModel: AlbumViewModel = hiltViewModel()

    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    var isFirstView by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (isFirstView) {
            viewModel.onIntent(AlbumIntent.Initialize)
            isFirstView = false
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            AlbumSideEffect.GoBack -> goBack()
            is AlbumSideEffect.GoAlbumDetail -> goAlbumDetail(it.albumId)
        }
    }

    AlbumScreen(
        uiState = uiState,
        goBack = { viewModel.onIntent(AlbumIntent.GoBack) },
        onClickItem = { albumId -> viewModel.onIntent(AlbumIntent.GoAlbumDetail(albumId = albumId)) },
        onPermissionGranted = { viewModel.onIntent(AlbumIntent.Initialize) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumScreen(
    uiState: AlbumState,
    goBack: () -> Unit,
    onClickItem: (albumId: Long) -> Unit,
    onPermissionGranted: () -> Unit,
) {
    BackHandler {
        goBack()
    }

    Scaffold(
        topBar = { AppBar() }
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it),
        ) {
            when (uiState) {
                AlbumState.Loading -> LoadingPlaceholder(modifier = Modifier.fillMaxSize())

                AlbumState.NoPermission -> NoPermissionPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    onPermissionGranted = { onPermissionGranted() },
                )

                is AlbumState.FetchSuccess -> AlbumList(
                    modifier = Modifier.fillMaxSize(),
                    albums = uiState.albums,
                    onClickItem = onClickItem,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "라이브러리", color = Color.White)
        },
        colors =  TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF4287f5),
        )
    )
}

@Composable
private fun AlbumList(
    modifier: Modifier,
    albums: ImmutableList<Album>,
    onClickItem: (albumId: Long) -> Unit,
) {
    if (albums.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = modifier.background(Color(0xFFEEEEEE)),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
        ) {
            items(albums) { album ->
                AlbumItem(
                    album = album,
                    modifier = Modifier.fillMaxWidth(),
                    onClickItem = onClickItem,
                )
            }
        }
    } else {
        EmptyAlbumListPlaceholder(modifier = modifier)
    }
}

@Preview(name = "album 뷰")
@Composable
private fun AlbumPreview(
    @PreviewParameter(AlbumScreenPreviewProvider::class) uiState: AlbumState,
) {
    DelightroomtestTheme {
        AlbumScreen(
            uiState = uiState,
            goBack = {},
            onClickItem = {},
            onPermissionGranted = {},
        )
    }
}
