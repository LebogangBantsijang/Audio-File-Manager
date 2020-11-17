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

package com.lebogang.audiofilemanager.Models;

import android.os.Parcel;

public class Genre extends Media{
    private final long id;
    private final String name;
    private final String[] audioIds;

    public Genre(long id, String name, String[] audioIds) {
        this.id = id;
        this.name = name;
        this.audioIds = audioIds;
    }

    public String[] getAudioIds() {
        return audioIds;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeStringArray(this.audioIds);
    }

    protected Genre(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.audioIds = in.createStringArray();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
