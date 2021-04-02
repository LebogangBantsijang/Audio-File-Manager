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

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.lebogang.filemanager.connectors.helpers.AudioDatabaseInterface;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.data.Audio;
import com.lebogang.filemanager.data.helpers.UriHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.COMPOSER;
import static android.provider.MediaStore.Audio.Media.DATE_MODIFIED;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.IS_MUSIC;
import static android.provider.MediaStore.Audio.Media.SIZE;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.YEAR;
import static android.provider.MediaStore.Audio.Media._ID;

public class AudioConnector implements AudioDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;

    public AudioConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudio() {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , DURATION + " >=? AND " + IS_MUSIC, new String[]{Long.toString(ConnectorTools.DEFAULT_AUDIO_DURATION_FILTER)}, ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudioAboveDuration(long duration) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , DURATION + " >=? AND " + IS_MUSIC, new String[]{Long.toString(ConnectorTools.DEFAULT_AUDIO_DURATION_FILTER)}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudio(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , _ID + "=? AND" + DURATION + " >=? AND " + IS_MUSIC, new String[]{Long.toString(id),Long.toString(ConnectorTools.DEFAULT_AUDIO_DURATION_FILTER)}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudio(@NonNull String name) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , TITLE + "=? AND" + DURATION + " >=? AND " + IS_MUSIC, new String[]{name,Long.toString(ConnectorTools.DEFAULT_AUDIO_DURATION_FILTER)}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudio(@NonNull Uri uri) {
        Cursor cursor = contentResolver.query(uri, ConnectorTools.AUDIO_PROJECTION
                , IS_MUSIC, null, ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudio(@NonNull String[] audioIds) {
        String selection = "";
        for (int x = 0; x < audioIds.length; x++){
            if (x == (audioIds.length-1)){
                selection += (_ID + " =?");
                break;
            }
            selection += (_ID + " =? OR ");
        }
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI,ConnectorTools.AUDIO_PROJECTION
                , selection,audioIds, ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public int deleteAudio(long id) {
        return contentResolver.delete(ConnectorTools.AUDIO_EXTERNAL_URI, _ID + "=?", new String[]{Long.toString(id)});
    }

    @Override
    public int updateAudio(long id, ContentValues values) {
        return contentResolver.update(ConnectorTools.AUDIO_EXTERNAL_URI, values
                , _ID + "=?",new String[]{Long.toString(id)});
    }

    public void registerObserver(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.AUDIO_EXTERNAL_URI, true, contentObserver);
    }

    public void unregisterObserver() {
        contentResolver.unregisterContentObserver(contentObserver);
    }

    @SuppressLint("InlinedApi")
    private List<Audio> iterateCursor(Cursor cursor){
        if (cursor == null){
            return Collections.emptyList();
        }
        List<Audio> audioList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(_ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(ALBUM_ID));
                Audio audio = new Audio.Builder()
                        .setId(id)
                        .setAlbumId(albumId)
                        .setArtistId(cursor.getLong(cursor.getColumnIndex(ARTIST_ID)))
                        .setDuration(cursor.getLong(cursor.getColumnIndex(DURATION)))
                        .setDateModified(cursor.getLong(cursor.getColumnIndex(DATE_MODIFIED)))
                        .setTitle(cursor.getString(cursor.getColumnIndex(TITLE)))
                        .setAlbumTitle(cursor.getString(cursor.getColumnIndex(ALBUM)))
                        .setArtistTitle(cursor.getString(cursor.getColumnIndex(ARTIST)))
                        .setComposer(cursor.getString(cursor.getColumnIndex(COMPOSER)))
                        .setReleaseYear(cursor.getString(cursor.getColumnIndex(YEAR)))
                        .setTrackNumber(cursor.getString(cursor.getColumnIndex(TRACK)))
                        .setSize(cursor.getLong(cursor.getColumnIndex(SIZE)))
                        .setAlbumArtUri(UriHelper.createAlbumArtUri(albumId))
                        .setContentUri(UriHelper.createContentUri(ConnectorTools.AUDIO_EXTERNAL_URI, id))
                        .build();
                audioList.add(audio);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return audioList;
    }
}
