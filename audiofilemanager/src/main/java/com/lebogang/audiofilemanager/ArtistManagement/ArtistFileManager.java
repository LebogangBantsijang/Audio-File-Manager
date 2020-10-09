package com.lebogang.audiofilemanager.ArtistManagement;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Callbacks.ArtistCallBacks;
import com.lebogang.audiofilemanager.Models.ArtistMediaItem;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.LinkedHashMap;
import java.util.List;

public class ArtistFileManager {
    private ContentResolver resolver;
    private ArtistOperationsManager operationsManger;
    private MutableLiveData<List<ArtistMediaItem>> liveDataAudioItems;
    private LifecycleOwner lifecycleOwner;
    private ArtistCallBacks artistCallBacks;
    private LinkedHashMap<String, ArtistMediaItem> hashMap = new LinkedHashMap<>();

    public ArtistFileManager(Context context, LifecycleOwner owner) {
        this.lifecycleOwner = owner;
        resolver = context.getApplicationContext().getContentResolver();
        liveDataAudioItems = new MutableLiveData<>();
        operationsManger = new ArtistOperationsManager();
    }

    public ArtistFileManager() {
        operationsManger = new ArtistOperationsManager();
    }

    /**
     * Register callbacks to be notified when changes occur to the data
     * */
    public void registerCallbacks(ArtistCallBacks artistCallBacks){
        this.artistCallBacks = artistCallBacks;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    private DefaultLifecycleObserver getDefaultLifecycleObserver(){
        return new DefaultLifecycleObserver() {

            /**
             * Initialise the live data when onCreate is called
             * */
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveDataAudioItems.observe(lifecycleOwner, mediaItems -> {
                    artistCallBacks.onGetAudio(mediaItems);
                    for (ArtistMediaItem item:mediaItems)
                        hashMap.put(item.getTitle(), item);
                });
            }

            /**
             * Get files from device when activity/fragment resumes
             * */
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                if (operationsManger.getLastKnownItemCount() != hashMap.size())
                    new Handler().post(()->{
                        List<ArtistMediaItem> mediaItems = operationsManger.getArtists(resolver);
                        boolean update = false;
                        for (ArtistMediaItem item:mediaItems){
                            if (!hashMap.containsKey(item.getTitle())){
                                update = true;
                                hashMap.put(item.getTitle(), item);
                            }
                        }
                        if (update)
                            liveDataAudioItems.setValue(mediaItems);
                    });
            }

            /**
             * uninitialize live data when component is destroyed
             * */
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                lifecycleOwner.getLifecycle().removeObserver(this);
            }
        };
    }

    /**
     * Get Artist data objects
     * You can call this even if you have not registered a callback
     * */
    public List<ArtistMediaItem> getItems(Context context){
        return operationsManger.getArtists(context.getApplicationContext().getContentResolver());
    }
    /**
     * Get an artist item with an id that matches the one provided in the parameter
     * */
    public ArtistMediaItem getItem(Context context, long mediaId){
        return operationsManger.getArtist(context.getApplicationContext().getContentResolver(), mediaId);
    }
}
