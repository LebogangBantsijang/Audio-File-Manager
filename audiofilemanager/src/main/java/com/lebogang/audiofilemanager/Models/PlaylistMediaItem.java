package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

public class PlaylistMediaItem extends MediaItem {
    private final long mediaId;
    private final long totalAudioDuration;
    private final String title;
    private final String trackCount;

    public PlaylistMediaItem(long mediaId, long totalAudioDuration, String title, String trackCount) {
        this.mediaId = mediaId;
        this.totalAudioDuration = totalAudioDuration;
        this.title = title;
        this.trackCount = trackCount;
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
        return "Playlist";
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
        return MediaItem.MEDIA_TYPE_PLAYLIST;
    }

    /**
     * Override this function and create custom URI
     *
     * @return Returns null: No Playlist album art available in AudioDB
     * */
    @Override
    public Uri getAlbumArtUri() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mediaId);
        dest.writeLong(this.totalAudioDuration);
        dest.writeString(this.title);
        dest.writeString(this.trackCount);
    }

    protected PlaylistMediaItem(Parcel in) {
        this.mediaId = in.readLong();
        this.totalAudioDuration = in.readLong();
        this.title = in.readString();
        this.trackCount = in.readString();
    }

    public static final Creator<PlaylistMediaItem> CREATOR = new Creator<PlaylistMediaItem>() {
        @Override
        public PlaylistMediaItem createFromParcel(Parcel source) {
            return new PlaylistMediaItem(source);
        }

        @Override
        public PlaylistMediaItem[] newArray(int size) {
            return new PlaylistMediaItem[size];
        }
    };
}
