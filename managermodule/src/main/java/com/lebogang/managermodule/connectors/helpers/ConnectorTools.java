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

package com.lebogang.managermodule.connectors.helpers;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.provider.MediaStore;

import com.lebogang.managermodule.Models.Media;

public class ConnectorTools {
    @SuppressLint("InlinedApi")
    public static final String[] AUDIO_PROJECTION = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DATE_TAKEN,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK
    };
    public static final String[] ALBUM_PROJECTION = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.FIRST_YEAR,
            MediaStore.Audio.Albums.LAST_YEAR,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST
    };
    public static final String[] ARTIST_PROJECTION = {
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
    };
    public static final String[] GENRE_PROJECTION = {
            MediaStore.Audio.Genres._ID,
            MediaStore.Audio.Genres.NAME
    };
    public static final String[] GENRE_AUDIO_PROJECTION = {
            MediaStore.Audio.Genres.Members.GENRE_ID,
            MediaStore.Audio.Genres.Members.AUDIO_ID
    };
    public static final Uri AUDIO_EXTERNAL_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri ALBUM_EXTERNAL_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    public static final Uri ARTIST_EXTERNAL_URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    public static final Uri GENRE_EXTERNAL_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    public static Uri getGenreAudioExternalUri(long id){
        return MediaStore.Audio.Genres.Members.getContentUri("external",id);
    }

    public static String DEFAULT_AUDIO_SORT_ORDER = MediaStore.Audio.Media.TITLE + " ASC";
    public static String DEFAULT_ALBUM_SORT_ORDER = MediaStore.Audio.Albums.ALBUM + " ASC";
    public static String DEFAULT_ARTIST_SORT_ORDER = MediaStore.Audio.Artists.ARTIST + " ASC";
    public static String DEFAULT_GENRE_SORT_ORDER = MediaStore.Audio.Genres.NAME + " ASC";
}
