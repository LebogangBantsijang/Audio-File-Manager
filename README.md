# Audio File Manager
[![](https://jitpack.io/v/LebogangBantsijang/Audio-File-Manager.svg)](https://jitpack.io/#LebogangBantsijang/Audio-File-Manager)  ![API](https://img.shields.io/badge/Android-21+-red.svg)

![Logo](https://raw.githubusercontent.com/LebogangBantsijang/Audio-File-Manager/master/profile-image.png)

## Introduction

This library allows you to manage Album, Artist, Genre and Audio Items that are under the [MediaStore.Audio](https://developer.android.com/reference/android/provider/MediaStore.Audio) collection without having to deal with content resolvers or anything along those lines.

## Gettting Started

#### Setup
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

```gradle
dependencies {
    implementation 'com.github.LebogangBantsijang:Android-File-Manager:v1.0.2'
}
```
## Usage

#### Prerequisite
* Before you start, ensure that you have permission to read or write to/from external storage. [Guide](https://developer.android.com/guide/topics/permissions/overview)

#### Example
How to get Audio and Album information from the external storage

```java
MediaManager mediaManager = new MediaManager(context);
List<Audio> audioList = mediaManager.getAudio();
List<Album> albumList = mediaManager.getAlbums();
List<Artist> audioList = mediaManager.getArtists();
List<Genre> albumList = mediaManager.getGenres();

```

### Compatibility
* API 21+

### License
Apache 2.0. See the LICENSE file for details.

### Contribute

Contributions are always welcome!
