package com.lebogang.audiofilemanager.AlbumManagement;

import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

public abstract class AlbumDatabaseScheme {
    private final String[] albumProjection = {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR,
            MediaStore.Audio.Albums.LAST_YEAR
    };

    private final String[] albumProjectionNewApi = {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR,
            MediaStore.Audio.Albums.LAST_YEAR
    };

    protected String[] getAlbumProjection() {
        return albumProjection;
    }

    protected String[] getAlbumProjectionNewApi() {
        return albumProjectionNewApi;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    }

    public abstract void setSortOrder(String order);
}
