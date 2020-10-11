package com.lebogang.audiofilemanager.ArtistManagement;

import android.net.Uri;
import android.provider.MediaStore;

public abstract class DatabaseScheme {

    private final String[] artistProjection = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
    };

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    }

    public String[] getArtistProjection() {
        return artistProjection;
    }

    /**
     * Set the sort order when searching for artist items
     *
     * @param defaultSortOrder : MediaStore Fields, e.g. MediaStore.Audio.Artists.ARTIST + " ASC"
     * */
    public abstract void setSortOrder(String defaultSortOrder);
}
