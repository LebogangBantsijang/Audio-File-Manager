package com.lebogang.audiofilemanager.ArtistManagement;

import com.lebogang.audiofilemanager.Models.Artist;

import java.util.List;

public interface ArtistCallbacks {

    /**
     * This method is called when the querying process is complete
     * It always returns an updated list
     * @param artistList contains all the collected artist
     * */
    void onQueryComplete(List<Artist> artistList);
}
