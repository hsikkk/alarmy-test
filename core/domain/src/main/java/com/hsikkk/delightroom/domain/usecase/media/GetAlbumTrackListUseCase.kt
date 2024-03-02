package com.hsikkk.delightroom.domain.usecase.media

import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.repository.MediaRepository

class GetAlbumTrackListUseCase (
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(albumId: Long) : List<Track> = mediaRepository.getAlbumTrackList(albumId)
}
