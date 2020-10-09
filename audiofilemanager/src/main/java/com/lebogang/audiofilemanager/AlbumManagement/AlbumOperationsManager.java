package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.DatabaseScheme.AlbumDB;
import com.lebogang.audiofilemanager.Models.AlbumMediaItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class AlbumOperationsManager extends AlbumDB {

    private final Uri mediaUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private final Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private int lastKnownItemCount = -1;

    /**
     * Set the sort order when searching for albums items
     *
     * @param defaultSortOrder : MediaStore Fields, e.g. MediaStore.Audio.Albums.ALBUM + " ASC"
     * */
    @Override
    public void setDefaultSortOrder(String defaultSortOrder) {
        super.setSortOrder(defaultSortOrder);
    }

    public int getLastKnownItemCount() {
        return lastKnownItemCount;
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @return list of albums items found on the device
     * */
    public List<AlbumMediaItem> getAlbums(ContentResolver resolver){
        Cursor cursor = resolver.query(mediaUri,getAlbumProjection(), null, null,getSortOrder());
        LinkedHashMap<String, AlbumMediaItem> mediaItems = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String trackCount = getTrackCount(resolver, title);
                Cursor tempCursor = getCursor(resolver, id);
                long audioDuration = getDuration(tempCursor);
                AlbumMediaItem item = new AlbumMediaItem(id,title,artistTitle,trackCount,audioDuration);
                if (!mediaItems.containsKey(title))
                    mediaItems.put(title, item);
            }while (cursor.moveToNext());
            cursor.close();
        }
        lastKnownItemCount = mediaItems.size();
        return new ArrayList<>(mediaItems.values());
    }

    private String getTrackCount(ContentResolver resolver, String title){
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Media._ID}
        ,MediaStore.Audio.Media.ALBUM + "=?", new String[]{title}, null);
        String count = "" + cursor.getCount();
        cursor.close();
        return count;
    }

    private Cursor getCursor(ContentResolver resolver, long mediaId){
        String selection = MediaStore.Audio.Media.ALBUM_ID + "=?";
        String mediaIdToString = Long.toString(mediaId);
        return resolver.query(audioUri,getAudioProjection(),selection,new String[]{mediaIdToString}, null);
    }

    private long getDuration(Cursor cursor){
        long duration = 0;
        if (cursor.moveToFirst()){
            do {
                duration += cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return duration;
    }

    /**
     * Get media item from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId Album ID
     * @return albums item found on the device
     * */
    public AlbumMediaItem getAlbum(ContentResolver resolver, long mediaId){
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String mediaIdString = Long.toString(mediaId);
        Cursor cursor = resolver.query(mediaUri,getAlbumProjection(), selection, new String[]{mediaIdString},null);
        if (cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String trackCount = getTrackCount(resolver, title);
            Cursor tempCursor = getCursor(resolver, id);
            long audioDuration = getDuration(tempCursor);
            AlbumMediaItem item = new AlbumMediaItem(id,title,artistTitle,trackCount,audioDuration);
            cursor.close();
            return item;
        }
        return null;
    }
}
