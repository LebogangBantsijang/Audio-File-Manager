package com.lebogang.audiofilemanager.DatabaseScheme;

import android.provider.MediaStore;

import androidx.annotation.Nullable;

public abstract class AudioDB {

    private final String[] audioProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.YEAR
    };

    private final String defaultSortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
    private String sortOrder = null;
    private final String selection = MediaStore.Audio.Media.IS_MUSIC;

    public String[] getAudioProjection() {
        return audioProjection;
    }

    public String getSortOrder() {
        if (sortOrder == null)
            return defaultSortOrder;
        return sortOrder;
    }

    public final void setSortOrder(@Nullable String sortOrder) {
        this.sortOrder = sortOrder;
    }

    protected final String getSelection() {
        return selection;
    }

    /**
     * Set the sort order when searching for audio items
     *
     * @param defaultSortOrder : MediaStore Fields, e.g. MediaStore.Audio.Media.TITLE + " ASC"
     * */
    public abstract void setDefaultSortOrder(String defaultSortOrder);
}
