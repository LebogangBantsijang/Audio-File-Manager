package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentValues;
import android.content.Context;

import com.lebogang.audiofilemanager.Models.AlbumMediaItem;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.List;

public abstract class ManagerWithContext {
    private final AudioOperationsManger manager;

    public ManagerWithContext() {
        this.manager = new AudioOperationsManger();
    }

    /**
     * Get Audio data objects
     *
     * @return list of Audio items
     * */
    public List<AudioMediaItem> getItems(Context context){
        return manager.getAudio(context.getApplicationContext().getContentResolver());
    }

    /**
     * Get Audio items that belong to a specific album/artist/playlist or genre
     * You can call this even if you have not registered a callback
     * */
    public List<AudioMediaItem> getItems(Context context, String mediaTitle, long mediaId, int mediaType){
        return manager.getAudio(context.getApplicationContext().getContentResolver(), mediaTitle, mediaId,mediaType);
    }

    /**
     * Get an audio item with an id that matches the one provided in the parameter
     * */
    public AudioMediaItem getItem(Context context, long mediaId){
        return manager.getAudioItem(context.getApplicationContext().getContentResolver(),mediaId);
    }

    /**
     * Update audio item and add the new value to the live data
     * Call this method is you have registered audio callbacks
     *
     * */
    public boolean updateItem(Context context, long mediaId, ContentValues values){
        int updatedRows = manager.updateAudio(context.getApplicationContext().getContentResolver(),mediaId,values);
        return updatedRows > 0;
    }

    /**
     * Remove audio item from local AudioDB
     * Call this method is you have registered audio callbacks
     *
     * */
    public boolean removeItem(Context context, long mediaId){
        int deletedRows = manager.removeAudio(context.getApplicationContext().getContentResolver(),mediaId);
        return deletedRows > 0;
    }
}
