package com.lebogang.audiofilemanager.Models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

public abstract class MediaItem implements Parcelable {

    public static final int MEDIA_TYPE_AUDIO = 0;
    public static final int MEDIA_TYPE_ALBUM = 1;
    public static final int MEDIA_TYPE_ARTIST = 2;
    public static final int MEDIA_TYPE_PLAYLIST = 3;
    public static final int MEDIA_TYPE_GENRE = 4;

    public abstract Uri getContentUri();

    public abstract long getMediaId();

    public abstract String getTitle();

    public abstract String getSubTitle();

    public abstract String getTrackCount();

    public abstract long getDuration();

    public abstract int getMediaItemType();

    public abstract Uri getAlbumArtUri();

}
