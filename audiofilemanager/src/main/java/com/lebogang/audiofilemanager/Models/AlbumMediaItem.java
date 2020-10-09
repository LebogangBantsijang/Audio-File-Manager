package com.lebogang.audiofilemanager.Models;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

public class AlbumMediaItem extends MediaItem {
    private final long mediaId;
    private final String title;
    private final String artistTitle;
    private final String trackCount;
    private final long audioDuration;

    public AlbumMediaItem(long mediaId, String title, String artistTitle, String trackCount, long audioDuration) {
        this.mediaId = mediaId;
        this.title = title;
        this.artistTitle = artistTitle;
        this.trackCount = trackCount;
        this.audioDuration = audioDuration;
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
        return artistTitle;
    }

    @Override
    public String getTrackCount() {
        return trackCount;
    }

    @Override
    public long getDuration() {
        return audioDuration;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

    @Override
    public int getMediaItemType() {
        return MediaItem.MEDIA_TYPE_ALBUM;
    }

    /**
     * Override this function and create custom URI
     * */
    @Override
    public Uri getAlbumArtUri() {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), mediaId);
    }


    @Override
    public int describeContents() {
        return 0;//No Special Objects in here
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mediaId);
        dest.writeString(this.title);
        dest.writeString(this.artistTitle);
        dest.writeString(this.trackCount);
        dest.writeLong(this.audioDuration);
    }

    protected AlbumMediaItem(Parcel in) {
        this.mediaId = in.readLong();
        this.title = in.readString();
        this.artistTitle = in.readString();
        this.trackCount = in.readString();
        this.audioDuration = in.readLong();
    }

    public static final Creator<AlbumMediaItem> CREATOR = new Creator<AlbumMediaItem>() {
        @Override
        public AlbumMediaItem createFromParcel(Parcel source) {
            return new AlbumMediaItem(source);
        }

        @Override
        public AlbumMediaItem[] newArray(int size) {
            return new AlbumMediaItem[size];
        }
    };
}
