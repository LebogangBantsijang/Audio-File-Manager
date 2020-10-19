package com.lebogang.audiofilemanager.PlaylistManagement;

import com.lebogang.audiofilemanager.Models.Playlist;

import java.util.List;

public interface PlaylistCallbacks {

    /**
     * This method is called when the querying process is complete
     * It always returns an updated list
     * @param playlists contains all the collected playlists
     * */
    void onQueryComplete(List<Playlist> playlists);

}
