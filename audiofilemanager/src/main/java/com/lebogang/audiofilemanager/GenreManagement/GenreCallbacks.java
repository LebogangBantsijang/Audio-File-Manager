/*
 * Copyright (c) 2020. Lebogang Bantsijng
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
