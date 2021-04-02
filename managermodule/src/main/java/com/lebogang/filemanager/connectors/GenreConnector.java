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
import android.database.ContentObserver;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.connectors.helpers.GenreDatabaseInterface;
import com.lebogang.filemanager.data.Genre;
import com.lebogang.filemanager.data.helpers.UriHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.provider.MediaStore.Audio.Genres.Members.AUDIO_ID;
import static android.provider.MediaStore.Audio.Genres.Members.GENRE_ID;
import static android.provider.MediaStore.Audio.Genres.NAME;
import static android.provider.MediaStore.Audio.Genres._ID;

public class GenreConnector implements GenreDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;
    private final HashMap<Integer, ContentObserver> observerHashMap;

    public GenreConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        observerHashMap = new HashMap<>();
    }

    @Override
    public List<Genre> getGenre() {
        Cursor cursor = contentResolver.query(ConnectorTools.GENRE_EXTERNAL_URI, ConnectorTools.GENRE_PROJECTION
                ,null, null, ConnectorTools.DEFAULT_GENRE_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Genre> getGenre(String name) {
        Cursor cursor = contentResolver.query(ConnectorTools.GENRE_EXTERNAL_URI, ConnectorTools.GENRE_PROJECTION
                , NAME, new String[]{name}, ConnectorTools.DEFAULT_GENRE_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public String[] getGenreAudioIds(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.getGenreAudioExternalUri(id),ConnectorTools.GENRE_AUDIO_PROJECTION
                , GENRE_ID + "=?", new String[]{Long.toString(id)}, null);
        return iterateCursorIds(cursor);
    }

    public void registerObserver(long genreId, @NonNull ContentObserver contentObserver) {
        observerHashMap.put(1, contentObserver);
        contentResolver.registerContentObserver(ConnectorTools.getGenreAudioExternalUri(genreId), true, contentObserver);
    }

    public void registerObserver(@NonNull ContentObserver contentObserver) {
        observerHashMap.put(2, contentObserver);
        contentResolver.registerContentObserver(ConnectorTools.GENRE_EXTERNAL_URI, true, contentObserver);
    }

    public void unregisterObserver() {
        contentResolver.unregisterContentObserver(observerHashMap.get(1));
    }

    public void unregisterObserver(long genreId) {
        contentResolver.unregisterContentObserver(observerHashMap.get(2));
    }

    private String[] iterateCursorIds(Cursor cursor) throws NullPointerException{
        if (cursor == null)
            return null;
        String[] audioIdArray = new String[cursor.getCount()];
        int counter = 0;
        if (cursor.moveToFirst()){
            do {
                long audioId = cursor.getLong(cursor.getColumnIndex(AUDIO_ID));
                audioIdArray[counter] = Long.toString(audioId);
                counter++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return audioIdArray;
    }

    private List<Genre> iterateCursor(Cursor cursor){
        if (cursor == null)
            return Collections.emptyList();
        List<Genre> genreList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(_ID));
                Genre genre = new Genre.Builder()
                        .setId(id)
                        .setTitle(cursor.getString(cursor.getColumnIndex(NAME)))
                        .setContentUri(UriHelper.createContentUri(ConnectorTools.GENRE_EXTERNAL_URI, id))
                        .build();
                genreList.add(genre);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return genreList;
    }
}
