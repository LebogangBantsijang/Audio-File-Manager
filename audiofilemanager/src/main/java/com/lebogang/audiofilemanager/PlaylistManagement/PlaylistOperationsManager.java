package com.lebogang.audiofilemanager.PlaylistManagement;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.lebogang.audiofilemanager.DatabaseScheme.PlaylistDB;
import com.lebogang.audiofilemanager.Models.PlaylistMediaItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class PlaylistOperationsManager extends PlaylistDB {

    private final Uri mediaUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
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
     * @return list of playlist items found on the device
     * */
    public List<PlaylistMediaItem> getPlaylist(ContentResolver resolver){
        Cursor cursor = resolver.query(mediaUri,getPlaylistProjection(),null,null,null);
        lastKnownItemCont = cursor.getCount();
        LinkedHashMap<String, PlaylistMediaItem> mediaItems = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
                Cursor tempCursor = getCursor(resolver, id);
                String trackCount = getTrackCount(tempCursor);
                long totalDuration = getDuration(tempCursor,resolver);
                PlaylistMediaItem item = new PlaylistMediaItem(id,totalDuration,title,trackCount);
                mediaItems.put(title,item);
            }while (cursor.moveToNext());
            cursor.close();
        }
        lastKnownItemCont = mediaItems.size();
        return new ArrayList<>(mediaItems.values());
    }

    private Cursor getCursor(ContentResolver resolver, long mediaId){
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL,mediaId);
        return resolver.query(uri,getMemberProjection(),null, null, null);
    }

    private String getTrackCount(Cursor cursor){
        return ""+cursor.getCount();
    }

    private long getDuration(Cursor cursor, ContentResolver resolver){
        long duration = 0;
        if (cursor.moveToFirst()){
            do {
                long audioId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID));
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

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @return list of playlist items found on the device
     * */
    public PlaylistMediaItem getPlaylist(ContentResolver resolver, long mediaId){
        String selection = MediaStore.Audio.Playlists._ID + "=?";
        String mediaIdString = Long.toString(mediaId);
        Cursor cursor = resolver.query(mediaUri,getPlaylistProjection(),selection,new String[]{mediaIdString},null);
        if (cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
            Cursor tempCursor = getCursor(resolver, id);
            String trackCount = getTrackCount(tempCursor);
            long totalDuration = getDuration(tempCursor,resolver);
            PlaylistMediaItem item = new PlaylistMediaItem(id,totalDuration,title,trackCount);
            cursor.close();
            return item;
        }
        return null;
    }

    /**
     * Create new playlist
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param playlistName playlist name
     * @return playlist Uri
     * */
    public Uri createNewPlaylist(ContentResolver resolver, String playlistName){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, playlistName);
        Uri uri = resolver.insert(mediaUri, values);
        return uri;
    }

    /**
     * Update playlist item
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to update media store
     * @param mediaId media id to be updated
     * @param playlistName playlist name
     * @return the amount of update rows
     * */
    public int updatePlaylist(ContentResolver resolver, long mediaId, String playlistName){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, playlistName);
        String mediaIdToString = Long.toString(mediaId);
        int updatedRows = resolver.update(mediaUri, values,MediaStore.Audio.Playlists._ID+"=?", new String[]{mediaIdToString});
        return updatedRows;
    }

    /**
     * Delete media item from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId ID of the media item to remove
     * @return the amount of deleted rows
     * */
    public int removePlaylist(ContentResolver resolver, long mediaId){
        String mediaIdToString = Long.toString(mediaId);
        int deletedRows = resolver.delete(mediaUri,MediaStore.Audio.Playlists._ID+"=?", new String[]{mediaIdToString});
        return deletedRows;
    }

    /**
     * Add audio item to playlist AudioDB
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId ID of the media item to remove
     * @param audioId id of audio to add
     * @return Uri of the added audio item in the Playlist.Member table
     * */
    public Uri addAudioToPlaylist(ContentResolver resolver,long mediaId, long audioId){
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, mediaId);
        Cursor cursor = resolver.query(uri,getMemberProjection(),null,null,null);
        int addPosition = cursor.getCount();
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID,audioId);
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, addPosition);
        Uri playlist = resolver.insert(uri, values);
        return playlist;
    }

    /**
     * Delete media item from playlist
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId ID of the media item to remove
     * @param audioId ID of the audio item
     * @return the amount of deleted rows
     * */
    public int removeAudioFromPlaylist(ContentResolver resolver, long mediaId, long audioId){
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, mediaId);
        String mediaIdToString = Long.toString(audioId);
        int deletedRows = resolver.delete(uri,MediaStore.Audio.Playlists.Members.AUDIO_ID+"=?", new String[]{mediaIdToString});
        return deletedRows;
    }

    /**
     * Move a playlist item to a new location
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver The content resolver to use
     * @param playlistId The numeric id of the playlist
     * @param from The position of the item to move
     * @param to The position to move the item to
     * @return true on success
     */
    public boolean moveAudioToPosition(ContentResolver resolver,long playlistId, int from, int to){
        return MediaStore.Audio.Playlists.Members.moveItem(resolver,playlistId,from,to);
    }
}
