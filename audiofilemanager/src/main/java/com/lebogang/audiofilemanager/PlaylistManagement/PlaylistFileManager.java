package com.lebogang.audiofilemanager.PlaylistManagement;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Callbacks.PlaylistCallBacks;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;
import com.lebogang.audiofilemanager.Models.PlaylistMediaItem;

import java.util.LinkedHashMap;
import java.util.List;

public class PlaylistFileManager extends ManagerWithContext{
    private ContentResolver resolver;
    private PlaylistOperationsManager operationsManger;
    private MutableLiveData<List<PlaylistMediaItem>> liveDataAudioItems;
    private LifecycleOwner lifecycleOwner;
    private PlaylistCallBacks playlistCallBacks;
    private LinkedHashMap<String, PlaylistMediaItem> hashMap = new LinkedHashMap<>();

    public PlaylistFileManager(Context context, LifecycleOwner owner) {
        super();
        this.lifecycleOwner = owner;
        resolver = context.getApplicationContext().getContentResolver();
        liveDataAudioItems = new MutableLiveData<>();
        operationsManger = new PlaylistOperationsManager();
    }

    public PlaylistFileManager() {
        super();
        operationsManger = new PlaylistOperationsManager();
    }

    public void registerCallbacks(PlaylistCallBacks playlistCallBacks){
        this.playlistCallBacks = playlistCallBacks;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    private DefaultLifecycleObserver getDefaultLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveDataAudioItems.observe(lifecycleOwner, mediaItems -> {
                    playlistCallBacks.onGetAudio(mediaItems);
                    for (PlaylistMediaItem item:mediaItems)
                        hashMap.put(item.getTitle(), item);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                if (operationsManger.getLastKnownItemCont() != hashMap.size())
                    new Handler().post(()->{
                        List<PlaylistMediaItem> mediaItems = operationsManger.getPlaylist(resolver);
                        boolean update = false;
                        for (PlaylistMediaItem item:mediaItems){
                            if (!hashMap.containsValue(item)){
                                update = true;
                                hashMap.put(item.getTitle(), item);
                            }
                        }
                        if (update)
                            liveDataAudioItems.setValue(mediaItems);
                    });
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                lifecycleOwner.getLifecycle().removeObserver(this);
            }
        };
    }

    public Uri createNewItem(String name){
        Uri uri = operationsManger.createNewPlaylist(resolver, name);
        liveDataAudioItems.setValue(operationsManger.getPlaylist(resolver));
        return uri;
    }

    public int updateItem(PlaylistMediaItem mediaItem, String values){
        int updatedRows = operationsManger.updatePlaylist(resolver,mediaItem.getMediaId(),values);
        int pos = liveDataAudioItems.getValue().indexOf(mediaItem);
        PlaylistMediaItem item = operationsManger.getPlaylist(resolver, mediaItem.getMediaId());
        liveDataAudioItems.getValue().remove(pos);
        liveDataAudioItems.getValue().add(pos, item);
        playlistCallBacks.onUpdate(item);
        return updatedRows;
    }

    public int removeItem(PlaylistMediaItem mediaItem){
        int deletedRows = operationsManger.removePlaylist(resolver, mediaItem.getMediaId());
        int pos = liveDataAudioItems.getValue().indexOf(mediaItem);
        liveDataAudioItems.getValue().remove(pos);
        playlistCallBacks.onRemove(mediaItem);
        return deletedRows;
    }

    public Uri addAudioToItem(PlaylistMediaItem playlistMediaItem, AudioMediaItem mediaItem){
        Uri uri = operationsManger.addAudioToPlaylist(resolver,playlistMediaItem.getMediaId(), mediaItem.getMediaId());
        liveDataAudioItems.setValue(operationsManger.getPlaylist(resolver));
        return uri;
    }

    public int removeAudioFromItem(PlaylistMediaItem playlistMediaItem, AudioMediaItem mediaItem){
        int deletedRows = operationsManger.removeAudioFromPlaylist(resolver,playlistMediaItem.getMediaId(), mediaItem.getMediaId());
        liveDataAudioItems.setValue(operationsManger.getPlaylist(resolver));
        return deletedRows;
    }

    public boolean moveAudioToPosition(PlaylistMediaItem playlistMediaItem, int from, int to){
        return operationsManger.moveAudioToPosition(resolver,playlistMediaItem.getMediaId(),from, to);
    }
}
