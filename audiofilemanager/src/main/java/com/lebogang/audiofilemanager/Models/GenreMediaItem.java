package com.lebogang.audiofilemanager.Models;

import android.net.Uri;
import android.os.Parcel;

public class GenreMediaItem extends MediaItem {
    private final  long mediaId;
    private final  String title;
    private final  String trackCount;
    private final  long totalDuration;

    public GenreMediaItem(long mediaId, String title, String trackCount, long totalDuration) {
        this.mediaId = mediaId;
        this.title = title;
        this.trackCount = trackCount;
        this.totalDuration = totalDuration;
    }

    @Override
    public Uri getContentUri() {
        return null;
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
        return "Genre";
    }

    @Override
    public String getTrackCount() {
        return trackCount;
    }

    @Override
    public long getDuration() {
        return totalDuration;
    }

    @Override
    public int getMediaItemType() {
        return MediaItem.MEDIA_TYPE_GENRE;
    }

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
        dest.writeString(this.title);
        dest.writeString(this.trackCount);
        dest.writeLong(this.totalDuration);
    }

    protected GenreMediaItem(Parcel in) {
        this.mediaId = in.readLong();
        this.title = in.readString();
        this.trackCount = in.readString();
        this.totalDuration = in.readLong();
    }

    public static final Creator<GenreMediaItem> CREATOR = new Creator<GenreMediaItem>() {
        @Override
        public GenreMediaItem createFromParcel(Parcel source) {
            return new GenreMediaItem(source);
        }

        @Override
        public GenreMediaItem[] newArray(int size) {
            return new GenreMediaItem[size];
        }
    };
}
