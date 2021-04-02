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

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lebogang.filemanager.data.Audio;
import com.lebogang.filemanager.managers.AlbumManager;
import com.lebogang.filemanager.managers.ArtistManager;
import com.lebogang.filemanager.managers.AudioManager;
import com.lebogang.filemanager.managers.GenreManager;
import com.lebogang.filemanager.managers.OnMediaContentChanged;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements OnMediaContentChanged {
    private AudioManager audioManager;
    private List<Audio> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initManager();
    }

    //I am going to show you how to use my  library
    //it was made to make your life a little bit easier

    //Now, How to get media files from your device in less than 10 min
    //Go to github and see how to add it, link is in the description

    //before using this library, make sure you have read/write permission to external storage
    private void initManager(){
        audioManager = new AudioManager(this);
        audioManager.registerObserver(this);
        list = audioManager.getAudio();
        Log.e("Total Items", Integer.toString(list.size()));
        //filter
        audioManager.setDurationFilter((1000 *60 *4));//4 min or longer
        list = audioManager.getAudio();
        Log.e("Total Items", Integer.toString(list.size()));
        for (Audio audio:list){
            Log.e("Audio Name - ", audio.getTitle());
        }
    }
    //this might take a while

    //you can do the same thing for albums, artists and genres located in you device
    //

    @Override
    protected void onDestroy() {
        //almost forgot
        audioManager.unregisterObserver();
        super.onDestroy();
    }

    //this is called every time a new item is downloaded or something like that.
    //this library works well with liveData, so I suggest using that with your viewModels
    @Override
    public void onMediaContentChanged() {
        list = audioManager.getAudio();
        //and that's it
    }
}
