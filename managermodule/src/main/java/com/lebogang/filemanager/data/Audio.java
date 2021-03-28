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

public class Audio {
    private final long id;
    private final long albumId;
    private final long artistId;
    private final long duration;
    private final long size;
    private final long dateModified;
    private final String title;
    private final String albumTitle;
    private final String artistTitle;
    private final String composer;
    private final String releaseYear;
    private final String trackNumber;
    private final Uri albumArtUri;
    private final Uri contentUri;

    private Audio(long id, long albumId, long artistId, long duration, long size, long dateModified
            , String title, String albumTitle, String artistTitle, String composer, String releaseYear
            , String trackNumber, Uri albumArtUri
            ,Uri contentUri) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.duration = duration;
        this.size = size;
        this.dateModified = dateModified;
        this.title = title;
        this.albumTitle = albumTitle;
        this.artistTitle = artistTitle;
        this.composer = composer;
        this.releaseYear = releaseYear;
        this.trackNumber = trackNumber;
        this.albumArtUri = albumArtUri;
        this.contentUri = contentUri;
    }

    public long getId() {
        return id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public long getDuration() {
        return duration;
    }

    public long getSize() {
        return size;
    }

    public long getDateModified() {
        return dateModified;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

    public String getComposer() {
        return composer;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public Uri getContentUri() {
        return contentUri;
    }
    
    public static class Builder{
        private long id;
        private long albumId;
        private long artistId;
        private long duration;
        private long size;
        private long dateModified;
        private String title;
        private String albumTitle;
        private String artistTitle;
        private String composer;
        private String releaseYear;
        private String trackNumber;
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

        public Builder setArtistId(long artistId) {
            this.artistId = artistId;
            return this;
        }

        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder setSize(long size) {
            this.size = size;
            return this;
        }

        public Builder setDateModified(long dateModified) {
            this.dateModified = dateModified;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
            return this;
        }

        public Builder setArtistTitle(String artistTitle) {
            this.artistTitle = artistTitle;
            return this;
        }

        public Builder setComposer(String composer) {
            this.composer = composer;
            return this;
        }

        public Builder setReleaseYear(String releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder setTrackNumber(String trackNumber) {
            this.trackNumber = trackNumber;
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

        public Audio build() throws NullPointerException{
            return new Audio(id,albumId,artistId,duration,size,dateModified
                    ,title,albumTitle,artistTitle,composer,releaseYear,trackNumber
                    ,albumArtUri, contentUri);
        }
    }
}
