package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Audio;

import java.util.List;

public class AudioManager extends DatabaseOperations{
    private final Context context;
    private AudioCallbacks callbacks;
    private MutableLiveData<List<Audio>> liveData;
    private long albumId = -1, artistId = -1;
    private String artistName = null, albumName = null;
    private String[] audioIds = null;

    public AudioManager(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param callbacks is also required
     * */
    public void registerCallbacks(AudioCallbacks callbacks, LifecycleOwner owner){
        this.callbacks = callbacks;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param name of the album. This will focus on audio belonging to the album
     * @param callbacks is also required
     * */
    public void registerCallbacksForAlbumAudio(AudioCallbacks callbacks, LifecycleOwner owner, String name){
        this.callbacks = callbacks;
        this.albumName = name;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param id of the album. This will focus on audio belonging to the album
     * @param callbacks is also required
     * */
    public void registerCallbacksForAlbumAudio(AudioCallbacks callbacks, LifecycleOwner owner, long id){
        this.callbacks = callbacks;
        this.albumId = id;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param name of the artist. This will focus on audio belonging to the artist
     * @param callbacks is also required
     * */
    public void registerCallbacksForArtistAudio(AudioCallbacks callbacks, LifecycleOwner owner, String name){
        this.callbacks = callbacks;
        this.artistName = name;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param id of the artist. This will focus on audio belonging to the artist
     * @param callbacks is also required
     * */
    public void registerCallbacksForArtistAudio(AudioCallbacks callbacks, LifecycleOwner owner, long id){
        this.callbacks = callbacks;
        this.artistId = id;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param audioIds of the audios in focus. This will focus on audio with ids that match the ones in the provided array
     * @param callbacks is also required
     * */
    public void registerCallbacksForAudioIds(AudioCallbacks callbacks, LifecycleOwner owner, String[] audioIds){
        this.callbacks = callbacks;
        this.audioIds = audioIds;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    @Override
    public List<Audio> getAudio() {
        return super.query(context);
    }

    @Override
    public Audio getAudioItemWithId(long id) {
        return super.queryWithId(context,id);
    }

    @Override
    public Audio getAudioItemWithName(String name) {
        return super.queryWithName(context, name);
    }

    @Override
    public List<Audio> getAlbumAudioWithID(long id) {
        return super.queryAlbumSongsWithID(context, id);
    }

    @Override
    public List<Audio> getAlbumAudioWithName(String name) {
        return super.queryAlbumSongsWithName(context, name);
    }

    @Override
    public List<Audio> getArtistAudioWithID(long id) {
        return super.queryArtistSongsWithID(context, id);
    }

    @Override
    public List<Audio> getArtistAudioWithName(String name) {
        return super.queryArtistSongsWithName(context, name);
    }

    @Override
    public List<Audio> getArtistAudioWithIDArray(String[] audioIds) {
        return super.queryWithIdArray(context, audioIds);
    }

    @Override
    public boolean updateAudio(long id, ContentValues values) {
        boolean result = super.update(context,id, values);
        if (result && liveData != null){
            liveData.setValue(query(context));
        }
        return result;
    }

    @Override
    public boolean deleteAudio(long id) {
        boolean results = super.delete(context, id);
        if (results && liveData!=null)
            liveData.setValue(query(context));
        return results;
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, audioList -> {
                    callbacks.onQueryComplete(audioList);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                if (albumId != -1)
                    liveData.setValue(AudioManager.super.queryAlbumSongsWithID(context, albumId));
                else if (artistId != -1)
                    liveData.setValue(AudioManager.super.queryArtistSongsWithID(context, albumId));
                else if (albumName != null)
                    liveData.setValue(AudioManager.super.queryAlbumSongsWithName(context, albumName));
                else if (artistName != null)
                    liveData.setValue(AudioManager.super.queryArtistSongsWithName(context, artistName));
                else if (audioIds != null)
                    liveData.setValue(AudioManager.super.queryWithIdArray(context, audioIds));
                else
                    liveData.setValue(AudioManager.super.query(context));
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }
}
