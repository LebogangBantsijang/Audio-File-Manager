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

package com.lebogang.audiofilemanager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Audio;

/**
 * Use the class to build content values for updating purposes
 * e.g
 * new AudioValuesBuilder(audio).setTitle(title).build();
 *
 * */

public final  class AudioValuesBuilder {
    private final ContentValues contentValues;

    public AudioValuesBuilder(Audio audio) {
        contentValues = new ContentValues();
        contentValues.put(MediaStore.Audio.Media.TITLE,audio.getTitle());
        contentValues.put(MediaStore.Audio.Media.ALBUM,audio.getAlbumTitle());
        contentValues.put(MediaStore.Audio.Media.ARTIST,audio.getArtistTitle());
        contentValues.put(MediaStore.Audio.Media.YEAR,audio.getReleaseYear());
        contentValues.put(MediaStore.Audio.Media.COMPOSER,audio.getComposer());
        contentValues.put(MediaStore.Audio.Media.TRACK,audio.getTrackNumber());
    }

    public AudioValuesBuilder setTitle(String title){
        contentValues.put(MediaStore.Audio.Media.TITLE,title);
        return this;
    }

    public AudioValuesBuilder setAlbumTitle(String title){
        contentValues.put(MediaStore.Audio.Media.ALBUM,title);
        return this;
    }

    public AudioValuesBuilder setArtistTitle(String title){
        contentValues.put(MediaStore.Audio.Media.ARTIST,title);
        return this;
    }

    public AudioValuesBuilder setYear(String year){
        contentValues.put(MediaStore.Audio.Media.YEAR,year);
        return this;
    }

    public AudioValuesBuilder setComposer(String composer){
        contentValues.put(MediaStore.Audio.Media.COMPOSER,composer);
        return this;
    }

    public AudioValuesBuilder setTrackNumber(String trackNumber){
        contentValues.put(MediaStore.Audio.Media.TRACK,trackNumber);
        return this;
    }

    public ContentValues build(){
        return contentValues;
    }
}
