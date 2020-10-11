package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Album;

import java.util.List;

class AlbumManager extends DatabaseOperations{
    private final Context context;
    private AlbumCallbacks callbacks;
    private final MutableLiveData<List<Album>> liveData;

    public AlbumManager(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
    }
    
    /**
     * To avoid getting album items manually, register collBacks using this method
     * @param lifecycleOwner is required
     * @param callbacks is also required
     * */
    public void registerCallbacks(LifecycleOwner lifecycleOwner,AlbumCallbacks callbacks){
        this.callbacks = callbacks;
        lifecycleOwner.getLifecycle().addObserver(getLifecycleObserver());
    }

    public List<Album> getAlbums(){
        return super.getAlbums(context);
    }

    public Album getAlbumItemWithId(long id){
        return super.getAlbumItemWithID(context,id);
    }

    public Album getAlbumItemWithName(String name){
        return super.getAlbumItemWithName(context, name);
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, albumList -> {
                    callbacks.onQueryComplete(albumList);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                liveData.setValue(getAlbums());
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }
}
