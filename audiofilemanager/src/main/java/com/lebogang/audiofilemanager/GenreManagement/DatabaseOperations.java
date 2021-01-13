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

package com.lebogang.audiofilemanager.GenreManagement;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Genre;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Genres.NAME + " ASC";
    private final List<String> audioIds = new ArrayList<>();
    private final List<Genre> genreList = new ArrayList<>();
    private final Context context;

    public DatabaseOperations(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get genres from device
     * @return list of genres
     * */
    public List<Genre> getGenres(){
        genreList.clear();
        Cursor cursor = context.getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
                getAudioIds(genreId);
                genreList.add(new Genre(genreId, title, audioIds));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return genreList;
    }

    /**
     * Call this method before you queryItems/getArtistItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Genres#NAME + " ASC"}
     * */
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    /**
     * Get genres from device
     * @param id of the required genre item
     * @return list of genres
     * */
    public Genre getGenre(long id){
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                        ,MediaStore.Audio.Genres._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
            getAudioIds(genreId);
            Genre genre = new Genre(genreId, title, audioIds);
            cursor.close();
            return genre;
        }
        return null;
    }

    /**
     * Get genres from device
     * @param name of the required genre item
     * @return list of genres
     * */
    public Genre getGenreByName(String name){
        Cursor cursor = context.getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                        ,MediaStore.Audio.Genres.NAME + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
            getAudioIds(genreId);
            Genre genre = new Genre(genreId, title, audioIds);
            cursor.close();
            return genre;
        }
        return null;
    }

    private List<String> getAudioIds(long id){
        audioIds.clear();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Genres.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id)
                , new String[]{MediaStore.Audio.Genres.Members.AUDIO_ID}, null, null, null);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                String audioId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.AUDIO_ID));
                audioIds.add(audioId);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioIds;
    }
}
