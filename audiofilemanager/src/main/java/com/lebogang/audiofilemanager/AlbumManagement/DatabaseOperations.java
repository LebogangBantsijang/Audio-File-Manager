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
import android.database.Cursor;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Album;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    private final Context context;
    private final List<Album> albumList = new ArrayList<>();

    public DatabaseOperations(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get albums from device
     * @return list of albums
     * */
    public List<Album> getAlbums(){
        albumList.clear();
        Cursor cursor = context.getContentResolver()
                .query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
                String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                albumList.add(new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return albumList;
    }

    /**
     * Some albums can have the same name with different IDs. This will return a list without the
     * duplicates. However, the song count specified in the album and the actual song count may not match
     * @return filtered list.
     * */
    public List<Album> getAlbumsGroupedByName(){
        List<Album> albumList = getAlbums();
        LinkedHashMap<String, Album> linkedHashMap = new LinkedHashMap<>();
        for (Album album:albumList){
            if (!linkedHashMap.containsKey(album.getTitle()))
                linkedHashMap.put(album.getTitle(), album);
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    /**
     * Get albums from device
     * @param id of the required album item
     * @return list of alums
     * */
    public Album getAlbumById(long id){
        albumList.clear();
        Cursor cursor = context.getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,MediaStore.Audio.Albums._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * Get albums from device
     * @param name of the required album item
     * @return list of alums
     * */
    public Album getAlbumByName(String name){
        Cursor cursor = context.getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,MediaStore.Audio.Albums.ALBUM + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * Call this method before you queryItems/getAlbumItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Albums#ALBUM + " ASC"}
     * */
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

}
