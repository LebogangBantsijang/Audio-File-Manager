package com.lebogang.audiofilemanager.ArtistManagement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.DatabaseScheme.ArtistDB;
import com.lebogang.audiofilemanager.Models.ArtistMediaItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class ArtistOperationsManager extends ArtistDB {

    private final Uri mediaUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    private final Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private int lastKnownItemCount = -1;
    /**
     * Set the sort order when searching for artist items
     *
     * @param defaultSortOrder : MediaStore Fields, e.g. MediaStore.Audio.Artists.TITLE + " ACS"
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
     * @return list of artist items found on the device
     * */
    public List<ArtistMediaItem> getArtists(ContentResolver resolver){
        Cursor cursor = resolver.query(mediaUri,getArtistProjection(),null,null,getSortOrder());
        LinkedHashMap<String, ArtistMediaItem> mediaItems = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String albumCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                String trackCount = getTrackCount(resolver, title);
                Cursor tempCursor = getCursor(resolver, id);
                long lastAlbumIdFound = getAlbumID(tempCursor);
                long totalAudioDuration = getDuration(tempCursor);
                String albumTitles = getTitles(tempCursor);
                ArtistMediaItem item = new ArtistMediaItem(id,lastAlbumIdFound,totalAudioDuration,title,trackCount,albumCount,albumTitles);
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
                ,MediaStore.Audio.Media.ARTIST + "=?", new String[]{title}, null);
        String count = "" + cursor.getCount();
        cursor.close();
        return count;
    }

    private Cursor getCursor(ContentResolver resolver, long mediaId){
        String selection = MediaStore.Audio.Media.ARTIST_ID + "=?";
        String mediaIdToString = Long.toString(mediaId);
        return resolver.query(audioUri,getAudioProject(),selection,new String[]{mediaIdToString}, null);
    }

    private long getAlbumID(Cursor cursor){
        long id = -1;
        if (cursor.moveToFirst()){
            id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        }
        return id;
    }

    private long getDuration(Cursor cursor){
        long duration = 0;
        if (cursor.moveToFirst()){
            do {
                duration += cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            }while (cursor.moveToNext());
        }
        return duration;
    }

    private String getTitles(Cursor cursor){
        String albumTitles = "";
        if (cursor.moveToFirst()){
            do {
                String temp = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                if (!albumTitles.contains(temp))
                    albumTitles+= temp+ " ";
            }while (cursor.moveToNext());
            cursor.close();
        }
        return albumTitles;
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId Artist ID
     * @return list of artist items found on the device
     * */
    public ArtistMediaItem getArtist(ContentResolver resolver, long mediaId){
        String selection = MediaStore.Audio.Artists._ID + "=?";
        String mediaIdString = Long.toString(mediaId);
        Cursor cursor = resolver.query(mediaUri,getArtistProjection(),selection,new String[]{mediaIdString},null);
        if (cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
            String trackCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            String albumCount = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            Cursor tempCursor = getCursor(resolver, id);
            long lastAlbumIdFound = getAlbumID(tempCursor);
            long totalAudioDuration = getDuration(tempCursor);
            String albumTitles = getTitles(tempCursor);
            ArtistMediaItem item = new ArtistMediaItem(id,lastAlbumIdFound,totalAudioDuration,title,trackCount,albumCount,albumTitles);
            cursor.close();
            return item;
        }
        return null;
    }
}
