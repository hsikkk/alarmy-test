package com.hsikkk.delightroom.domain.repository

import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import kotlinx.coroutines.flow.Flow

interface MediaPlayerRepository {
    fun requestMediaPlayerAction(action: MediaPlayerAction)

    fun observeMediaPlayerStatus(): Flow<MediaPlayerStatus>
}
