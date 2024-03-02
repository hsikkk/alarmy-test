package com.hsikkk.delightroom.domain.usecase.media

import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import com.hsikkk.delightroom.domain.repository.MediaPlayerRepository
import kotlinx.coroutines.flow.Flow

class ObserveMediaPlayerStatusUseCase(
    private val mediaPlayerRepository: MediaPlayerRepository,
) {
    operator fun invoke(): Flow<MediaPlayerStatus> =
        mediaPlayerRepository.observeMediaPlayerStatus()
}
