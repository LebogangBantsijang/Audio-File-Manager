package com.lebogang.audiofilemanager.PlaylistManagement;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.audiofilemanager.Models.Playlist;

import java.util.List;

public class PlaylistManager extends DatabaseOperations {
    private final Context context;
    private PlaylistCallbacks callbacks;
    private MutableLiveData<List<Playlist>> liveData;

    public PlaylistManager(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
    }

    public void registerCallbacks(PlaylistCallbacks playlistCallbacks, LifecycleOwner owner){
        this.callbacks = playlistCallbacks;
        owner.getLifecycle().addObserver(getLifecycleObserver());
    }

    @Override
    public List<Playlist> getPlaylists() {
        return super.queryItems(context);
    }

    @Override
    public Playlist getPlaylistItemWithId(long id) {
        return super.queryItemID(context, id);
    }

    @Override
    public Playlist getPlaylistItemWithName(String name) {
        return super.queryItemName(context, name);
    }

    @Override
    public boolean createPlaylist(String name) {
        boolean result = super.createPlaylist(context , name);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean updatePlaylist(long id, String name) {
        boolean result = super.updatePlaylist(context, id , name);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean deletePlaylist(long id) {
        boolean result = super.deletePlaylist(context , id);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean addMultipleItemsToPlaylist(long id, String[] audioIds) {
        boolean result = super.addAudioToPlaylist(context,id, audioIds);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean addSingleItemsToPlaylist(long id, String audioId) {
        boolean result = super.addAudioToPlaylist(context,id, audioId);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean deleteAudioFromPlaylist(long id, String[] audioIds) {
        boolean result = super.removeAudioFromPlaylist(context,id,audioIds);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    @Override
    public boolean deleteAudioFromPlaylist(long id, long audioId) {
        boolean result = super.removeAudioFromPlaylist(context,id,audioId);
        if (result && liveData!=null)
            liveData.setValue(getPlaylists());
        return result;
    }

    private DefaultLifecycleObserver getLifecycleObserver(){
        return new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                liveData.observe(owner, playlists -> {
                    callbacks.onQueryComplete(playlists);
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                liveData.setValue(getPlaylists());
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                liveData.removeObservers(owner);
                owner.getLifecycle().removeObserver(this);
            }
        };
    }
}
