package com.lebogang.audiofilemanager.AudioManagement;

import android.net.Uri;
import android.provider.MediaStore;

abstract class DatabaseScheme {

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

    public String[] getAudioProjection() {
        return audioProjection;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    protected String produceWhereClauses(String[] strings){
        String temp = MediaStore.Audio.Media._ID + "=?";
        for (int x = 1; x < strings.length; x++){
            temp += " AND " + MediaStore.Audio.Media._ID + "=?";
        }
        return temp;
    }

    public abstract void setSortOrder(String order);
}
