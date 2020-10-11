package com.lebogang.audiofilemanager.AlbumManagement;

import android.net.Uri;
import android.provider.MediaStore;

public abstract class DatabaseScheme {
    private final String[] albumProjection = {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR,
            MediaStore.Audio.Albums.LAST_YEAR
    };

    protected String[] getAlbumProjection() {
        return albumProjection;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    }

    public abstract void setSortOrder(String order);
}
