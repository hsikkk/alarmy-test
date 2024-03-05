package com.hsikkk.delightroom.player

import androidx.lifecycle.viewModelScope
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.RepeatMode
import com.hsikkk.delightroom.domain.usecase.media.ObserveMediaPlayerStatusUseCase
import com.hsikkk.delightroom.domain.usecase.media.RequestMediaPlayerActionUseCase
import com.hsikkk.delightroom.player.contract.PlayerBottomSheetScaffoldIntent
import com.hsikkk.delightroom.player.contract.PlayerBottomSheetScaffoldState
import com.hsikkk.delightroom.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class PlayerBottomSheetScaffoldViewModel @Inject constructor(
    private val requestMediaPlayerActionUseCase: RequestMediaPlayerActionUseCase,
    private val observeMediaPlayerStatusUseCase: ObserveMediaPlayerStatusUseCase,
) : BaseViewModel<PlayerBottomSheetScaffoldIntent, PlayerBottomSheetScaffoldState, Unit>() {
    override val container: Container<PlayerBottomSheetScaffoldState, Unit> =
        container(PlayerBottomSheetScaffoldState.initialState())

    init {
        intent {
            viewModelScope.launch {
                observeMediaPlayerStatusUseCase().onEach {
                    reduce {
                        PlayerBottomSheetScaffoldState(
                            currentTrack = it.currentTrack,
                            isInPlaying = it.isInPlaying,
                            currentPosition = it.currentPosition,
                            volume = it.volume,
                            repeatMode = it.repeatMode,
                            isShuffleEnabled = it.isShuffleEnabled,
                            canGoNext = it.canGoNext,
                        )
                    }
                }.collect()
            }
        }
    }

    override fun handleIntent(intent: PlayerBottomSheetScaffoldIntent) {
        when (intent) {
            PlayerBottomSheetScaffoldIntent.ClickPlay -> onClickPlay()

            PlayerBottomSheetScaffoldIntent.ClickPrev -> onClickPrev()

            PlayerBottomSheetScaffoldIntent.ClickNext -> onClickNext()

            PlayerBottomSheetScaffoldIntent.ClickRepeatMode -> onClickRepeatMode()

            PlayerBottomSheetScaffoldIntent.ClickShuffle -> onClickShuffle()

            is PlayerBottomSheetScaffoldIntent.ChangeVolume ->
                onChangeVolume(volume = intent.volume)

            is PlayerBottomSheetScaffoldIntent.ChangeProgress ->
                onChangeProgress(position = intent.position)
        }
    }

    private fun onClickPlay() = intent {
        requestMediaPlayerActionUseCase(
            if (state.isInPlaying) MediaPlayerAction.Pause
            else MediaPlayerAction.Play
        )
    }

    private fun onClickPrev() {
        requestMediaPlayerActionUseCase(MediaPlayerAction.GoPrev)
    }

    private fun onClickNext() {
        requestMediaPlayerActionUseCase(MediaPlayerAction.GoNext)
    }

    private fun onClickRepeatMode() = intent {
        val nextRepeatMode = RepeatMode.values().let {
            it[(state.repeatMode.ordinal + 1) % it.size]
        }

        requestMediaPlayerActionUseCase(
            MediaPlayerAction.ChangeRepeatMode(
                repeatMode = nextRepeatMode
            )
        )
    }

    private fun onClickShuffle() = intent {
        requestMediaPlayerActionUseCase(
            MediaPlayerAction.SetShuffleEnabled(
                enabled = !state.isShuffleEnabled
            )
        )
    }

    private fun onChangeVolume(volume: Float) {
        requestMediaPlayerActionUseCase(MediaPlayerAction.ChangeVolume(volume = volume))
    }

    private fun onChangeProgress(position: Long) {
        requestMediaPlayerActionUseCase(MediaPlayerAction.SeekTo(position = position))
    }
}
