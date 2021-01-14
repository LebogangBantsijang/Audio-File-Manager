/*
 * Copyright (c) 2020. Lebogang Bantsijng
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *   implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 *
 */

package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Audio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AudioManager extends DatabaseOperations{
    private AudioCallbacks callbacks;
    private final MutableLiveData<List<Audio>> liveData;
    private long albumId = -1;
    private long artistId = -1;
    private String albumName = null;
    private String artistName = null;
    private List<Long> audioIds = new ArrayList<>();
    private AudioSearchType audioSearchType;

    public AudioManager(Context context) {
        super(context);
        liveData = new MutableLiveData<>();
    }

    /**
     * To avoid getting audio items manually, register collBacks using this method
     * @param owner is required
     * @param callbacks is also required
     * */
    public void registerCallbacks(AudioCallbacks callbacks, LifecycleOwner owner){
        audioSearchType = AudioSearchType.GENERAL_AUDIO;
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
        audioSearchType = AudioSearchType.AUDIO_BY_ALBUM_NAME;
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
        audioSearchType = AudioSearchType.AUDIO_BY_ALBUM_ID;
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
        audioSearchType = AudioSearchType.AUDIO_BY_ARTIST_NAME;
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
        audioSearchType = AudioSearchType.AUDIO_BY_ARTIST_ID;
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
    public void registerCallbacksForAudioIds(AudioCallbacks callbacks, LifecycleOwner owner, List<Long> audioIds){
        audioSearchType = AudioSearchType.SPECIFIC_AUDIO;
        this.callbacks = callbacks;
        this.audioIds = audioIds;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, audioList -> callbacks.onQueryComplete(audioList));
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                switch (audioSearchType){
                    case AUDIO_BY_ALBUM_ID:
                        liveData.setValue(getAudioByAlbumId(albumId));
                        break;
                    case AUDIO_BY_ALBUM_NAME:
                        liveData.setValue(getAudioByAlbumName(albumName));
                        break;
                    case AUDIO_BY_ARTIST_NAME:
                        liveData.setValue(getAudioByArtistName(artistName));
                        break;
                    case AUDIO_BY_ARTIST_ID:
                        liveData.setValue(getAudioByArtistId(artistId));
                        break;
                    case SPECIFIC_AUDIO:
                        liveData.setValue(getAudio(audioIds));
                        break;
                    case GENERAL_AUDIO:
                        liveData.setValue(getAudio());
                        break;
                }
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }

    private enum AudioSearchType{
        GENERAL_AUDIO, AUDIO_BY_ALBUM_ID, AUDIO_BY_ALBUM_NAME, AUDIO_BY_ARTIST_ID, AUDIO_BY_ARTIST_NAME, SPECIFIC_AUDIO
    }
}
