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
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lebogang.audiofilemanager.Models.Audio;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseOperations extends DatabaseScheme{

    private String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
    private long duration = 0;
    private final List<Audio> audioList = new ArrayList<>();
    private final Context context;

    public DatabaseOperations(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get audio from device. Audio files will be filtered based the duration attribute.
     * Set duration before calling this method or everything will be selected.
     * Same applies to the sort order
     * @return {@link Audio}
     * */
    public List<Audio> getAudio(){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.DURATION + ">= ?"
                , new String[]{Long.toString(duration)}, sortOrder);
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
                audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Get audio from device. Audio files will be filtered based the duration attribute.
     * Set duration from calling this method or everything will be selected.
     * Same applies to the sort order
     * @param  audioIdList: list of audioIds
     * @return {@link Audio}
     * */
    public List<Audio> getAudio( List<Long> audioIdList){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.DURATION + ">=?"
                , new String[]{Long.toString(duration)}, sortOrder);
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
                if (audioIdList.contains(id))
                    audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                            ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Get audio from device.
     * @param  audioId: audioId
     * @return {@link Audio}
     * */
    @Nullable
    public Audio getAudio( long audioId){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media._ID + "=?"
                , new String[]{Long.toString(audioId)}, null);
        if (cursor != null && cursor.moveToFirst()){
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
            Audio audio = new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                    ,albumTitle,artistTitle,composer,releaseYear,trackNumber);
            cursor.close();
            return audio;
        }
        return null;
    }

    /**
     * Get audio from device.
     * @param  audioAlbumId: album Id
     * @return {@link Audio}
     * */
    public List<Audio> getAudioByAlbumId( long audioAlbumId){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ALBUM_ID + "=?"
                , new String[]{Long.toString(audioAlbumId)}, MediaStore.Audio.Media.TRACK);
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
                audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Get audio from device.
     * @param  audioArtistId: album Id
     * @return {@link Audio}
     * */
    public List<Audio> getAudioByArtistId( long audioArtistId){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ARTIST_ID + "=?"
                , new String[]{Long.toString(audioArtistId)}, MediaStore.Audio.Media.TRACK);
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
                audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Get audio from device.
     * @param  audioAlbumName: album name
     * @return {@link Audio}
     * */
    public List<Audio> getAudioByAlbumName( String audioAlbumName){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ALBUM + "=?"
                , new String[]{audioAlbumName}, MediaStore.Audio.Media.TRACK);
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
                audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Get audio from device.
     * @param  artistAlbumName: album Id
     * @return {@link Audio}
     * */
    public List<Audio> getAudioByArtistName( String artistAlbumName){
        audioList.clear();
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,super.getAudioProjection(), MediaStore.Audio.Media.ARTIST + "=?"
                , new String[]{artistAlbumName}, MediaStore.Audio.Media.TRACK);
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
                audioList.add(new Audio(id,albumId,artistId,audioDuration,audioSize,dateAdded,title
                        ,albumTitle,artistTitle,composer,releaseYear,trackNumber));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return audioList;
    }

    /**
     * Update audio
     * @param audioId: audio id
     * @param values: values to update. see {@link com.lebogang.audiofilemanager.AudioValuesBuilder}
     * @return true if successful, false otherwise
     *
     */
    public boolean updateAudio(Context context, long audioId, ContentValues values){
        int updateResult = context.getApplicationContext().getContentResolver().update(getMediaStoreUri(),values
                , MediaStore.Audio.Media._ID + "=?", new String[]{Long.toString(audioId)});
        return updateResult > 0;
    }

    /**
     * Delete audio from media store database
     * @param audioId: audio id
     * @return true if successful, false otherwise
     *
     */
    public boolean deleteAudio(Context context, long audioId){
        int deleteResults = context.getApplicationContext().getContentResolver().delete(getMediaStoreUri(),
                MediaStore.Audio.Media._ID + "=?", new String[]{Long.toString(audioId)});
        return deleteResults > 0;
    }

    /**
     * Call this method before you queryItems/getAlbumItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Media#TITLE + " ASC"}
     * */
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
