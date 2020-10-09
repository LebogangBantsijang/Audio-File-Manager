package com.lebogang.audiofilemanager.Callbacks;

import android.content.ContentValues;

import com.lebogang.audiofilemanager.AudioManagement.AudioFileManger;
import com.lebogang.audiofilemanager.Models.AlbumMediaItem;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.List;

public interface AlbumCallBacks {
    /**
     * Called once all mediaItems are collected
     *
     * */
    void onGetAudio(List<AlbumMediaItem> mediaItems);

}
