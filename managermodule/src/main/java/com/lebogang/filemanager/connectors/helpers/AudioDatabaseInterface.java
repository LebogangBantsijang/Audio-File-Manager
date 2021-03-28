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

package com.lebogang.filemanager.connectors.helpers;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.lebogang.filemanager.data.Audio;

import java.util.List;

public interface AudioDatabaseInterface {

    List<Audio> getAudio();

    List<Audio> getAudio(long id);

    List<Audio> getAudio(@NonNull String name);

    List<Audio> getAudio(@NonNull Uri uri);

    List<Audio> getAudio(@NonNull String[] audioIds);

    List<Audio> getAudioAboveDuration(long duration);

    int deleteAudio(long id);

    int updateAudio(long id, ContentValues values);

    void observeAudioChanges(ContentObserver contentObserver);

    void stopAudioObserving();

}
