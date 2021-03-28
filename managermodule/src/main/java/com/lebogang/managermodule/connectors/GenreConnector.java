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

package com.lebogang.managermodule.connectors;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.provider.MediaStore.Audio;

import com.lebogang.managermodule.connectors.helpers.ConnectorTools;
import com.lebogang.managermodule.connectors.helpers.GenreDatabaseInterface;
import com.lebogang.managermodule.data.Genre;
import com.lebogang.managermodule.data.helpers.UriHelper;

import java.util.Collections;
import java.util.List;

public class GenreConnector implements GenreDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;

    public GenreConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
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
                , Audio.Genres.NAME, new String[]{name}, ConnectorTools.DEFAULT_GENRE_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public String[] getGenreAudioIds(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.getGenreAudioExternalUri(id),ConnectorTools.GENRE_AUDIO_PROJECTION
                , Audio.Genres.Members.GENRE_ID + "=?", new String[]{Long.toString(id)}, null);
        return iterateCursorIds(cursor);
    }

    @Override
    public void observeGenreChanges(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.GENRE_EXTERNAL_URI, true, contentObserver);
    }

    @Override
    public void observeGenreAudioChanges(long genreId, ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.getGenreAudioExternalUri(genreId), true, contentObserver);
    }

    @Override
    public void stopGenreObserving() {
        contentResolver.unregisterContentObserver(contentObserver);
    }

    @Override
    public void stopGenreAudioObserving() {
        contentResolver.unregisterContentObserver(contentObserver);
    }

    private String[] iterateCursorIds(Cursor cursor) throws NullPointerException{
        if (cursor == null)
            return null;
        String[] audioIdArray = new String[cursor.getCount()];
        int counter = 0;
        if (cursor.moveToFirst()){
            do {
                long audioId = cursor.getLong(cursor.getColumnIndex(Audio.Genres.Members.AUDIO_ID));
                audioIdArray[counter] = Long.toString(audioId);
                counter++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return audioIdArray;
    }

    private List<Genre> iterateCursor(Cursor cursor){
        List<Genre> genreList = Collections.emptyList();
        if (cursor == null)
            return genreList;
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(Audio.Genres._ID));
                Genre genre = new Genre.Builder()
                        .setId(id)
                        .setTitle(cursor.getString(cursor.getColumnIndex(Audio.Genres.NAME)))
                        .setContentUri(UriHelper.createContentUri(ConnectorTools.GENRE_EXTERNAL_URI, id))
                        .build();
                genreList.add(genre);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return genreList;
    }
}