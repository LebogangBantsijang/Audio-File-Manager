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

import android.net.Uri;
import android.provider.MediaStore;

abstract class DatabaseScheme {

    private final String[] audioProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.YEAR
    };

    protected String[] getAudioProjection() {
        return audioProjection;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    protected String produceWhereClauses(String[] strings){
        if (strings.length > 0){
            String temp = "_id IN (" + strings[0];
            for (int x = 1; x < strings.length; x++){
                temp += ", " + strings[x];
            }
            temp += ")";
            return temp;
        }
        return "_id IN (0)";
    }
}
