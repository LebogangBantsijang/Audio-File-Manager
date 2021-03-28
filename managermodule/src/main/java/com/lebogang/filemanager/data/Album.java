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

public class Album {
    private final long id;
    private final String albumName;
    private final String artist;
    private final String firstYear;
    private final String lastYear;
    private final int numberOfSongs;
    private final Uri albumArtUri;
    private final Uri contentUri;

    private Album(long id, String albumName, String artist, String firstYear, String lastYear
            , int numberOfSongs, Uri albumArtUri, Uri contentUri) {
        this.id = id;
        this.albumName = albumName;
        this.artist = artist;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
        this.numberOfSongs = numberOfSongs;
        this.albumArtUri = albumArtUri;
        this.contentUri = contentUri;
    }

    public long getId() {
        return id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public String getFirstYear() {
        return firstYear;
    }

    public String getLastYear() {
        return lastYear;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public Uri getAlbumArt() {
        return albumArtUri;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public static class Builder{
        private long id;
        private String albumName;
        private String artist;
        private String firstYear;
        private String lastYear;
        private int numberOfSongs;
        private Uri albumArtUri;
        private Uri contentUri;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAlbumName(String albumName) {
            this.albumName = albumName;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setFirstYear(String firstYear) {
            this.firstYear = firstYear;
            return this;
        }

        public Builder setLastYear(String lastYear) {
            this.lastYear = lastYear;
            return this;
        }

        public Builder setNumberOfSongs(int numberOfSongs) {
            this.numberOfSongs = numberOfSongs;
            return this;
        }

        public Builder setAlbumArtUri(Uri albumArtUri) {
            this.albumArtUri = albumArtUri;
            return this;
        }

        public Builder setContentUri(Uri contentUri) {
            this.contentUri = contentUri;
            return this;
        }

        public Album build(){
            return new Album(id,albumName
                    ,artist,firstYear,lastYear,numberOfSongs
                    , albumArtUri, contentUri);
        }
    }
}
