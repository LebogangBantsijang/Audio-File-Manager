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

package com.lebogang.audiofilemanager.ArtistManagement;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Artist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
    private final Context context;
    private final List<Artist> artistList = new ArrayList<>();

    public DatabaseOperations(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get artists from device
     * @return list of artists
     * */
    public List<Artist> getArtists(){
        artistList.clear();
        Cursor cursor = context.getContentResolver()
                .query(super.getMediaStoreUri(),super.getArtistProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String numOfAlbums = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                String numOfSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                artistList.add(new Artist(artistId, title, numOfAlbums, numOfSongs));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return artistList;
    }

    /**
     * Some artists can have the same name with different IDs. This will return a list without the
     * duplicates. However, the song count specified in the artist and the actual song count may not match
     * @return filtered list.
     * */
    public List<Artist> getArtistGroupedByName(){
        List<Artist> artists = getArtists();
        LinkedHashMap<String, Artist> linkedHashMap = new LinkedHashMap<>();
        for (Artist artist:artists){
            if (!linkedHashMap.containsKey(artist.getTitle()))
                linkedHashMap.put(artist.getTitle(), artist);
        }
        return new ArrayList<>(linkedHashMap.values());
    }

    /**
     * Get artist from device
     * @param id of the required artist item
     * @return list of artists
     * */
    public Artist getArtistsById(long id){
        Cursor cursor = context.getContentResolver().query(
                super.getMediaStoreUri(),super.getArtistProjection(),
                MediaStore.Audio.Artists._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            String numOfAlbums = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            String numOfSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            Artist artist = new Artist(artistId, title, numOfAlbums, numOfSongs);
            cursor.close();
            return artist;
        }
        return null;
    }

    /**
     * Get artist from device
     * @param name of the required artist item
     * @return list of artists
     * */
    public Artist getArtistByName(String name){
        Cursor cursor = context.getContentResolver().query(super.getMediaStoreUri()
                ,super.getArtistProjection(),MediaStore.Audio.Artists.ARTIST + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            String numOfAlbums = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            String numOfSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            Artist artist = new Artist(artistId, title, numOfAlbums, numOfSongs);
            cursor.close();
            return artist;
        }
        return null;
    }

    /**
     * Call this method before you queryItems/getArtistItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Artists#ARTIST + " ASC"}
     * */
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }
}
