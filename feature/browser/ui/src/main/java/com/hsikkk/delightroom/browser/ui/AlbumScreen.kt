package com.hsikkk.delightroom.browser.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hsikkk.delightroom.browser.ui.contract.AlbumIntent
import com.hsikkk.delightroom.browser.ui.contract.AlbumSideEffect
import com.hsikkk.delightroom.browser.ui.contract.AlbumState
import com.hsikkk.delightroom.browser.ui.preview.AlbumScreenPreviewProvider
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun AlbumRoute(
    viewModel: AlbumViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goAlbumDetail: (albumId: Long) -> Unit,
) {

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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlbumScreen(
    uiState: AlbumState,
    goBack: () -> Unit
) {
    BackHandler {
        goBack()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(it),
        ) {

        }
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
        )
    }
}
