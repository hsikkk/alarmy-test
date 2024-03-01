package com.hsikkk.delightroom.data.repository

import com.hsikkk.delightroom.data.datasource.LocalMediaDataSource
import com.hsikkk.delightroom.domain.model.entity.Album
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.repository.MediaRepository

class MediaRepositoryImpl(
    private val localMediaDataSource: LocalMediaDataSource,
) : MediaRepository {
    override fun getAlbums(): List<Album> = localMediaDataSource.getAlbums()

    override fun getAlbumTrackList(albumId: Long): List<Track> =
        localMediaDataSource.getAlbumTrackList(albumId)
}
