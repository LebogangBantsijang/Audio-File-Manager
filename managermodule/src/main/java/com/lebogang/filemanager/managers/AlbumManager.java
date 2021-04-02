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

import com.lebogang.filemanager.connectors.AlbumConnector;
import com.lebogang.filemanager.connectors.helpers.AlbumDatabaseInterface;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.data.Album;

import java.util.Collection;
import java.util.List;

public class AlbumManager implements AlbumDatabaseInterface, OptionsInterface {
    private final AlbumConnector albumConnector;
    private OnMediaContentChanged onMediaContentChanged;

    public AlbumManager(@NonNull Context context) {
        albumConnector = new AlbumConnector(context.getApplicationContext().getContentResolver());
    }

    @Override
    public List<Album> getAlbums() {
        return albumConnector.getAlbums();
    }

    @Override
    public List<Album> getAlbums(long id) {
        return albumConnector.getAlbums(id);
    }

    @Override
    public List<Album> getAlbums(String albumName) {
        return albumConnector.getAlbums(albumName);
    }

    @Override
    public List<Album> getAlbumsForArtist(String artistName) {
        return albumConnector.getAlbums(artistName);
    }

    @Override
    public int updateAlbum(long id, ContentValues values) {
        return albumConnector.updateAlbum(id, values);
    }

    public void registerObserver(@NonNull ContentObserver contentObserver){
        this.onMediaContentChanged = onMediaContentChanged;
        albumConnector.registerObserver(getContentObserver());
    }

    public void unregisterObserver(){
        albumConnector.unregisterObserver();
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
    public void setSortOrder(String order) {
        ConnectorTools.DEFAULT_ALBUM_SORT_ORDER = order;
    }
}
