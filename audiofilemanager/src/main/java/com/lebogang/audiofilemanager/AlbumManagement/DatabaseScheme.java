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

import android.net.Uri;
import android.provider.MediaStore;

public abstract class DatabaseScheme {
    private final String[] albumProjection = {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR,
            MediaStore.Audio.Albums.LAST_YEAR
    };

    protected String[] getAlbumProjection() {
        return albumProjection;
    }

    protected Uri getMediaStoreUri(){
        return MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    }

    public abstract void setSortOrder(String order);
}
