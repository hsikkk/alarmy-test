package com.hsikkk.delightroom.domain.model.entity

import java.net.URI

data class Album(
    val id: Long,
    val name: String,
    val artist: String,
    val albumArtUri: URI,
)
