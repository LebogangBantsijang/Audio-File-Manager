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

package com.lebogang.managermodule.connectors.helpers;

import android.database.ContentObserver;

import com.lebogang.managermodule.data.Genre;

import java.util.List;

public interface GenreDatabaseInterface {

    List<Genre> getGenre();

    List<Genre> getGenre(String name);

    String[] getGenreAudioIds(long id);

    void observeGenreChanges(ContentObserver contentObserver);

    void observeGenreAudioChanges(long genreId,ContentObserver contentObserver);

    void stopGenreObserving();

    void stopGenreAudioObserving();
}
