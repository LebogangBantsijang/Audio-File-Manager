package com.lebogang.audiofilemanager.DatabaseScheme;

import android.provider.MediaStore;

public class GenreDB {
    private final String[] genreProjection = {
            MediaStore.Audio.Genres._ID,
            MediaStore.Audio.Genres.NAME
    };

    private final String[] memberProjection = {
            MediaStore.Audio.Genres.Members.AUDIO_ID
    };

    private final String[] audioProjection = {
            MediaStore.Audio.Media.DURATION
    };

    public String[] getGenreProjection() {
        return genreProjection;
    }

    public String[] getMemberProjection() {
        return memberProjection;
    }

    public String[] getAudioProjection() {
        return audioProjection;
    }

    public String getSortOder(){
        return MediaStore.Audio.Genres.DEFAULT_SORT_ORDER;
    }
}
