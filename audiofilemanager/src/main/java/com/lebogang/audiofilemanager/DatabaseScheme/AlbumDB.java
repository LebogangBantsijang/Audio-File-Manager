package com.lebogang.audiofilemanager.DatabaseScheme;

import android.provider.MediaStore;

import androidx.annotation.Nullable;

public abstract class AlbumDB {
    private final String[] albumProjection = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS
    };

    private final String[] audioProjection = {
            MediaStore.Audio.Media.DURATION
    };

    private final String defaultSortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    private String sortOrder = null;

    public String getSortOrder() {
        if (sortOrder == null)
            return defaultSortOrder;
        return sortOrder;
    }

    protected final void setSortOrder(@Nullable String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Set the sort order when searching for artist items
     *
     * @param defaultSortOrder : MediaStore Fields, e.g. MediaStore.Audio.Artists.ARTIST + " ASC"
     * */
    public abstract void setDefaultSortOrder(String defaultSortOrder);

    public String[] getAlbumProjection() {
        return albumProjection;
    }

    public String[] getAudioProjection() {
        return audioProjection;
    }
}
