package com.lebogang.audiofilemanager.PlaylistManagement;

import android.content.Context;
import android.net.Uri;

import com.android.tools.r8.n;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;
import com.lebogang.audiofilemanager.Models.PlaylistMediaItem;

import java.util.List;

public class ManagerWithContext {

    private final PlaylistOperationsManager operationsManager;

    public ManagerWithContext(){
        operationsManager = new PlaylistOperationsManager();
    }

    public List<PlaylistMediaItem> getItems(Context context){
        return operationsManager.getPlaylist(context.getApplicationContext().getContentResolver());
    }

    public Uri createNewItem(Context context, String name){
        Uri uri = operationsManager.createNewPlaylist(context.getApplicationContext().getContentResolver(), name);
        return uri;
    }

    public int updateItem(Context context,PlaylistMediaItem mediaItem, String values){
        int updatedRows = operationsManager.updatePlaylist(context.getApplicationContext().getContentResolver(),mediaItem.getMediaId(),values);
        return updatedRows;
    }

    public int removeItem(Context context,PlaylistMediaItem mediaItem){
        int deletedRows = operationsManager.removePlaylist(context.getApplicationContext().getContentResolver(), mediaItem.getMediaId());
        return deletedRows;
    }

    public Uri addAudioToItem(Context context,PlaylistMediaItem playlistMediaItem, AudioMediaItem mediaItem){
        Uri uri = operationsManager.addAudioToPlaylist(context.getApplicationContext().getContentResolver(),playlistMediaItem.getMediaId(), mediaItem.getMediaId());
        return uri;
    }

    public int removeAudioFromItem(Context context,PlaylistMediaItem playlistMediaItem, AudioMediaItem mediaItem){
        int deletedRows = operationsManager.removeAudioFromPlaylist(context.getApplicationContext().getContentResolver(),playlistMediaItem.getMediaId(), mediaItem.getMediaId());
        return deletedRows;
    }

    public boolean moveAudioToPosition(Context context,PlaylistMediaItem playlistMediaItem, int from, int to){
        return operationsManager.moveAudioToPosition(context.getApplicationContext().getContentResolver(),playlistMediaItem.getMediaId(),from, to);
    }
}
