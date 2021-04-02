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

package com.lebogang.filemanager.connectors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.lebogang.filemanager.connectors.helpers.AlbumDatabaseInterface;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.data.Album;
import com.lebogang.filemanager.data.helpers.UriHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static android.provider.MediaStore.Audio.Albums._ID;
import static android.provider.MediaStore.Audio.Albums.ALBUM;
import static android.provider.MediaStore.Audio.Albums.ALBUM_ID;
import static android.provider.MediaStore.Audio.Albums.ARTIST;
import static android.provider.MediaStore.Audio.Albums.FIRST_YEAR;
import static android.provider.MediaStore.Audio.Albums.LAST_YEAR;
import static android.provider.MediaStore.Audio.Albums.NUMBER_OF_SONGS;

public class AlbumConnector implements AlbumDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;

    public AlbumConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Album> getAlbums() {
        Cursor cursor = contentResolver.query(ConnectorTools.ALBUM_EXTERNAL_URI, ConnectorTools.ALBUM_PROJECTION
                , null, null, ConnectorTools.DEFAULT_ALBUM_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Album> getAlbums(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.ALBUM_EXTERNAL_URI, ConnectorTools.ALBUM_PROJECTION
                , ALBUM_ID + "=?", new String[]{Long.toString(id)}, ConnectorTools.DEFAULT_ALBUM_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Album> getAlbums(String albumName) {
        Cursor cursor = contentResolver.query(ConnectorTools.ALBUM_EXTERNAL_URI, ConnectorTools.ALBUM_PROJECTION
                , ALBUM + "=?", new String[]{albumName}, ConnectorTools.DEFAULT_ALBUM_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Album> getAlbumsForArtist(String artistName) {
        Cursor cursor = contentResolver.query(ConnectorTools.ALBUM_EXTERNAL_URI, ConnectorTools.ALBUM_PROJECTION
                , ARTIST + "=?", new String[]{artistName}, ConnectorTools.DEFAULT_ALBUM_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public int updateAlbum(long id, ContentValues values) {
        return contentResolver.update(ConnectorTools.ALBUM_EXTERNAL_URI,values,ALBUM_ID + "=?"
                , new String[]{Long.toString(id)});
    }

    public void registerObserver(@NonNull ContentObserver contentObserver){
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.ALBUM_EXTERNAL_URI, true, contentObserver);
    }

    public void unregisterObserver(){
        contentResolver.unregisterContentObserver(contentObserver);
    }

    private List<Album> iterateCursor(Cursor cursor){
        if (cursor == null){
            return Collections.emptyList();
        }
        LinkedHashMap<String, Album> albumLinkedHashMap = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(_ID));
                Album album = new Album.Builder()
                        .setId(id)
                        .setAlbumName(cursor.getString(cursor.getColumnIndex(ALBUM)))
                        .setArtist(cursor.getString(cursor.getColumnIndex(ARTIST)))
                        .setFirstYear(cursor.getString(cursor.getColumnIndex(FIRST_YEAR)))
                        .setLastYear(cursor.getString(cursor.getColumnIndex(LAST_YEAR)))
                        .setNumberOfSongs(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_SONGS)))
                        .setAlbumArtUri(UriHelper.createAlbumArtUri(id))
                        .setContentUri(UriHelper.createContentUri(ConnectorTools.ALBUM_EXTERNAL_URI, id))
                        .build();
                albumLinkedHashMap.put(album.getAlbumName(), album);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return new ArrayList<>(albumLinkedHashMap.values());
    }
}
