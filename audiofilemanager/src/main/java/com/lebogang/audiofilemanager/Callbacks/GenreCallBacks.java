package com.lebogang.audiofilemanager.Callbacks;

import android.content.ContentValues;

import com.lebogang.audiofilemanager.AudioManagement.AudioFileManger;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;
import com.lebogang.audiofilemanager.Models.GenreMediaItem;

import java.util.List;

public interface GenreCallBacks {
    /**
     * Called once all mediaItems are collected
     * */
    void onGetAudio(List<GenreMediaItem> mediaItems);

}
