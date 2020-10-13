package com.lebogang.audiofilemanager.PlaylistManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Playlist;

import java.util.ArrayList;
import java.util.List;

abstract class DatabaseOperations extends DatabaseScheme{

    private String sortOrder = MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER;

    /**
     * Get playlists from device
     * @param context used to get the resolver
     * @return list of playlists
     * */
    protected List<Playlist> queryItems(Context context){
        List<Playlist> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getPlaylistProjection()
                        ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
                String[] audioIds = getAudioIds(context, id);
                list.add(new Playlist(id, title, audioIds));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Call this method before you queryItems/getPlaylists
     * @param order Sort order: Default is {@link MediaStore.Audio.Playlists#NAME + " ASC"}
     * */
    @Override
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    /**
     * Get playlists from device
     * @param context is required to get resolver
     * @param id of the required playlist item
     * @return list of playlists
     * */
    protected Playlist queryItemID(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getPlaylistProjection()
                        ,MediaStore.Audio.Playlists._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long playlistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
            String[] audioIds = getAudioIds(context, playlistId);
            Playlist playlist = new Playlist(playlistId, title, audioIds);
            cursor.close();
            return playlist;
        }
        return null;
    }

    /**
     * Get playlists from device
     * @param context is required to get resolver
     * @param name of the required playlist item
     * @return list of playlists
     * */
    protected Playlist queryItemName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getPlaylistProjection()
                        ,MediaStore.Audio.Playlists.NAME + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long playlistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
            String[] audioIds = getAudioIds(context, playlistId);
            Playlist playlist = new Playlist(playlistId, title, audioIds);
            cursor.close();
            return playlist;
        }
        return null;
    }

    /**
     * Create a new playlist
     * @param context is required
     * @param name of the new playlist
     * @return true if successful
     * */
    protected boolean createPlaylist(Context context, String name){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, name);
        Uri result = context.getApplicationContext().getContentResolver().insert(super.getMediaStoreUri(), values);
        return result != null;
    }

    /**
     * Update a playlist
     * @param context is required
     * @param id of the playlist to update
     * @param name: The new name of the playlist
     * @return true if successful
     * */
    protected boolean updatePlaylist(Context context, long id, String name){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, name);
        int result = context.getApplicationContext().getContentResolver().update(super.getMediaStoreUri(), values
                , MediaStore.Audio.Playlists._ID + "=?", new String[]{Long.toString(id)});
        return result != 0;
    }

    /**
     * delete a playlist
     * @param context is required
     * @param id of the playlist to delete
     * @return true if successful
     * */
    protected boolean deletePlaylist(Context context, long id){
        int result = context.getApplicationContext().getContentResolver().delete(super.getMediaStoreUri()
                ,MediaStore.Audio.Playlists._ID + "=?", new String[]{Long.toString(id)});
        return result != 0;
    }

    /**
     * Add multiple audio items to a playlist
     * @param context is required
     * @param id of the playlist
     * @param audioIds to insert
     * @return true if successful
     * */
    protected boolean addAudioToPlaylist(Context context, long id, String[] audioIds){
        //init the insert data
        Cursor countCursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id)
                ,new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID},null,null, null);
        int currentItemCount = countCursor.getCount();
        countCursor.close();
        ContentValues values = new ContentValues();
        for (String audioId:audioIds){
            ++currentItemCount;
            values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.toString(currentItemCount));
            values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId);
        }
        //insert
        Uri results = context.getApplicationContext().getContentResolver().insert(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id),values);
        return results != null;
    }

    /**
     * Add one audio items to a playlist
     * @param context is required
     * @param id of the playlist
     * @param audioId to insert
     * @return true if successful
     * */
    protected boolean addAudioToPlaylist(Context context, long id, String audioId){
        //init the insert data
        Cursor countCursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id)
                ,new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID},null,null, null);
        int currentItemCount = countCursor.getCount() + 1;
        countCursor.close();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.toString(currentItemCount));
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId);
        //insert
        Uri results = context.getApplicationContext().getContentResolver().insert(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id),values);
        return results != null;
    }

    protected boolean removeAudioFromPlaylist(Context context, long id ,String[] audioIds){
        int result = context.getApplicationContext().getContentResolver().delete(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id),
                super.produceWhereClauses(audioIds), null);
        return result == audioIds.length;
    }

    protected boolean removeAudioFromPlaylist(Context context, long id , long audioId){
        int result = context.getApplicationContext().getContentResolver().delete(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id),
                MediaStore.Audio.Playlists.Members.AUDIO_ID + "=?", new String[]{Long.toString(audioId)});
        return result != 0;
    }
  
    /**
     * Get the total number of items
     * */
    public int getItemCount(Context context){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,new String[]{MediaStore.Audio.Playlists._ID}, null,null, null);
        if (cursor != null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    private String[] getAudioIds(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, id)
                , new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID}, null, null, null);
        if (cursor!= null){
            String[] audioIds = new String[cursor.getCount()];
            if (cursor.moveToFirst()){
                int index = 0;
                do {
                    String audioId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID));
                    audioIds[index] = audioId;
                    index++;
                }while (cursor.moveToNext());
            }
            cursor.close();
            return audioIds;
        }
        return new String[0];
    }

    public abstract List<Playlist> getPlaylists();

    public abstract Playlist getPlaylistItemWithId(long id);

    public abstract Playlist getPlaylistItemWithName(String name);

    public abstract boolean createPlaylist(String name);

    public abstract boolean updatePlaylist(long id, String name);

    public abstract boolean deletePlaylist(long id);

    public abstract boolean addMultipleItemsToPlaylist(long id, String[] audioIds);

    public abstract boolean addSingleItemsToPlaylist(long id, String audioId);
}
