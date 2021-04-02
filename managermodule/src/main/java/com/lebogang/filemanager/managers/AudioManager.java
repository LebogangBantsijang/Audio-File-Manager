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

package com.lebogang.filemanager.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lebogang.filemanager.connectors.AudioConnector;
import com.lebogang.filemanager.connectors.helpers.AudioDatabaseInterface;
import com.lebogang.filemanager.connectors.helpers.ConnectorTools;
import com.lebogang.filemanager.data.Audio;

import java.util.Collection;
import java.util.List;

public class AudioManager implements AudioDatabaseInterface, OptionsInterface {
    private OnMediaContentChanged onMediaContentChanged;
    private final AudioConnector audioConnector;

    public AudioManager(@NonNull Context context) {
        audioConnector = new AudioConnector(context.getApplicationContext().getContentResolver());
    }

    @Override
    public List<Audio> getAudio() {
        return audioConnector.getAudio();
    }

    @Override
    public List<Audio> getAudio(long id) {
        return audioConnector.getAudio(id);
    }

    @Override
    public List<Audio> getAudio(@NonNull String name) {
        return audioConnector.getAudio(name);
    }

    @Override
    public List<Audio> getAudio(@NonNull Uri uri) {
        return audioConnector.getAudio(uri);
    }

    @Override
    public List<Audio> getAudio(@NonNull String[] audioIds) {
        return audioConnector.getAudio(audioIds);
    }

    @Override
    public List<Audio> getAudioAboveDuration(long duration) {
        return audioConnector.getAudioAboveDuration(duration);
    }

    /**
     * @return : see {@link android.content.ContentResolver#delete(Uri, Bundle)}}
     * */
    @Override
    public int deleteAudio(long id) {
        return audioConnector.deleteAudio(id);
    }

    /**
     * @return : see {@link android.content.ContentResolver#update(Uri, ContentValues, Bundle)}
     * */
    @Override
    public int updateAudio(long id, ContentValues values) {
        return audioConnector.updateAudio(id, values);
    }

    public void registerObserver(@NonNull OnMediaContentChanged onMediaContentChanged) {
        this.onMediaContentChanged = onMediaContentChanged;
        audioConnector.registerObserver(getContentObserver());
    }

    public void unregisterObserver() {
        audioConnector.unregisterObserver();
    }

    private ContentObserver getContentObserver(){
        return new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri, int flags) {
                onMediaContentChanged.onMediaContentChanged();
            }

            @Override
            public void onChange(boolean selfChange, @NonNull Collection<Uri> uris, int flags) {
                onMediaContentChanged.onMediaContentChanged();
            }
        };
    }

    @Override
    public void setSortOrder(String order) {
        ConnectorTools.DEFAULT_AUDIO_SORT_ORDER = order;
    }

    public void setDurationFilter(long durationFilter){
        ConnectorTools.DEFAULT_AUDIO_DURATION_FILTER = durationFilter;
    }
}
