package com.lebogang.audiofilemanager.GenreManagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Genre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GenreManager extends DatabaseOperations {
    private final Context context;
    private GenreCallbacks callbacks;
    private final MutableLiveData<List<Genre>> liveData;

    public GenreManager(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
    }

    /**
     * To avoid getting genre items manually, register collBacks using this method
     * @param lifecycleOwner is required
     * @param callbacks is also required
     * */
    public void registerCallbacks(LifecycleOwner lifecycleOwner,GenreCallbacks callbacks){
        this.callbacks = callbacks;
        lifecycleOwner.getLifecycle().addObserver(getLifecycleObserver());
    }

    @Override
    public List<Genre> getGenres() {
        return super.queryItems(context);
    }

    @Override
    public Genre getGenreItemWithId(long id) {
        return super.queryItemID(context, id);
    }

    @Override
    public Genre getGenreItemWithName(String name) {
        return super.queryItemName(context, name);
    }

    /**
     * Some genres can have the same name with different IDs. This will return a list without the
     * duplicates. However, the song count specified in the genre and the actual song count may not match
     * @param genreList list containing the genres
     * @return filtered list.
     * */
    public static List<Genre> groupByName(List<Genre> genreList){
        LinkedHashMap<String, Genre> linkedHashMap = new LinkedHashMap<>();
        for (Genre genre:genreList){
            if (!linkedHashMap.containsKey(genre.getTitle()))
                linkedHashMap.put(genre.getTitle(), genre);
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, genreList -> {
                    callbacks.onQueryComplete(genreList);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                liveData.setValue(getGenres());
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }
}
