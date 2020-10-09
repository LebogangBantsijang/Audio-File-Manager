package com.lebogang.audiofilemanager.AudioManagement;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Callbacks.AudioCallBacks;
import com.lebogang.audiofilemanager.Models.AlbumMediaItem;
import com.lebogang.audiofilemanager.Models.AudioMediaItem;

import java.util.List;

public class AudioFileManger extends ManagerWithContext{

    private ContentResolver resolver;
    private AudioOperationsManger operationsManger;
    private MutableLiveData<List<AudioMediaItem>> liveDataAudioItems;
    private LifecycleOwner lifecycleOwner;
    private AudioCallBacks audioCallBacks;
    private int count = -1;
    private int mediaType = -1;
    private long mediaId = -1;
    private String mediaTitle = null;
    {
        operationsManger = new AudioOperationsManger();
    }

    public AudioFileManger(Context context, LifecycleOwner owner) {
        super();
        this.lifecycleOwner = owner;
        resolver = context.getApplicationContext().getContentResolver();
        liveDataAudioItems = new MutableLiveData<>();
        operationsManger = new AudioOperationsManger();
    }

    public AudioFileManger() {
        super();
    }

    /**
     *
     * Register callbacks to be notified when changes occur to the data. The applies to all audio files
     *
     *  cannot be called without the context. An Exception will be throw if such methods are called when a call back is not
     *  registered.
     *  So to summarise all this crap, use methods that require a context to perform an action if u not interested in
     *  receiving callbacks.
     * */
    public void registerCallbacks(AudioCallBacks audioCallBacks){
        this.audioCallBacks = audioCallBacks;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    /**
     * Register callbacks to be notified when changes occur to the data. This applies to sub-folders such as albums.
     *
     *  if callbacks are registered then method such as {@link #updateItem(AudioMediaItem, ContentValues)}
     *  can be called without the context. An Exception will be throw if such method are called when a call back is not
     *  registered.
     *  So to summarise all this crap, use methods that require a context to perform an action if u not interested in
     *  receiving callbacks.
     * */
    public void registerCallbacks(AudioCallBacks audioCallBacks, String mediaTitle , long mediaId , int mediaType){
        this.audioCallBacks = audioCallBacks;
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.mediaTitle = mediaTitle;
        lifecycleOwner.getLifecycle().addObserver(getDefaultLifecycleObserver());
    }

    private DefaultLifecycleObserver getDefaultLifecycleObserver(){
        return new DefaultLifecycleObserver() {

            /**
             * Initialise the live data when onCreate is called
             * */
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveDataAudioItems.observe(lifecycleOwner, mediaItems -> {
                    audioCallBacks.onGetAudio(mediaItems);
                    count = mediaItems.size();
                });
            }

            /**
             * Get files from device when activity/fragment resumes
             * */
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                new Handler().post(()->{
                    if (mediaType < 0){
                        List<AudioMediaItem> mediaItems = operationsManger.getAudio(resolver);
                        if (mediaItems.size() != count){
                            count = mediaItems.size();
                            liveDataAudioItems.setValue(mediaItems);
                        }
                    }
                    else{
                        List<AudioMediaItem> mediaItems = operationsManger.getAudio(resolver, mediaTitle, mediaId, mediaType);
                        if (mediaItems.size() != count){
                            count = mediaItems.size();
                            liveDataAudioItems.setValue(mediaItems);
                        }
                    }
                });
            }

            /**
             * uninitialize live data when component is destroyed
             * */
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                lifecycleOwner.getLifecycle().removeObserver(this);
            }
        };
    }

    /**
     * Set the sort order when searching for audio items
     * */
    public void setSortOrder(String order){
        operationsManger.setSortOrder(order);
    }

    /**
     * Update audio item and add the new value to the live data
     * Call this method is you have registered audio callbacks
     * */
    public int updateItem(AudioMediaItem mediaItem, ContentValues values){
        int updatedRows = operationsManger.updateAudio(resolver,mediaItem.getMediaId(),values);
        int pos = liveDataAudioItems.getValue().indexOf(mediaItem);
        AudioMediaItem item =operationsManger.getAudioItem(resolver,mediaItem.getMediaId());
        liveDataAudioItems.getValue().remove(pos);
        liveDataAudioItems.getValue().add(pos,item);
        audioCallBacks.onUpdate(item);
        return updatedRows;
    }

   /**
    * Remove audio item from local AudioDB
    * Call this method is you have registered audio callbacks
    *
    * */
    public int removeItem(AudioMediaItem mediaItem){
        int deletedRows = operationsManger.removeAudio(resolver, mediaItem.getMediaId());
        int pos = liveDataAudioItems.getValue().indexOf(mediaItem);
        liveDataAudioItems.getValue().remove(pos);
        audioCallBacks.onRemove(mediaItem);
        return deletedRows;
    }

    /**
     * Use this class to create a ContentValues object
     * This will ensure that you update the correct values with no null exception occur
     *
     * */
    public static class ValueBuilder{
        private final ContentValues contentValues;
        public ValueBuilder(AudioMediaItem audioItem) {
            contentValues = new ContentValues();
            if (audioItem.getAlbumTitle() != null)
                contentValues.put(MediaStore.Audio.Media.ALBUM, audioItem.getAlbumTitle());
            if (audioItem.getTitle() != null)
                contentValues.put(MediaStore.Audio.Media.TITLE, audioItem.getTitle());
            if (audioItem.getArtistTitle() != null)
                contentValues.put(MediaStore.Audio.Media.ARTIST, audioItem.getArtistTitle());
            if (audioItem.getComposer() != null)
                contentValues.put(MediaStore.Audio.Media.COMPOSER, audioItem.getComposer());
            if (audioItem.getReleaseYear() != null)
                contentValues.put(MediaStore.Audio.Media.YEAR, audioItem.getReleaseYear());
            if (audioItem.getTrackNumber() != null)
                contentValues.put(MediaStore.Audio.Media.TRACK,audioItem.getTrackNumber());
        }

        public void setTitle(@NonNull String title) {
            if (title != null)
            contentValues.put(MediaStore.Audio.Media.TITLE, title);
        }

        public void setAlbum(@NonNull String album) {
            if (album != null)
            contentValues.put(MediaStore.Audio.Media.ALBUM, album);
        }

        public void setArtist(@NonNull String artist) {
            if (artist != null)
            contentValues.put(MediaStore.Audio.Media.ARTIST, artist);
        }

        public void setComposer(@NonNull String composer) {
            if (composer != null)
            contentValues.put(MediaStore.Audio.Media.COMPOSER, composer);
        }

        public void setYear(@NonNull String year) {
            if (year != null)
            contentValues.put(MediaStore.Audio.Media.YEAR, year);
        }

        public void setCompilation(@NonNull String track){
            if (track != null)
            contentValues.put(MediaStore.Audio.Media.TRACK, track);
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
