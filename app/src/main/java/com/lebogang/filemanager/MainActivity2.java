/*
 * Copyright (c) 2021. Lebogang Bantsijng
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *   implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 *
 */

package com.lebogang.filemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.lebogang.filemanager.data.Genre;
import com.lebogang.filemanager.managers.MediaManager;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        check();
        mediaManager = new MediaManager(getApplicationContext());
        test();
    }

    private void check(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 232);
        }
    }

    private void test(){
        List songs = mediaManager.getAudio();
        List albums = mediaManager.getAlbums();
        List artist = mediaManager.getArtists();
        List<Genre> genres = mediaManager.getGenre();
        Log.e("Songs", Integer.toString(songs.size()));
        Log.e("Albums", Integer.toString(albums.size()));
        Log.e("Artist", Integer.toString(artist.size()));
        Log.e("Genres", Integer.toString(genres.size()));
        Genre genre = genres.get(0);
        String[] audioIds = mediaManager.getGenreAudioIds(genre.getId());
        Log.e("Genres-Audio", Integer.toString(audioIds.length));
        List audio = mediaManager.getAudio(audioIds);
        Log.e(genre.getTitle(), Integer.toString(audio.size()));
    }
}