# Audio-File-Manager
[![](https://jitpack.io/v/LebogangBantsijang/Audio-File-Manager.svg)](https://jitpack.io/#LebogangBantsijang/Audio-File-Manager) ![API](https://img.shields.io/badge/Android-21+-red.svg) ![](https://img.shields.io/badge/Latest-1.0.0-blue.svg)

![Logo](https://raw.githubusercontent.com/LebogangBantsijang/Audio-File-Manager/master/profile-image.png)

## Introduction

This library allows you to manage local audio files on android devices withot having to deal with content resolvers or anything along those lines. This includes Albums, Artists, Playlists and Genre Items that are under the [MediaStore.Audio](https://developer.android.com/reference/android/provider/MediaStore.Audio) collection.

## Gettting Started
Before you get started, take a look at a working example of this library [Here.](https://youtu.be/SJoQsasNBAQ)
#### Setup
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    implementation 'com.github.LebogangBantsijang:Audio-File-Manager:1.0.0'
}
```
## Usage

#### Prerequisite
* Before you start ensure that you have permission to read or write to enternal storage. [Guide](https://developer.android.com/guide/topics/permissions/overview)

#### How do I use Audio-File-Manager?
The following code will collect audio files automatically from the the device moment your Activity or Fragment is created. If there is an update then the onGetAudio will be called
```
AudioFileManger audioFileManger = new AudioFileManger(this /*Context*/, this /*LifeCycle Owner*/);
audioFileManger.registerCallbacks(new AudioCallBacks() {
    @Override
    public void onGetAudio(List<AudioMediaItem> mediaItems) {
        //List of all local file
    }

    @Override
    public void onUpdate(AudioMediaItem mediaItem) {
        //Updated item
    }

    @Override
    public void onRemove(AudioMediaItem mediaItem) {
        //Removed Item
    }
});
```
The following code enables you to manually collect files from the device.
```
AudioFileManger audioFileManger = new AudioFileManger();
audioFileManger.getItems(this); //Returns list of Audio Items
```

### Documentation
Coming Soon.

##### Conditions
* You cannot register callbacks if you have instantiated the AudioFileManager object without the context and lifecyce owner. If you do then an exception will be thrown.
* If you have instantiated the AudioFileManager object without the context and lifecycle owner then every method you call should accept a context in the parameter.

### Compatability
* Minimum Android SDK: Audio-File-Manager v4 requires a minimum API level of 21.

### License
Apache 2.0. See the LICENSE file for details.
