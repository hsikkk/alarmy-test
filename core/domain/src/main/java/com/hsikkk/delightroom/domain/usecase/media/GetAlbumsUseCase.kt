package com.hsikkk.delightroom.domain.usecase.media

import com.hsikkk.delightroom.domain.model.entity.Album
import com.hsikkk.delightroom.domain.repository.MediaRepository

class GetAlbumsUseCase(
    private val mediaRepository: MediaRepository
) {
    fun invoke() : List<Album> = mediaRepository.getAlbums()
}
