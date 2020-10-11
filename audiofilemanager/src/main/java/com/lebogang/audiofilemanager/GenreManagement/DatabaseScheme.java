package com.lebogang.audiofilemanager.GenreManagement;

import android.net.Uri;
import android.provider.MediaStore;

abstract class DatabaseScheme {

    private final String[] genreProjection = {
            MediaStore.Audio.Genres._ID,
            MediaStore.Audio.Genres.NAME
    };

    public String[] getGenreProjection() {
        return genreProjection;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    }

    public abstract void setSortOrder(String order);
}
