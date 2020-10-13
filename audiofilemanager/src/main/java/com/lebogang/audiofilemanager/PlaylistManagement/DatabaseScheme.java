package com.lebogang.audiofilemanager.PlaylistManagement;

import android.net.Uri;
import android.provider.MediaStore;

abstract class DatabaseScheme {
    private final String[] playlistProjection = {
            MediaStore.Audio.Playlists._ID,
            MediaStore.Audio.Playlists.NAME
    };

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
    }

    public String[] getPlaylistProjection() {
        return playlistProjection;
    }

    /**
     * Set the sort order when searching for artist items
     * @param defaultSortOrder : MediaStore Fields, e.g. {@link MediaStore.Audio.Playlists#NAME} + " ASC"
     * */
    public abstract void setSortOrder(String defaultSortOrder);

    protected String produceWhereClauses(String[] strings){
        if (strings.length > 0){
            String temp = "audio_id IN (" + strings[0];
            for (int x = 1; x < strings.length; x++){
                temp += ", " + strings[x];
            }
            temp += ")";
            return temp;
        }
        return "audio_id IN (0)";
    }

}
