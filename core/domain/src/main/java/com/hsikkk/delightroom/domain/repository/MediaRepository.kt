package com.hsikkk.delightroom.domain.repository

import com.hsikkk.delightroom.domain.model.entity.Album
import com.hsikkk.delightroom.domain.model.entity.Track

interface MediaRepository {

    fun getAlbums(): List<Album>

    fun getAlbumTrackList(albumId: Long): List<Track>

}
