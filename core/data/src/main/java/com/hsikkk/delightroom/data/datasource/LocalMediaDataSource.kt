package com.hsikkk.delightroom.data.datasource

import com.hsikkk.delightroom.domain.model.entity.Album
import com.hsikkk.delightroom.domain.model.entity.Track

interface LocalMediaDataSource {
    fun getAlbums(): List<Album>

    fun getAlbumTrackList(albumId: Long): List<Track>
}
