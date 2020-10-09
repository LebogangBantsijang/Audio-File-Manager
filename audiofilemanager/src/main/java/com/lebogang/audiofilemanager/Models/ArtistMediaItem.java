package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

public class ArtistMediaItem extends MediaItem {
    private final long mediaId;
    private final long lastAlbumIdFound;
    private final long totalAudioDuration;
    private final String title;
    private final String trackCount;
    private final String albumCount;
    private final String albumTitles;

    public ArtistMediaItem(long mediaId, long lastAlbumIdFound, long totalAudioDuration, String title, String trackCount, String albumCount, String albumTitles) {
        this.mediaId = mediaId;
        this.lastAlbumIdFound = lastAlbumIdFound;
        this.totalAudioDuration = totalAudioDuration;
        this.title = title;
        this.trackCount = trackCount;
        this.albumCount = albumCount;
        this.albumTitles = albumTitles;
    }

    @Override
    public Uri getContentUri() {
        return ContentUris.withAppendedId(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, mediaId);
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
        return albumTitles;
    }

    @Override
    public String getTrackCount() {
        return trackCount;
    }

    @Override
    public long getDuration() {
        return totalAudioDuration;
    }

    @Override
    public int getMediaItemType() {
        return MediaItem.MEDIA_TYPE_ARTIST;
    }

    /**
     * Override this function and create custom URI
     * */
    @Override
    public Uri getAlbumArtUri() {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), lastAlbumIdFound);
    }


    @Override
    public int describeContents() {
        return 0;//No Special Objects in here
    }

    public long getLastAlbumIdFound() {
        return lastAlbumIdFound;
    }

    public String getAlbumCount() {
        return albumCount;
    }

    public String getAlbumTitles() {
        return albumTitles;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mediaId);
        dest.writeLong(this.lastAlbumIdFound);
        dest.writeLong(this.totalAudioDuration);
        dest.writeString(this.title);
        dest.writeString(this.trackCount);
        dest.writeString(this.albumCount);
        dest.writeString(this.albumTitles);
    }

    protected ArtistMediaItem(Parcel in) {
        this.mediaId = in.readLong();
        this.lastAlbumIdFound = in.readLong();
        this.totalAudioDuration = in.readLong();
        this.title = in.readString();
        this.trackCount = in.readString();
        this.albumCount = in.readString();
        this.albumTitles = in.readString();
    }

    public static final Creator<ArtistMediaItem> CREATOR = new Creator<ArtistMediaItem>() {
        @Override
        public ArtistMediaItem createFromParcel(Parcel source) {
            return new ArtistMediaItem(source);
        }

        @Override
        public ArtistMediaItem[] newArray(int size) {
            return new ArtistMediaItem[size];
        }
    };
}
