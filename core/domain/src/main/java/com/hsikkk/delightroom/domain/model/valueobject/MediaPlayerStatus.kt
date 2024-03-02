package com.hsikkk.delightroom.domain.model.valueobject

import com.hsikkk.delightroom.domain.model.entity.Track

data class MediaPlayerStatus(
    val isInPlaying: Boolean,

    val currentTrackIndex: Int,
    val playList: List<Track>,

    val currentPosition: Long,

    val volume: Float,
){
    val currentTrack: Track?
        get() = playList.getOrNull(currentTrackIndex)
    val duration: Int?
        get() = currentTrack?.duration
}
