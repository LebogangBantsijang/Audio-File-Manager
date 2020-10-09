package com.lebogang.audiofilemanager.DatabaseScheme;

import android.provider.MediaStore;

public abstract class PlaylistDB {
    private final String[] playlistProjection = {
            MediaStore.Audio.Playlists._ID,
            MediaStore.Audio.Playlists.NAME
    };

    private final String[] audioProjection = {
            MediaStore.Audio.Media.DURATION
    };

    private final String[] memberProjection = {
            MediaStore.Audio.Playlists.Members.AUDIO_ID
    };

    public String[] getPlaylistProjection() {
        return playlistProjection;
    }

    public String[] getAudioProjection() {
        return audioProjection;
    }

    public String[] getMemberProjection() {
        return memberProjection;
    }
}
