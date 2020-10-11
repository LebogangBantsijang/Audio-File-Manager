package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;

public class Album extends Media {
    private final String album;
    private final long albumId;
    private final String artist;
    private final long artistId;
    private final String firstYear;
    private final String lastYear;
    private final String numSongs;
    private final String numSongsByArtist;

    public Album(String album, long albumId, String artist, long artistId, String firstYear
            , String lastYear, String numSongs, String numSongsByArtist) {
        this.album = album;
        this.albumId = albumId;
        this.artist = artist;
        this.artistId = artistId;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
        this.numSongs = numSongs;
        this.numSongsByArtist = numSongsByArtist;
    }


    public String getArtist() {
        return artist;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getFirstYear() {
        return firstYear;
    }

    public String getLastYear() {
        return lastYear;
    }

    public String getNumSongs() {
        return numSongs;
    }

    public String getNumSongsByArtist() {
        return numSongsByArtist;
    }

    public Uri getAlbumArtUri() {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    @Override
    public long getId() {
        return albumId;
    }

    @Override
    public String getTitle() {
        return album;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album);
        dest.writeLong(this.albumId);
        dest.writeString(this.artist);
        dest.writeLong(this.artistId);
        dest.writeString(this.firstYear);
        dest.writeString(this.lastYear);
        dest.writeString(this.numSongs);
        dest.writeString(this.numSongsByArtist);
    }

    protected Album(Parcel in) {
        this.album = in.readString();
        this.albumId = in.readLong();
        this.artist = in.readString();
        this.artistId = in.readLong();
        this.firstYear = in.readString();
        this.lastYear = in.readString();
        this.numSongs = in.readString();
        this.numSongsByArtist = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
