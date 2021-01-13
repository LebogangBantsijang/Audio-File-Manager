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

package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Album;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AlbumManager extends DatabaseOperations{
    private final Context context;
    private AlbumCallbacks callbacks;
    private final MutableLiveData<List<Album>> liveData;

    public AlbumManager(Context context) {
        super(context);
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

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, albumList -> {
                    callbacks.onQueryComplete(albumList);
                });
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                liveData.setValue(getAlbumsGroupedByName());
            }

        };
    }
}
