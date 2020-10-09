package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

public class AudioMediaItem extends MediaItem {
    private final long mediaId;
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

    public AudioMediaItem(long mediaId, long albumId, long artistId, long audioDuration, long audioSize, long dateAdded, String title, String albumTitle, String artistTitle, String composer, String releaseYear, String trackNumber) {
        this.mediaId = mediaId;
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

    @Override
    public Uri getContentUri() {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaId);
    }

    @Override
    public long getMediaId() {
        return mediaId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubTitle() {
        return artistTitle + "-" + albumTitle;
    }

    @Override
    public String getTrackCount() {
        return "1";
    }

    @Override
    public long getDuration() {
        return audioDuration;
    }

    @Override
    public int getMediaItemType() {
        return MediaItem.MEDIA_TYPE_AUDIO;
    }

    /**
     * Override this function and create custom URI
     * */
    @Override
    public Uri getAlbumArtUri() {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    @Override
    public int describeContents() {
        return 0;//No Special Objects in here
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getArtistId() {
        return artistId;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mediaId);
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

    protected AudioMediaItem(Parcel in) {
        this.mediaId = in.readLong();
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

    public static final Creator<AudioMediaItem> CREATOR = new Creator<AudioMediaItem>() {
        @Override
        public AudioMediaItem createFromParcel(Parcel source) {
            return new AudioMediaItem(source);
        }

        @Override
        public AudioMediaItem[] newArray(int size) {
            return new AudioMediaItem[size];
        }
    };
}
