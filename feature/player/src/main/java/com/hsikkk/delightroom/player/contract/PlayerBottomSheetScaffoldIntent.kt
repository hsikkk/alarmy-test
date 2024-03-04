package com.hsikkk.delightroom.player.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

sealed interface PlayerBottomSheetScaffoldIntent {
    @Stable
    object ClickPlay : PlayerBottomSheetScaffoldIntent

    @Stable
    object ClickPrev : PlayerBottomSheetScaffoldIntent

    @Stable
    object ClickNext : PlayerBottomSheetScaffoldIntent

    @Stable
    object ClickRepeatMode : PlayerBottomSheetScaffoldIntent

    @Stable
    object ClickShuffle: PlayerBottomSheetScaffoldIntent

    @Immutable
    data class ChangeVolume(
        val volume: Float,
    ) : PlayerBottomSheetScaffoldIntent

    @Immutable
    data class ChangeProgress(
        val position: Long,
    ) : PlayerBottomSheetScaffoldIntent
}
