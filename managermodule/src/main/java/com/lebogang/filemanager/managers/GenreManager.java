/*
 * Copyright (c) 2021. Lebogang Bantsijng
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

package com.lebogang.filemanager.managers;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lebogang.filemanager.connectors.GenreConnector;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.connectors.helpers.GenreDatabaseInterface;
import com.lebogang.filemanager.data.Genre;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GenreManager implements GenreDatabaseInterface, OptionsInterface {
    private final GenreConnector genreConnector;
    private final HashMap<Integer, OnMediaContentChanged> observerHashMap;
    private int key = -1;

    public GenreManager(Context context) {
        genreConnector = new GenreConnector(context.getApplicationContext().getContentResolver());
        observerHashMap = new HashMap<>();
    }

    public void registerObserver(@NonNull OnMediaContentChanged onMediaContentChanged){
        key = 1;
        observerHashMap.put(key, onMediaContentChanged);
        genreConnector.registerObserver(getContentObserver());
    }

    public void unregisterObserver(){
        genreConnector.unregisterObserver();
    }

    public void registerObserver(long genreId,@NonNull OnMediaContentChanged onMediaContentChanged){
        key = 2;
        observerHashMap.put(key, onMediaContentChanged);
        genreConnector.registerObserver(getContentObserver());
    }

    public void unregisterObserver(long genreId){
        genreConnector.unregisterObserver();
    }

    private ContentObserver getContentObserver(){
        return new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                observerHashMap.get(key).onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                observerHashMap.get(key).onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri, int flags) {
                observerHashMap.get(key).onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @NonNull Collection<Uri> uris, int flags) {
                observerHashMap.get(key).onMediaContentChanged();
            }
        };
    }

    @Override
    public List<Genre> getGenre() {
        return genreConnector.getGenre();
    }

    @Override
    public List<Genre> getGenre(String name) {
        return genreConnector.getGenre(name);
    }

    @Override
    public String[] getGenreAudioIds(long id) {
        return new String[0];
    }

    @Override
    public void setSortOrder(String order) {
        ConnectorTools.DEFAULT_GENRE_SORT_ORDER = order;
    }
}
