package com.hsikkk.delightroom.domain.model.entity

import java.net.URI

data class Track(
    val id: Long,
    val name: String,
    val duration: Int,
    val albumId: Long,
    val albumName: String,
    val artist: String,
    val cdTrackNumber: Int,
    val albumArtUri: URI,
    val contentUri: URI,
)
