package com.lebogang.audiofilemanager.DatabaseScheme;

import android.provider.MediaStore;

import androidx.annotation.Nullable;

public abstract class ArtistDB {

    private final String[] artistProjection = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
    };

    private final String[] audioProject = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM
    };

    private final String defaultSortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
    private String sortOrder = null;

    public String[] getArtistProjection() {
        return artistProjection;
    }

    public String[] getAudioProject() {
        return audioProject;
    }

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
}
