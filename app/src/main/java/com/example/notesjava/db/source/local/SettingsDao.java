package com.example.notesjava.db.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesjava.db.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConfiguration(Settings configs);

    @Query("update settings set mBrightNess=:brightness where mId=1")
    void updateBrightness(long brightness);

    @Query("update settings set mWifi=:wifi where mId=1")
    void updateWifi(String wifi);

    @Query("update settings set mIsFlashOn=:torchStatus where mId=1")
    void updateTorch(boolean torchStatus);


    @Query("select * from settings")
    List<Settings> getAllSettings();
}
