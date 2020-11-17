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

abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Genres.NAME + " ASC";

    /**
     * Get genres from device
     * @param context used to get the resolver
     * @return list of genres
     * */
    protected List<Genre> queryItems(Context context){
        List<Genre> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
                String[] audioIds = getAudioIds(context, genreId);
                list.add(new Genre(genreId, title, audioIds));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Call this method before you queryItems/getArtistItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Genres#NAME + " ASC"}
     * */
    @Override
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    /**
     * Get genres from device
     * @param context is required to get resolver
     * @param id of the required genre item
     * @return list of genres
     * */
    protected Genre queryItemID(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                        ,MediaStore.Audio.Genres._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
            String[] audioIds = getAudioIds(context, genreId);
            Genre genre = new Genre(genreId, title, audioIds);
            cursor.close();
            return genre;
        }
        return null;
    }

    /**
     * Get genres from device
     * @param context is required to get resolver
     * @param name of the required genre item
     * @return list of genres
     * */
    protected Genre queryItemName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getGenreProjection()
                        ,MediaStore.Audio.Genres.NAME + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long genreId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
            String[] audioIds = getAudioIds(context, genreId);
            Genre genre = new Genre(genreId, title, audioIds);
            cursor.close();
            return genre;
        }
        return null;
    }

    /**
     * Get the total number of items
     * */
    public int getItemCount(Context context){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,new String[]{MediaStore.Audio.Genres._ID}, null,null, null);
        if (cursor != null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    private String[] getAudioIds(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Genres.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id)
                , new String[]{MediaStore.Audio.Genres.Members.AUDIO_ID}, null, null, null);
        if (cursor!= null){
            String[] audioIds = new String[cursor.getCount()];
            if (cursor.moveToFirst()){
                int index = 0;
                do {
                    String audioId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.AUDIO_ID));
                    audioIds[index] = audioId;
                    index++;
                }while (cursor.moveToNext());
            }
            cursor.close();
            return audioIds;
        }
        return new String[0];
    }

    public abstract List<Genre> getGenres();

    public abstract Genre getGenreItemWithId(long id);

    public abstract Genre getGenreItemWithName(String name);
}
