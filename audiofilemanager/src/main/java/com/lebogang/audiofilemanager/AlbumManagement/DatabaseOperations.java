package com.lebogang.audiofilemanager.AlbumManagement;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.lebogang.audiofilemanager.Models.Album;

import java.util.ArrayList;
import java.util.List;

abstract class DatabaseOperations extends AlbumDatabaseScheme {
    private String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

    /**
     * Get albums from device
     * @param context is required to get resolver
     * @return list of alums
     * */
    protected List<Album> getAlbums(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return queryItems(context.getApplicationContext().getContentResolver());
        }
        return queryItems(context);
    }

    /**
     * This method is used when the api is above 29. Mainly to deal with the artist-id field
     * @param resolver used to create Cursor object
     * @return list of albums
     * */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private List<Album> queryItems(ContentResolver resolver){
        List<Album> list = new ArrayList<>();
        Cursor cursor = resolver.query(super.getMediaStoreUri(),super.getAlbumProjectionNewApi()
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
                String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
                list.add(new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    /**
     * This method is used when the API level is below 29
     * @param context used to get the resolver
     * @return list of albums
     * */
    private List<Album> queryItems(Context context){
        List<Album> list = new ArrayList<>();
        Cursor cursor = context.getApplicationContext().getContentResolver()
                .query(super.getMediaStoreUri(),super.getAlbumProjection()
                ,null,null, sortOrder);
        if (cursor!= null && cursor.moveToFirst()){
            do {
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                long artistId = 0;
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
                String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
                list.add(new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist));
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
    protected Album getAlbumItemWithID(Context context, long id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return queryItemID(context.getApplicationContext().getContentResolver(), id);
        }
        return queryItemID(context, id);
    }

    /**
     * This method is used when the api is above 29. Mainly to deal with the artist-id field
     * @param resolver used to create Cursor object
     * @param id of the desired album
     * @return album that matches the give ID. Null if nothing is found
     * */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Album queryItemID(ContentResolver resolver, long id){
        Cursor cursor = resolver.query(super.getMediaStoreUri(),super.getAlbumProjectionNewApi()
                ,MediaStore.Audio.Albums._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * This method is used when the API level is below 29
     * @param context used to get the resolver
     * @param id of the desired album
     * @return album that matches the give ID. Null if nothing is found
     * */
    private Album queryItemID(Context context, long id){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjectionNewApi()
                ,MediaStore.Audio.Albums._ID + "=?",new String[]{Long.toString(id)}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = 0;
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist);
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
    protected Album getAlbumItemWithName(Context context, String name){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return queryItemName(context.getApplicationContext().getContentResolver(), name);
        }
        return queryItemName(context, name);
    }

    /**
     * This method is used when the api is above 29. Mainly to deal with the artist-id field
     * @param resolver used to create Cursor object
     * @param name of the desired album
     * @return album that matches the give ID. Null if nothing is found
     * */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Album queryItemName(ContentResolver resolver, String name){
        Cursor cursor = resolver.query(super.getMediaStoreUri(),super.getAlbumProjectionNewApi()
                ,MediaStore.Audio.Albums.ALBUM + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist);
            cursor.close();
            return album;
        }
        return null;
    }

    /**
     * This method is used when the API level is below 29
     * @param context used to get the resolver
     * @param name of the desired album
     * @return album that matches the give ID. Null if nothing is found
     * */
    private Album queryItemName(Context context, String name){
        Cursor cursor = context.getApplicationContext().getContentResolver().query(super.getMediaStoreUri(),super.getAlbumProjectionNewApi()
                ,MediaStore.Audio.Albums.ALBUM + "=?",new String[]{name}, null);
        if (cursor!= null && cursor.moveToFirst()){
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            long artistId = 0;
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            String firstYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
            String lastYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
            String numSongs = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            String numSongsByArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST));
            Album album = new Album(title,albumId,artist,artistId,firstYear,lastYear,numSongs,numSongsByArtist);
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
}
