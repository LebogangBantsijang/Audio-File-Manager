package com.lebogang.audiofilemanager.Models;

import android.os.Parcel;

public class Artist extends Media {
    private final long artistId;
    private final String artist;
    private final String numberOfAlbums;
    private final String numberOfTracks;

    public Artist(long artistId, String artist, String numberOfAlbums, String numberOfTracks) {
        this.artistId = artistId;
        this.artist = artist;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfTracks = numberOfTracks;
    }

    public String getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public String getNumberOfTracks() {
        return numberOfTracks;
    }

    @Override
    public long getId() {
        return artistId;
    }

    @Override
    public String getTitle() {
        return artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.artistId);
        dest.writeString(this.artist);
        dest.writeString(this.numberOfAlbums);
        dest.writeString(this.numberOfTracks);
    }

    protected Artist(Parcel in) {
        this.artistId = in.readLong();
        this.artist = in.readString();
        this.numberOfAlbums = in.readString();
        this.numberOfTracks = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
