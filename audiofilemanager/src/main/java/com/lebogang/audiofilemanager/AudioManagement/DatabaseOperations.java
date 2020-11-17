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

package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Audio;

import java.util.ArrayList;
import java.util.List;

abstract class DatabaseOperations extends DatabaseScheme{

    private String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

    /**
     * Get audio from device
     * @param context used to get the resolver
     * @return list of audio
     * */
    protected List<Audio> query(Context context){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), null, null, sortOrder);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific albumId
     * @param context used to get the resolver
     * @param id of the album
     * @return list of audio
     * */
    protected List<Audio> queryAlbumSongsWithID(Context context, long id){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ALBUM_ID + "=?", new String[]{Long.toString(id)}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific album name
     * @param context used to get the resolver
     * @param name of the album
     * @return list of audio
     * */
    protected List<Audio> queryAlbumSongsWithName(Context context, String name){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ALBUM + "=?", new String[]{name}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific artistId
     * @param context used to get the resolver
     * @param id of the artist
     * @return list of audio
     * */
    protected List<Audio> queryArtistSongsWithID(Context context, long id){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{Long.toString(id)}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific artist name
     * @param context used to get the resolver
     * @param name of the artist
     * @return list of audio
     * */
    protected List<Audio> queryArtistSongsWithName(Context context, String name){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ARTIST + "=?", new String[]{name}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific ids
     * @param context used to get the resolver
     * @param audioIds of the audio
     * @return list of audio
     * */
    protected List<Audio> queryWithIdArray(Context context, String[] audioIds){
        List<Audio> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), super.produceWhereClauses(audioIds),null, null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                list.add(new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Get audio from device with a specific id
     * @param context used to get the resolver
     * @param id of the audio
     * @return audio, null if not found
     * */
    protected Audio queryWithId(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media._ID + "=?", new String[]{Long.toString(id)}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
            String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
            String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
            Audio audio = new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                    ,albumTitle,artistTitle,composer,releaseYear,trackNumber);
            cursor.close();
            return audio;
        }
        return null;
    }

    /**
     * Get audio from device with a specific name
     * @param context used to get the resolver
     * @param name of the audio
     * @return audio, null if not found
     * */
    protected Audio queryWithName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.TITLE + "=?", new String[]{name}
                , null);
        if (cursor != null && cursor.moveToFirst()){
            long mediaId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
            String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
            String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
            Audio audio = new Audio(mediaId,albumId,artistId,audioDuration,audioSize,dateAdded,title
                    ,albumTitle,artistTitle,composer,releaseYear,trackNumber);
            cursor.close();
            return audio;
        }
        return null;
    }

    /**
     * Update an audio item
     * @param context used to get the resolver
     * @param id of of the audio
     * @param values : new values to insert
     * @return true if successful
     * */
    protected boolean update(Context context, long id, ContentValues values){
        int updateResult = context.getApplicationContext().getContentResolver().update(super.getMediaStoreUri(),values
                , MediaStore.Audio.Media._ID + "=?", new String[]{Long.toString(id)});
        return updateResult != 0;
    }

    /**
     * Delete an audio item
     * @param context used to get the resolver
     * @param id of of the audio
     * @return true if successful
     * */
    protected boolean delete(Context context, long id){
        int updateResult = context.getApplicationContext().getContentResolver().delete(super.getMediaStoreUri(),
                MediaStore.Audio.Media._ID + "=?", new String[]{Long.toString(id)});
        return updateResult != 0;
    }

    /**
     * Get the total number of items
     * */
    public int getItemCount(Context context){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,new String[]{MediaStore.Audio.Media._ID}, null,null, null);
        if (cursor != null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    /**
     * Call this method before you queryItems/getAlbumItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Media#TITLE + " ASC"}
     * */
    @Override
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    public abstract List<Audio> getAudio();
    public abstract Audio getAudioItemWithId(long id);
    public abstract Audio getAudioItemWithName(String name);
    public abstract List<Audio> getAlbumAudioWithID(long id);
    public abstract List<Audio> getAlbumAudioWithName(String name);
    public abstract List<Audio> getArtistAudioWithID(long id);
    public abstract List<Audio> getArtistAudioWithName(String name);
    public abstract List<Audio> getArtistAudioWithIDArray(String[] audioIds);
    public abstract boolean updateAudio(long id, ContentValues values);
    public abstract boolean deleteAudio(long id);
}
