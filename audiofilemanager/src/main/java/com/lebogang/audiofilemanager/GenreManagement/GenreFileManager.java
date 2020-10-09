package com.lebogang.audiofilemanager.GenreManagement;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Callbacks.GenreCallBacks;
import com.lebogang.audiofilemanager.Models.GenreMediaItem;

import java.util.LinkedHashMap;
import java.util.List;

public class GenreFileManager {
    private ContentResolver resolver;
    private GenreOperationsManager operationsManger;
    private MutableLiveData<List<GenreMediaItem>> liveDataAudioItems;
    private LifecycleOwner lifecycleOwner;
    private GenreCallBacks genreCallBacks;
    private LinkedHashMap<String, GenreMediaItem> hashMap = new LinkedHashMap<>();

    public GenreFileManager(Context context, LifecycleOwner owner) {
        this.lifecycleOwner = owner;
        resolver = context.getApplicationContext().getContentResolver();
        liveDataAudioItems = new MutableLiveData<>();
        operationsManger = new GenreOperationsManager();
    }

    public GenreFileManager(Context context){
        resolver = context.getApplicationContext().getContentResolver();
        operationsManger = new GenreOperationsManager();
    }

    public void registerCallbacks(GenreCallBacks genreCallBacks){
        this.genreCallBacks = genreCallBacks;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    private DefaultLifecycleObserver getDefaultLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveDataAudioItems.observe(lifecycleOwner, mediaItems -> {
                    genreCallBacks.onGetAudio(mediaItems);
                    for (GenreMediaItem item:mediaItems)
                        hashMap.put(item.getTitle(), item);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                if (operationsManger.getLastKnownItemCont() != hashMap.size())
                    new Handler().post(()->{
                        List<GenreMediaItem> mediaItems = operationsManger.getGenre(resolver);
                        boolean update = false;
                        for (GenreMediaItem item:mediaItems){
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
}
