package com.example.notesjava.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Settings {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int mId;
    @ColumnInfo
    int mBrightNess;
    @ColumnInfo
    String mWifi;
    @ColumnInfo
    boolean mIsFlashOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return mId == settings.mId &&
                mBrightNess == settings.mBrightNess &&
                mIsFlashOn == settings.mIsFlashOn &&
                Objects.equals(mWifi, settings.mWifi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mBrightNess, mWifi, mIsFlashOn);
    }

    public Settings(int mId, int mBrightNess, String mWifi, boolean mIsFlashOn) {
        this.mId = mId;
        this.mBrightNess = mBrightNess;
        this.mWifi = mWifi;
        this.mIsFlashOn = mIsFlashOn;
    }

    public int getmId() {
        return mId;
    }

    public int getmBrightNess() {
        return mBrightNess;
    }

    public String getmWifi() {
        return mWifi;
    }

    public boolean ismIsFlashOn() {
        return mIsFlashOn;
    }
}
