package com.lebogang.audiofilemanager.Callbacks;

import android.content.ContentValues;

import com.lebogang.audiofilemanager.AudioManagement.AudioFileManger;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;
import com.lebogang.audiofilemanager.Models.PlaylistMediaItem;

import java.util.List;

public interface PlaylistCallBacks {
    /**
     * Called once all mediaItems are collected
     * */
    void onGetAudio(List<PlaylistMediaItem> mediaItems);

    void onCreate(PlaylistMediaItem mediaItem);

    void onUpdate(PlaylistMediaItem mediaItem);

    void onRemove(PlaylistMediaItem mediaItem);

}
