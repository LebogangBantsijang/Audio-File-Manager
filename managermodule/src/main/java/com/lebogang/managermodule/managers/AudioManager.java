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

package com.lebogang.managermodule.managers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.lebogang.managermodule.connectors.AlbumConnector;
import com.lebogang.managermodule.connectors.ArtistConnector;
import com.lebogang.managermodule.connectors.AudioConnector;
import com.lebogang.managermodule.connectors.GenreConnector;
import com.lebogang.managermodule.connectors.helpers.AlbumDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.ArtistDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.AudioDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.ConnectorTools;
import com.lebogang.managermodule.connectors.helpers.GenreDatabaseInterface;
import com.lebogang.managermodule.connectors.helpers.SortTypes;
import com.lebogang.managermodule.data.Album;
import com.lebogang.managermodule.data.Artist;
import com.lebogang.managermodule.data.Audio;
import com.lebogang.managermodule.data.Genre;

import java.util.List;

public class AudioManager implements AudioDatabaseInterface, AlbumDatabaseInterface, ArtistDatabaseInterface, GenreDatabaseInterface {
    private final AudioConnector audioConnector;
    private final AlbumConnector albumConnector;
    private final ArtistConnector artistConnector;
    private final GenreConnector genreConnector;

    public AudioManager(Context applicationContext) {
        ContentResolver contentResolver = applicationContext.getApplicationContext().getContentResolver();
        audioConnector = new AudioConnector(contentResolver);
        albumConnector = new AlbumConnector(contentResolver);
        artistConnector = new ArtistConnector(contentResolver);
        genreConnector = new GenreConnector(contentResolver);
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

    @Override
    public int deleteAudio(long id) {
        return audioConnector.deleteAudio(id);
    }

    @Override
    public int updateAudio(long id, ContentValues values) {
        return audioConnector.updateAudio(id, values);
    }

    @Override
    public void observeAudioChanges(ContentObserver contentObserver) {
        audioConnector.observeAudioChanges(contentObserver);
    }

    @Override
    public void stopAudioObserving() {
        audioConnector.stopAudioObserving();
    }

    @Override
    public List<Album> getAlbums() {
        return albumConnector.getAlbums();
    }

    @Override
    public List<Album> getAlbums(long id) {
        return albumConnector.getAlbums(id);
    }

    @Override
    public List<Album> getAlbums(String albumName) {
        return albumConnector.getAlbums(albumName);
    }

    @Override
    public List<Album> getAlbumsForArtist(String artistName) {
        return albumConnector.getAlbumsForArtist(artistName);
    }

    @Override
    public int updateAlbum(long id, ContentValues values) {
        return albumConnector.updateAlbum(id, values);
    }

    @Override
    public List<Artist> getArtists() {
        return artistConnector.getArtists();
    }

    @Override
    public List<Artist> getArtists(long id) {
        return artistConnector.getArtists(id);
    }

    @Override
    public List<Artist> getArtists(String name) {
        return artistConnector.getArtists(name);
    }

    @Override
    public int updateArtist(long id, ContentValues values) {
        return artistConnector.updateArtist(id, values);
    }

    @Override
    public void observeArtistChanges(ContentObserver contentObserver) {
        artistConnector.observeArtistChanges(contentObserver);
    }

    @Override
    public void stopArtistObserving() {
        artistConnector.stopArtistObserving();
    }

    @Override
    public void observeAlbumChanges(ContentObserver contentObserver) {
        albumConnector.observeAlbumChanges(contentObserver);
    }

    @Override
    public void stopAlbumObserving() {
        albumConnector.stopAlbumObserving();
    }

    @Override
    public List<Genre> getGenre() {
        return genreConnector.getGenre();
    }

    @Override
    public List<Genre> getGenre(String name) {
        return genreConnector.getGenre(name);
    }

    @Override
    public String[] getGenreAudioIds(long id) {
        return genreConnector.getGenreAudioIds(id);
    }

    @Override
    public void observeGenreChanges(ContentObserver contentObserver) {
        genreConnector.observeGenreChanges(contentObserver);
    }

    @Override
    public void observeGenreAudioChanges(long genreId, ContentObserver contentObserver) {
        genreConnector.observeGenreAudioChanges(genreId, contentObserver);
    }

    @Override
    public void stopGenreObserving() {
        genreConnector.stopGenreObserving();
    }

    @Override
    public void stopGenreAudioObserving() {
        genreConnector.stopGenreAudioObserving();
    }

    /**
     * if you want to change the order, call this method before all other methods
     * @param types: see {@link SortTypes}
     * @param order: {@link android.provider.MediaStore} fields with " ASC" or " DESC"
     *             e.g. MediaStore.Audio.Media.TITLE + " ASC"
     * */
    public void setSortOrder(SortTypes types, String order){
        switch (types){
            case SORT_AUDIO:
                ConnectorTools.DEFAULT_AUDIO_SORT_ORDER = order;
                break;
            case SORT_ALBUMS:
                ConnectorTools.DEFAULT_ALBUM_SORT_ORDER = order;
                break;
            case SORT_ARTIST:
                ConnectorTools.DEFAULT_ARTIST_SORT_ORDER = order;
                break;
            case SORT_GENRES:
                ConnectorTools.DEFAULT_GENRE_SORT_ORDER = order;
                break;
        }
    }
}
