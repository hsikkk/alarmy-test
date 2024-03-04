package com.hsikkk.delightroom.player.contract

import androidx.compose.runtime.Immutable
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.model.valueobject.RepeatMode

@Immutable
internal data class PlayerBottomSheetScaffoldState(
    val currentTrack: Track?,
    val isInPlaying: Boolean,
    val currentPosition: Long,
    val volume: Float,
    val repeatMode: RepeatMode,
    val isShuffleEnabled: Boolean,
) {
    companion object {
        fun initialState(): PlayerBottomSheetScaffoldState = PlayerBottomSheetScaffoldState(
            currentTrack = null,
            isInPlaying = false,
            currentPosition = 0,
            volume = 1f,
            repeatMode = RepeatMode.REPEAT_OFF,
            isShuffleEnabled = false,
        )
    }
}
