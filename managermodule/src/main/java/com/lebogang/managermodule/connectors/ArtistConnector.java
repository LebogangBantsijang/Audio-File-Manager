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

package com.lebogang.managermodule.connectors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Artists;

import com.lebogang.managermodule.connectors.helpers.ArtistDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.ConnectorTools;
import com.lebogang.managermodule.data.Artist;
import com.lebogang.managermodule.data.helpers.UriHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static android.provider.MediaStore.Audio.Artists.ARTIST;
import static android.provider.MediaStore.Audio.Artists.NUMBER_OF_ALBUMS;
import static android.provider.MediaStore.Audio.Artists.NUMBER_OF_TRACKS;

public class ArtistConnector implements ArtistDatabaseInterface {
    private final ContentResolver contentResolver;
    private ContentObserver contentObserver;

    public ArtistConnector(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Artist> getArtists() {
        Cursor cursor = contentResolver.query(ConnectorTools.ARTIST_EXTERNAL_URI, ConnectorTools.ARTIST_PROJECTION
                , null, null, ConnectorTools.DEFAULT_ARTIST_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Artist> getArtists(long id) {
        Cursor cursor = contentResolver.query(ConnectorTools.ARTIST_EXTERNAL_URI, ConnectorTools.ARTIST_PROJECTION
                , Artists._ID + "=?", new String[]{Long.toString(id)}, ConnectorTools.DEFAULT_ARTIST_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public List<Artist> getArtists(String name) {
        Cursor cursor = contentResolver.query(ConnectorTools.ARTIST_EXTERNAL_URI, ConnectorTools.ARTIST_PROJECTION
                , ARTIST + "=?", new String[]{name}, ConnectorTools.DEFAULT_ARTIST_SORT_ORDER);
        return iterateCursor(cursor);
    }

    @Override
    public int updateArtist(long id, ContentValues values) {
        return contentResolver.update(ConnectorTools.ARTIST_EXTERNAL_URI, values
                , Artists._ID + "=?", new String[]{Long.toString(id)});
    }

    @Override
    public void observeArtistChanges(ContentObserver contentObserver) {
        this.contentObserver = contentObserver;
        contentResolver.registerContentObserver(ConnectorTools.ARTIST_EXTERNAL_URI
                , true, contentObserver);
    }

    @Override
    public void stopArtistObserving() {
        contentResolver.unregisterContentObserver(contentObserver);
    }

    private List<Artist> iterateCursor(Cursor cursor){
        if (cursor == null){
            return Collections.emptyList();
        }
        LinkedHashMap<String, Artist> artistLinkedHashMap = new LinkedHashMap<>();
        if (cursor.moveToFirst()){
            do {
                long id = cursor.getLong(cursor.getColumnIndex(Artists._ID));
                Artist artist = new Artist.Builder()
                        .setId(id)
                        .setArtistName(cursor.getString(cursor.getColumnIndex(ARTIST)))
                        .setNumberOfAlbums(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_ALBUMS)))
                        .setNumberOfSongs(cursor.getInt(cursor.getColumnIndex(NUMBER_OF_TRACKS)))
                        .setContentUri(UriHelper.createContentUri(ConnectorTools.ARTIST_EXTERNAL_URI, id))
                        .build();
                artistLinkedHashMap.put(artist.getArtistName(), artist);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return new ArrayList<>(artistLinkedHashMap.values());
    }
}
