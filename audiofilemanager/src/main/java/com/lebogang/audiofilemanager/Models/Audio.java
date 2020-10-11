package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

public class Audio extends Media{
    private final long id;
    private final long albumId;
    private final long artistId;
    private final long audioDuration;
    private final long audioSize;
    private final long dateAdded;
    private final String title;
    private final String albumTitle;
    private final String artistTitle;
    private final String composer;
    private final String releaseYear;
    private final String trackNumber;

    public Audio(long id, long albumId, long artistId, long audioDuration, long audioSize,
                 long dateAdded, String title, String albumTitle, String artistTitle,
                 String composer, String releaseYear, String trackNumber) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.audioDuration = audioDuration;
        this.audioSize = audioSize;
        this.dateAdded = dateAdded;
        this.title = title;
        this.albumTitle = albumTitle;
        this.artistTitle = artistTitle;
        this.composer = composer;
        this.releaseYear = releaseYear;
        this.trackNumber = trackNumber;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public long getAudioDuration() {
        return audioDuration;
    }

    public long getAudioSize() {
        return audioSize;
    }

    public long getDateAdded() {
        return dateAdded;
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
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public Uri getUri() {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.albumId);
        dest.writeLong(this.artistId);
        dest.writeLong(this.audioDuration);
        dest.writeLong(this.audioSize);
        dest.writeLong(this.dateAdded);
        dest.writeString(this.title);
        dest.writeString(this.albumTitle);
        dest.writeString(this.artistTitle);
        dest.writeString(this.composer);
        dest.writeString(this.releaseYear);
        dest.writeString(this.trackNumber);
    }

    protected Audio(Parcel in) {
        this.id = in.readLong();
        this.albumId = in.readLong();
        this.artistId = in.readLong();
        this.audioDuration = in.readLong();
        this.audioSize = in.readLong();
        this.dateAdded = in.readLong();
        this.title = in.readString();
        this.albumTitle = in.readString();
        this.artistTitle = in.readString();
        this.composer = in.readString();
        this.releaseYear = in.readString();
        this.trackNumber = in.readString();
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
}
