package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.lebogang.audiofilemanager.DatabaseScheme.AudioDB;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;
import com.lebogang.audiofilemanager.Models.MediaItem;

import java.util.ArrayList;
import java.util.List;

class AudioOperationsManger extends AudioDB {

    private final Uri mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    /**
     * Set the sort order when searching for audio items
     *
     * @param defaultSortOrder : MediaStore Fields,{@link MediaStore.Audio.Media} e.g. MediaStore.Audio.Media.TITLE + " ACS"
     * */
    @Override
    public void setDefaultSortOrder(String defaultSortOrder) {
        super.setSortOrder(defaultSortOrder);
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @return list of audio items found on the device
     * */
    protected final List<AudioMediaItem> getAudio(@NonNull ContentResolver resolver){
        Cursor cursor = resolver.query(mediaUri,getAudioProjection(),getSelection(),null,getSortOrder());
        List<AudioMediaItem> mediaItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                AudioMediaItem item = new AudioMediaItem(id,albumId,artistId,audioDuration,audioSize,dateAdded,title,albumTitle,artistTitle,composer,releaseYear,trackNumber);
                mediaItems.add(item);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return mediaItems;
    }

    /**
     * Remove media item from local AudioDB associated with your application
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to remove media store
     * @param mediaId media id to be removed
     * @return the amount of deleted rows
     * */
    protected int removeAudio(ContentResolver resolver,long mediaId){
        String mediaIdToString = Long.toString(mediaId);
        int deletedRows = resolver.delete(mediaUri,MediaStore.Audio.Media._ID + "=?", new String[]{mediaIdToString});
        return deletedRows;
    }

    /**
     * Update media item from local AudioDB associated with your application
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to update media store
     * @param mediaId media id to be updated
     * @param values {@link ContentValues}: new values to insert
     * @return the amount of update rows
     * */
    protected int updateAudio(ContentResolver resolver, long mediaId, ContentValues values){
        String mediaIdToString = Long.toString(mediaId);
        int updatedRows = resolver.update(mediaUri,values,MediaStore.Audio.Media._ID + "=?", new String[]{mediaIdToString});
        return updatedRows;
    }

    /**
     * Get media item from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId ID of the media item to locate
     * @return AudioItem Object found on the device. Return null if nothing matches the id
     * */
    protected AudioMediaItem getAudioItem(ContentResolver resolver, long mediaId){
        String mediaIdToString = Long.toString(mediaId);
        Cursor cursor = resolver.query(mediaUri,getAudioProjection(),MediaStore.Audio.Media._ID + "=?",new String[]{mediaIdToString},null);
        if (cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
            String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
            String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
            AudioMediaItem item = new AudioMediaItem(id,albumId,artistId,audioDuration,audioSize,dateAdded,title,albumTitle,artistTitle,composer,releaseYear,trackNumber);
            cursor.close();
            return item;
        }
        return null;
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaId the of either album, artist, playlist or genre
     * @param mediaType Type of media to retrieve: {@link MediaItem}
     * @return list of audio items found on the device
     * */
    public List<AudioMediaItem> getAudio(ContentResolver resolver, long mediaId, int mediaType){
        String selection = getSelectionType(mediaType);
        if (mediaType == MediaItem.MEDIA_TYPE_PLAYLIST)
            return getPlaylistAudio(resolver,mediaId);
        if (mediaType == MediaItem.MEDIA_TYPE_GENRE)
            return getGenreAudio(resolver,mediaId);
        String mediaIdToString = Long.toString(mediaId);
        Cursor cursor = resolver.query(mediaUri,getAudioProjection(),selection,new String[]{mediaIdToString},null);
        List<AudioMediaItem> mediaItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                AudioMediaItem item = new AudioMediaItem(id,albumId,artistId,audioDuration,audioSize,dateAdded,title,albumTitle,artistTitle,composer,releaseYear,trackNumber);
                mediaItems.add(item);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return mediaItems;
    }

    /**
     * Get media items from the external storage
     *
     * @exception SecurityException: Request Read/Write permissions from user before calling this method
     * @param resolver resolver used to query media store
     * @param mediaTitle the of either album or artist
     * @param mediaType Type of media to retrieve: {@link MediaItem}
     * @return list of audio items found on the device
     * */
    public List<AudioMediaItem> getAudio(ContentResolver resolver, String mediaTitle, long mediaId, int mediaType){
        String selection = getSelectionTypeTitle(mediaType);
        if (mediaType == MediaItem.MEDIA_TYPE_PLAYLIST)
            return getPlaylistAudio(resolver,mediaId);
        if (mediaType == MediaItem.MEDIA_TYPE_GENRE)
            return getGenreAudio(resolver,mediaId);
        Cursor cursor = resolver.query(mediaUri,getAudioProjection(),selection,new String[]{mediaTitle},null);
        List<AudioMediaItem> mediaItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                long audioDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long audioSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artistTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String releaseYear = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String trackNumber = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
                AudioMediaItem item = new AudioMediaItem(id,albumId,artistId,audioDuration,audioSize,dateAdded,title,albumTitle,artistTitle,composer,releaseYear,trackNumber);
                mediaItems.add(item);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return mediaItems;
    }

    private String getSelectionTypeTitle(int type){
        switch (type){
            case MediaItem.MEDIA_TYPE_ALBUM:
                return MediaStore.Audio.Media.ALBUM + "=?";
            case MediaItem.MEDIA_TYPE_ARTIST:
                return MediaStore.Audio.Media.ARTIST + "=?";
        }
        return "";
    }

    private String getSelectionType(int type){
        switch (type){
            case MediaItem.MEDIA_TYPE_ALBUM:
                return MediaStore.Audio.Media.ALBUM_ID + "=?";
            case MediaItem.MEDIA_TYPE_ARTIST:
                return MediaStore.Audio.Media.ARTIST_ID + "=?";
        }
        return "";
    }

    private List<AudioMediaItem> getPlaylistAudio(ContentResolver resolver, long mediaId){
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, mediaId);
        Cursor cursor = resolver.query(uri, new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID}, null, null, null);
        List<AudioMediaItem> mediaItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID));
                AudioMediaItem item = getAudioItem(resolver, id);
                if (item != null)
                    mediaItems.add(item);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return mediaItems;
    }

    private List<AudioMediaItem> getGenreAudio(ContentResolver resolver, long mediaId){
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri(MediaStore.VOLUME_EXTERNAL, mediaId);
        Cursor cursor = resolver.query(uri, new String[]{MediaStore.Audio.Genres.Members.AUDIO_ID}, null, null, null);
        List<AudioMediaItem> mediaItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres.Members.AUDIO_ID));
                AudioMediaItem item = getAudioItem(resolver, id);
                if (item != null)
                    mediaItems.add(item);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return mediaItems;
    }

}
