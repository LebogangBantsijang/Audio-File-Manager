package com.lebogang.audiofilemanager.GenreManagement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.DatabaseScheme.GenreDB;
import com.lebogang.audiofilemanager.Models.GenreMediaItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class GenreOperationsManager extends GenreDB {
    private final Uri mediaUri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    private final Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private int lastKnownItemCont = -1;

    public int getLastKnownItemCont() {
        return lastKnownItemCont;
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @return list of genre items found on the device
     * */
    public List<GenreMediaItem> getGenre(ContentResolver resolver){
        Cursor cursor = resolver.query(mediaUri,getGenreProjection(),null, null, getSortOder());
        lastKnownItemCont = cursor.getCount();
        LinkedHashMap<String, GenreMediaItem> mediaItems = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
                Cursor tempCursor = getCursor(resolver, id);
                String trackCount = getTrackCount(tempCursor);
                long totalDuration = getDuration(tempCursor, resolver);
                GenreMediaItem item = new GenreMediaItem(id, title, trackCount, totalDuration);
                mediaItems.put(title, item);
            }while (cursor.moveToNext());
            cursor.close();
        }
        lastKnownItemCont = mediaItems.size();
        return new ArrayList<>(mediaItems.values());
    }

    private Cursor getCursor(ContentResolver resolver, long mediaId){
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri(MediaStore.VOLUME_EXTERNAL,mediaId);
        return resolver.query(uri,getMemberProjection(),null, null, null);
    }

    private String getTrackCount(Cursor cursor){
        return ""+cursor.getCount();
    }

    private long getDuration(Cursor cursor, ContentResolver resolver){
        long duration = 0;
        if (cursor.moveToFirst()){
            do {
                long audioId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.AUDIO_ID));
                //Find fields that are unavailable in this table
                duration += getAudioDuration(resolver,audioId);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return duration;
    }

    //Get duration of a song
    private long getAudioDuration(ContentResolver resolver, long audioId){
        String selection = MediaStore.Audio.Media._ID + "=?";
        String mediaIdToString = Long.toString(audioId);
        Cursor cursor = resolver.query(audioUri,getAudioProjection(),selection, new String[]{mediaIdToString},null);
        if (cursor.moveToFirst()){
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            cursor.close();
            return duration;
        }
        return 0;
    }
}
