package com.lebogang.audiofilemanager.Callbacks;

import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.List;

public interface AudioCallBacks {
    /**
     * Called once all mediaItems are collected
     * */
    void onGetAudio(List<AudioMediaItem> mediaItems);

    /**
     * Update Audio media Item
     * */
    void onUpdate(AudioMediaItem mediaItem);

    /**
     * Remove Audio media Item
     * */
    void onRemove(AudioMediaItem mediaItem);

}
