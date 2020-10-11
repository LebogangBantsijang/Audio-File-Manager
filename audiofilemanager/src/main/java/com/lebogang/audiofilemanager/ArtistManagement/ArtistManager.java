package com.lebogang.audiofilemanager.ArtistManagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Artist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ArtistManager extends DatabaseOperations {
    private final Context context;
    private ArtistCallbacks callbacks;
    private final MutableLiveData<List<Artist>> liveData;

    public ArtistManager(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
    }

    /**
     * To avoid getting artists items manually, register collBacks using this method
     * @param lifecycleOwner is required
     * @param callbacks is also required
     * */
    public void registerCallbacks(LifecycleOwner lifecycleOwner,ArtistCallbacks callbacks){
        this.callbacks = callbacks;
        lifecycleOwner.getLifecycle().addObserver(getLifecycleObserver());
    }

    @Override
    public List<Artist> getArtists(){
        return super.queryItems(context);
    }

    @Override
    public Artist getArtistItemWithId(long id){
        return super.queryItemID(context,id);
    }

    @Override
    public Artist getArtistItemWithName(String name){
        return super.queryItemName(context, name);
    }

    /**
     * Some artists can have the same name with different IDs. This will return a list without the
     * duplicates. However, the song count specified in the artist and the actual song count may not match
     * @param artistList list containing the albums
     * @return filtered list.
     * */
    public static List<Artist> groupByName(List<Artist> artistList){
        LinkedHashMap<String, Artist> linkedHashMap = new LinkedHashMap<>();
        for (Artist artist:artistList){
            if (!linkedHashMap.containsKey(artist.getTitle()))
                linkedHashMap.put(artist.getTitle(), artist);
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, artistList -> {
                    callbacks.onQueryComplete(artistList);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                liveData.setValue(getArtists());
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }
}
