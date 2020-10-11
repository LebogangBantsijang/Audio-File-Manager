package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.lebogang.audiofilemanager.Models.Album;

import java.util.ArrayList;
import java.util.List;

abstract class DatabaseOperations extends DatabaseScheme {
    private String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

    /**
     * Get albums from device
     * @param context used to get the resolver
     * @return list of albums
     * */
    protected List<Album> queryItems(Context context){
        List<Album> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
                String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                list.add(new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * Call this method before you queryItems/getAlbumItems
     * @param order Sort order: Default is {@link MediaStore.Audio.Albums#ALBUM + " ASC"}
     * */
    @Override
    public void setSortOrder(String order) {
        this.sortOrder = order;
    }

    /**
     * Get albums from device
     * @param context is required to get resolver
     * @param id of the required album item
     * @return list of alums
     * */
    protected Album queryItemID(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,MediaStore.Audio.Albums._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * Get albums from device
     * @param context is required to get resolver
     * @param name of the required album item
     * @return list of alums
     * */
    protected Album queryItemName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,MediaStore.Audio.Albums.ALBUM + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * Get the total number of items
     * */
    public int getItemCount(Context context){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri()
                ,new String[]{MediaStore.Audio.Albums._ID}, null,null, null);
        if (cursor != null){
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public abstract List<Album> getAlbums();
    public abstract Album getAlbumItemWithID(long id);
    public abstract Album getAlbumItemWithName(String name);
}
