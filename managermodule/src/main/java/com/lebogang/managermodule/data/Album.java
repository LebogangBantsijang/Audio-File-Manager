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

package com.lebogang.managermodule.data;

import android.net.Uri;

public class Album {
    private final long id;
    private final long albumId;
    private final String albumName;
    private final String artist;
    private final String firstYear;
    private final String lastYear;
    private final int numberOfSongs;
    private final int numberOfSongsForArtist;
    private final Uri albumArtUri;
    private final Uri contentUri;

    private Album(long id, long albumId, String albumName, String artist
            , String firstYear, String lastYear, int numberOfSongs, int numberOfSongsForArtist
            , Uri albumArtUri, Uri contentUri) {
        this.id = id;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artist = artist;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
        this.numberOfSongs = numberOfSongs;
        this.numberOfSongsForArtist = numberOfSongsForArtist;
        this.albumArtUri = albumArtUri;
        this.contentUri = contentUri;
    }

    public long getId() {
        return id;
    }

    public long getAlbumId() {
        return albumId;
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

    public int getNumberOfSongsForArtist() {
        return numberOfSongsForArtist;
    }

    public Uri getAlbumArt() {
        return albumArtUri;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public static class Builder{
        private long id;
        private long albumId;
        private String albumName;
        private String artist;
        private String firstYear;
        private String lastYear;
        private int numberOfSongs;
        private int numberOfSongsForArtist;
        private Uri albumArtUri;
        private Uri contentUri;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAlbumId(long albumId) {
            this.albumId = albumId;
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

        public Builder setNumberOfSongsForArtist(int numberOfSongsForArtist) {
            this.numberOfSongsForArtist = numberOfSongsForArtist;
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
            return new Album(id,albumId,albumName
                    ,artist,firstYear,lastYear,numberOfSongs
                    ,numberOfSongsForArtist, albumArtUri, contentUri);
        }
    }
}
