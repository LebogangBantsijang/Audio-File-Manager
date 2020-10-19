package com.lebogang.audiofilemanager.AudioManagement;

import com.lebogang.audiofilemanager.Models.Audio;

import java.util.List;

public interface AudioCallbacks {

    /**
     * This method is called when the querying process is complete
     * It always returns an updated list
     * @param audioList contains all the collected artist
     * */
    void onQueryComplete(List<Audio> audioList);

}
