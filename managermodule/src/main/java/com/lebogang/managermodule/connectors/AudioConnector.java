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

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;

import androidx.annotation.NonNull;

import com.lebogang.managermodule.connectors.helpers.AudioDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.ConnectorTools;
import com.lebogang.managermodule.data.Audio;
import com.lebogang.managermodule.data.helpers.UriHelper;

import java.util.Collections;
import java.util.List;

import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.IS_MUSIC;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.YEAR;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.COMPOSER;
import static android.provider.MediaStore.Audio.Media.DATE_ADDED;
import static android.provider.MediaStore.Audio.Media.DATE_MODIFIED;
import static android.provider.MediaStore.Audio.Media.DATE_TAKEN;
import static android.provider.MediaStore.Audio.Media.DISPLAY_NAME;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TITLE;

public class AudioConnector implements AudioDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;

    public AudioConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Audio> getAudio() {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , null, new String[]{IS_MUSIC}, ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    @SuppressLint("InlinedApi")
    public List<Audio> getAudioAboveDuration(long duration) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , DURATION + ">?", new String[]{Long.toString(duration), IS_MUSIC}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Audio> getAudio(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , Media._ID + "=?", new String[]{Long.toString(id), IS_MUSIC}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Audio> getAudio(@NonNull String name) {
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI, ConnectorTools.AUDIO_PROJECTION
                , TITLE + "=?", new String[]{name, IS_MUSIC}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Audio> getAudio(@NonNull Uri uri) {
        Cursor cursor = contentResolver.query(uri, ConnectorTools.AUDIO_PROJECTION
                , null, new String[]{IS_MUSIC}
                , ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Audio> getAudio(@NonNull String[] audioIds) {
        String selection = "";
        for (int x = 0; x < audioIds.length; x++){
            if (x == (1-audioIds.length)){
                selection += (Media._ID + " =?");
                break;
            }
            selection += (Media._ID + " =? OR ");
        }
        Cursor cursor = contentResolver.query(ConnectorTools.AUDIO_EXTERNAL_URI,ConnectorTools.AUDIO_PROJECTION
                , selection,audioIds, ConnectorTools.DEFAULT_AUDIO_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public int deleteAudio(long id) {
        return contentResolver.delete(ConnectorTools.AUDIO_EXTERNAL_URI,Media._ID + "=?", new String[]{Long.toString(id)});
    }

    @Override
    public int updateAudio(long id, ContentValues values) {
        return contentResolver.update(ConnectorTools.AUDIO_EXTERNAL_URI, values
                ,Media._ID + "=?",new String[]{Long.toString(id)});
    }

    @Override
    public void observeAudioChanges(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.AUDIO_EXTERNAL_URI, true, contentObserver);
    }

    @Override
    public void stopAudioObserving() {
        contentResolver.unregisterContentObserver(contentObserver);
    }

    @SuppressLint("InlinedApi")
    private List<Audio> iterateCursor(Cursor cursor){
        List<Audio> audioList = Collections.emptyList();
        if (cursor == null){
            return audioList;
        }
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(Media.ALBUM_ID));
                Audio audio = new Audio.Builder()
                        .setId(id)
                        .setAlbumId(albumId)
                        .setArtistId(cursor.getLong(cursor.getColumnIndex(ARTIST_ID)))
                        .setDuration(cursor.getLong(cursor.getColumnIndex(DURATION)))
                        .setDateAdded(cursor.getLong(cursor.getColumnIndex(DATE_ADDED)))
                        .setDateModified(cursor.getLong(cursor.getColumnIndex(DATE_MODIFIED)))
                        .setDateTaken(cursor.getLong(cursor.getColumnIndex(DATE_TAKEN)))
                        .setTitle(cursor.getString(cursor.getColumnIndex(TITLE)))
                        .setDisplayName(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)))
                        .setAlbumTitle(cursor.getString(cursor.getColumnIndex(ALBUM)))
                        .setArtistTitle(cursor.getString(cursor.getColumnIndex(ARTIST)))
                        .setComposer(cursor.getString(cursor.getColumnIndex(COMPOSER)))
                        .setReleaseYear(cursor.getString(cursor.getColumnIndex(YEAR)))
                        .setTrackNumber(cursor.getString(cursor.getColumnIndex(TRACK)))
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
