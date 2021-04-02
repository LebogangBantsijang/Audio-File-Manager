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

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lebogang.filemanager.connectors.ArtistConnector;
import com.lebogang.filemanager.connectors.helpers.ArtistDatabaseInterface;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.data.Artist;

import java.util.Collection;
import java.util.List;

public class ArtistManager implements ArtistDatabaseInterface, OptionsInterface {
    private OnMediaContentChanged onMediaContentChanged;
    private final ArtistConnector artistConnector;
    public ArtistManager(Context context) {
        artistConnector = new ArtistConnector(context.getApplicationContext().getContentResolver());
    }

    public void registerObserver(@NonNull OnMediaContentChanged onMediaContentChanged){
        this.onMediaContentChanged = onMediaContentChanged;
        artistConnector.registerObserver(getContentObserver());
    }

    public void unregisterObserver(){
        artistConnector.unregisterObserver();
    }

    private ContentObserver getContentObserver(){
        return new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri, int flags) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @NonNull Collection<Uri> uris, int flags) {
                onMediaContentChanged.onMediaContentChanged();
            }
        };
    }

    @Override
    public List<Artist> getArtists() {
        return artistConnector.getArtists();
    }

    @Override
    public List<Artist> getArtists(long id) {
        return artistConnector.getArtists(id);
    }

    @Override
    public List<Artist> getArtists(String name) {
        return artistConnector.getArtists(name);
    }

    @Override
    public int updateArtist(long id, ContentValues values) {
        return artistConnector.updateArtist(id, values);
    }

    @Override
    public void setSortOrder(String order) {
        ConnectorTools.DEFAULT_ARTIST_SORT_ORDER = order;
    }
}
