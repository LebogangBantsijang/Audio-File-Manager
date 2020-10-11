package com.lebogang.audiofilemanager.Models;

import android.os.Parcelable;

public abstract class Media implements Parcelable {

    public abstract long getId();

    public abstract String  getTitle();
}
