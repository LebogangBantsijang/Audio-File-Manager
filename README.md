# Audio File Manager
[![](https://jitpack.io/v/LebogangBantsijang/Android-File-Manager.svg)](https://jitpack.io/#LebogangBantsijang/Android-File-Manager)  ![API](https://img.shields.io/badge/Android-21+-red.svg)

![Logo](https://raw.githubusercontent.com/LebogangBantsijang/Audio-File-Manager/master/profile-image.png)

## Introduction

This library allows you to manage Albums, Artists, Genres and Audio that are under the [MediaStore.Audio](https://developer.android.com/reference/android/provider/MediaStore.Audio) collection without having to deal with content resolvers or anything along those lines.

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
How to get Audio items from external storage. 

```java
AudioManager audioManager = new AudioManager(context);
List<Audio> list = audioManager.getAudio();

```

For a video example, please click [here](https://youtu.be/T1jc-OqviLMw)

#### Future plans
I will include images and videos in to this project when i get the time lol.

### Compatibility
* API 21+

### License
Apache 2.0. See the LICENSE file for details.

