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

package com.lebogang.managermodule.data;

import android.net.Uri;

public class Genre {
    private final long id;
    private final String title;
    private final Uri contentUri;

    private Genre(long id, String title, Uri contentUri) {
        this.id = id;
        this.title = title;
        this.contentUri = contentUri;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public static class Builder{
        private long id;
        private String title;
        private Uri contentUri;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentUri(Uri contentUri) {
            this.contentUri = contentUri;
            return this;
        }

        public Genre build(){
            return new Genre(id, title, contentUri);
        }
    }
}
