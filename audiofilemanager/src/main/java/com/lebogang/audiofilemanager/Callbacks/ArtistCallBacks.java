package com.lebogang.audiofilemanager.Callbacks;

import android.content.ContentValues;

import com.lebogang.audiofilemanager.AudioManagement.AudioFileManger;
import com.lebogang.audiofilemanager.Models.ArtistMediaItem;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.List;

public interface ArtistCallBacks {
    /**
     * Called once all mediaItems are collected
     *
     * */
    void onGetAudio(List<ArtistMediaItem> mediaItems);

}
