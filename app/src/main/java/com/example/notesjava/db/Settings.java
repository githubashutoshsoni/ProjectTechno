package com.example.notesjava.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Settings {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int mId;
    @ColumnInfo
    public int mBrightNess;
    @ColumnInfo
    public String mWifi;
    @ColumnInfo
    public boolean mIsFlashOn;

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

    @Ignore
    public Settings(int mBrightNess, String mWifi, boolean mIsFlashOn) {

//        keep 1 because only on row is all we need to have in this table
        this.mId = 1;
        this.mBrightNess = mBrightNess;
        this.mWifi = mWifi;
        this.mIsFlashOn = mIsFlashOn;
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

    public int getBrightNess() {
        return mBrightNess;
    }

    public String getWifi() {
        return mWifi;
    }

    public boolean isIsFlashOn() {
        return mIsFlashOn;
    }
}
