package com.hsikkk.delightroom.browser.ui

import androidx.lifecycle.SavedStateHandle
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListIntent
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListSideEffect
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListState
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumTrackListUseCase
import com.hsikkk.delightroom.domain.usecase.media.RequestMediaPlayerActionUseCase
import com.hsikkk.delightroom.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

const val KEY_ALBUM_ID = "albumId"

@HiltViewModel
internal class AlbumTrackListViewModel @Inject constructor(
    private val getAlbumTrackListUseCase: GetAlbumTrackListUseCase,
    private val requestMediaPlayerActionUseCase: RequestMediaPlayerActionUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AlbumTrackListIntent, AlbumTrackListState, AlbumTrackListSideEffect>(){

    override val container: Container<AlbumTrackListState, AlbumTrackListSideEffect> =
        container(AlbumTrackListState.initialState())

    private val albumId = savedStateHandle.get<Long>(KEY_ALBUM_ID)!!

    override fun handleIntent(intent: AlbumTrackListIntent) {
        when(intent){
            AlbumTrackListIntent.Initialize -> initialize()
            AlbumTrackListIntent.GoBack -> onGoBack()
            AlbumTrackListIntent.ClickPlayAlbumButton -> onClickPlayAlbumButton()
            AlbumTrackListIntent.ClickPlayAlbumRandomButton -> onClickPlayAlbumRandomButton()
            is AlbumTrackListIntent.ClickPlayTrack -> onClickPlayTrack(intent.trackId)
        }
    }

    private fun initialize() = intent {
        // Track 이 비어있는 경우에 대한 처리는 생략
        getAlbumTrackListUseCase(albumId).let {
            reduce {
                AlbumTrackListState.FetchSuccess(
                    albumName = it[0].albumName,
                    artist = it[0].artist,
                    albumArtUri = it[0].albumArtUri.toString(),
                    tracks = it.toImmutableList(),
                )
            }
        }
    }
    private fun onGoBack() = intent {
        postSideEffect(AlbumTrackListSideEffect.GoBack)
    }

    private fun onClickPlayAlbumButton() = intent {
        getTracks()?.let{
            requestMediaPlayerActionUseCase(
                MediaPlayerAction.SetItemsToPlaylist(
                    items = it,
                    shuffleRandom = false,
                    startIndex = 0,
                )
            )

            prepareAndPlay()
        }
    }

    private fun onClickPlayAlbumRandomButton() = intent {
        getTracks()?.let{
            requestMediaPlayerActionUseCase(
                MediaPlayerAction.SetItemsToPlaylist(
                    items = it,
                    shuffleRandom = true,
                    startIndex = 0,
                )
            )

            prepareAndPlay()
        }
    }

    private fun onClickPlayTrack(trackId: Long) = intent {
        getTracks()?.let{
            requestMediaPlayerActionUseCase(
                MediaPlayerAction.SetItemsToPlaylist(
                    items = it,
                    shuffleRandom = false,
                    startIndex = it.indexOfFirst { item -> item.id == trackId },
                )
            )

            prepareAndPlay()
        }
    }

    private fun getTracks() = (container.stateFlow.value).let {
        if(it !is AlbumTrackListState) null
        else (it as AlbumTrackListState.FetchSuccess).tracks
    }

    private fun prepareAndPlay(){
        requestMediaPlayerActionUseCase(MediaPlayerAction.Prepare)
        requestMediaPlayerActionUseCase(MediaPlayerAction.Play)
    }
}
