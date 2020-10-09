package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Callbacks.AlbumCallBacks;
import com.lebogang.audiofilemanager.Models.AlbumMediaItem;

import java.util.LinkedHashMap;
import java.util.List;

public class AlbumFileManager {
    private ContentResolver resolver;
    private AlbumOperationsManager operationsManger;
    private MutableLiveData<List<AlbumMediaItem>> liveDataAudioItems;
    private LifecycleOwner lifecycleOwner;
    private AlbumCallBacks albumCallBacks;
    private LinkedHashMap<String, AlbumMediaItem> hashMap = new LinkedHashMap<>();

    public AlbumFileManager(Context context, LifecycleOwner owner) {
        this.lifecycleOwner = owner;
        resolver = context.getApplicationContext().getContentResolver();
        liveDataAudioItems = new MutableLiveData<>();
        operationsManger = new AlbumOperationsManager();
    }

    public AlbumFileManager() {
        operationsManger = new AlbumOperationsManager();
    }

    public void registerCallbacks(AlbumCallBacks albumCallBacks){
        this.albumCallBacks = albumCallBacks;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    private DefaultLifecycleObserver getDefaultLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveDataAudioItems.observe(lifecycleOwner, mediaItems -> {
                    albumCallBacks.onGetAudio(mediaItems);
                    for (AlbumMediaItem item:mediaItems)
                        hashMap.put(item.getTitle(), item);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                if (operationsManger.getLastKnownItemCount() != hashMap.size())
                    new Handler().post(()->{
                        List<AlbumMediaItem> mediaItems = operationsManger.getAlbums(resolver);
                        boolean update = false;
                        for (AlbumMediaItem item:mediaItems){
                            if (!hashMap.containsKey(item.getTitle())){
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

    public List<AlbumMediaItem> getItems(Context context){
        return operationsManger.getAlbums(context.getApplicationContext().getContentResolver());
    }

    public AlbumMediaItem getItem(Context context, long mediaId){
        return operationsManger.getAlbum(context.getApplicationContext().getContentResolver(), mediaId);
    }
}
