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

package com.lebogang.filemanager.data;

import android.net.Uri;

public class Artist {
    private final long id;
    private final String artistName;
    private final int numberOfAlbums;
    private final int numberOfSongs;
    private final Uri contentUri;

    private Artist(long id, String artistName, int numberOfAlbums, int numberOfSongs, Uri contentUri) {
        this.id = id;
        this.artistName = artistName;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfSongs = numberOfSongs;
        this.contentUri = contentUri;
    }

    public long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public static class Builder{
        private long id;
        private String artistName;
        private int numberOfAlbums;
        private int numberOfSongs;
        private Uri contentUri;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder setNumberOfAlbums(int numberOfAlbums) {
            this.numberOfAlbums = numberOfAlbums;
            return this;
        }

        public Builder setNumberOfSongs(int numberOfSongs) {
            this.numberOfSongs = numberOfSongs;
            return this;
        }

        public Builder setContentUri(Uri contentUri) {
            this.contentUri = contentUri;
            return this;
        }

        public Artist build(){
            return new Artist(id,artistName,numberOfAlbums,numberOfSongs,contentUri);
        }
    }
}
