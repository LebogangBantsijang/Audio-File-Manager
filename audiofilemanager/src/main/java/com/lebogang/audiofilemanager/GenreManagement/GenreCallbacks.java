package com.lebogang.audiofilemanager.GenreManagement;

import com.lebogang.audiofilemanager.Models.Genre;

import java.util.List;

public interface GenreCallbacks {

    /**
     * This method is called when the querying process is complete
     * It always returns an updated list
     * @param genreList contains all the collected genres
     * */
    void onQueryComplete(List<Genre> genreList);
}
