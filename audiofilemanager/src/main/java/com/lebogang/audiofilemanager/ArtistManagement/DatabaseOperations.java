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
import java.util.List;

abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";

    /**
     * Get artists from device
     * @param context used to get the resolver
     * @return list of artists
     * */
    protected List<Artist> queryItems(Context context){
        List<Artist> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getArtistProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String numOfAlbums = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                String numOfSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                list.add(new Artist(artistId, title, numOfAlbums, numOfSongs));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Call this method before you queryItems/getArtistItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Artists#ARTIST + " ASC"}
     * */
    @Override
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    /**
     * Get artist from device
     * @param context is required to get resolver
     * @param id of the required artist item
     * @return list of artists
     * */
    protected Artist queryItemID(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(
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
     * @param context is required to get resolver
     * @param name of the required artist item
     * @return list of artists
     * */
    protected Artist queryItemName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
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
     * Get the total number of items
     * */
    public int getItemCount(Context context){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,new String[]{MediaStore.Audio.Artists._ID}, null,null, null);
        if (cursor != null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public abstract List<Artist> getArtists();

    public abstract Artist getArtistItemWithId(long id);

    public abstract Artist getArtistItemWithName(String name);
}
