package com.hsikkk.delightroom.domain.usecase.media

import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.repository.MediaPlayerRepository

class RequestMediaPlayerActionUseCase(
    private val mediaPlayerRepository: MediaPlayerRepository
) {
    operator fun invoke(action: MediaPlayerAction) =
        mediaPlayerRepository.requestMediaPlayerAction(action)
}
