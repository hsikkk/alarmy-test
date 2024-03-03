package com.hsikkk.delightroom.datasource.media

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.hsikkk.delightroom.data.datasource.LocalMediaDataSource
import com.hsikkk.delightroom.domain.model.entity.Album
import com.hsikkk.delightroom.domain.model.entity.Track
import com.hsikkk.delightroom.domain.model.exception.NoPermissionException
import java.net.URI

class LocalMediaDataSourceImpl(
    private val context: Context
) : LocalMediaDataSource {
    override fun getAlbums(): List<Album> {
        checkPermission()

        val items = mutableListOf<Album>()

        val projection = arrayOf(
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ARTIST,
        )
        val selection = ""
        val selectionArgs = arrayOf<String>()
        val sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC"

        val collection = getMediaRootUri()

        context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            val albumArtistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)

            while (cursor.moveToNext()) {
                val albumId = cursor.getLong(albumIdColumn)

                items.add(
                    Album(
                        id = albumId,
                        name = cursor.getString(albumColumn),
                        artist = cursor.getString(albumArtistColumn),
                        albumArtUri = URI(
                            getAlbumArtUri(albumId).toString()
                        ),
                    )
                )
            }
        }

        return items.distinctBy{ it.id }
    }

    override fun getAlbumTrackList(albumId: Long): List<Track> {
        checkPermission()

        val items = mutableListOf<Track>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_ARTIST,
            MediaStore.Audio.Media.NUM_TRACKS,
        )
        val selection = "${MediaStore.Audio.Media.ALBUM_ID} == ?"
        val selectionArgs = arrayOf(
            albumId.toString()
        )
        val sortOrder = "${MediaStore.Audio.Media.NUM_TRACKS} ASC"

        val collection = getMediaRootUri()

        context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val albumArtistColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST)
            val numTracksColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.NUM_TRACKS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val albumId = cursor.getLong(albumIdColumn)

                items.add(
                    Track(
                        id = id,
                        name = cursor.getString(nameColumn),
                        duration = cursor.getInt(durationColumn),
                        albumId = cursor.getLong(albumIdColumn),
                        albumName = cursor.getString(albumColumn),
                        artist = cursor.getString(albumArtistColumn),
                        numTracks = cursor.getInt(numTracksColumn),
                        albumArtUri = URI(getAlbumArtUri(albumId).toString()),
                        contentUri = URI(
                            ContentUris.withAppendedId(
                                collection,
                                id,
                            ).toString()
                        ),
                    )
                )
            }
        }

        return items
    }

    private fun getMediaRootUri() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    private fun getAlbumArtUri(albumId: Long) = ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"),
        albumId
    )

    private fun checkPermission() {
        if (!hasPermission()) {
            throw NoPermissionException()
        }
    }

    private fun hasPermission() =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
}
