package com.example.notesjava.db.source.local.DataSource;

import com.example.notesjava.db.Settings;
import com.example.notesjava.db.source.SettingsDataSource;
import com.example.notesjava.db.source.local.SettingsDao;
import com.example.notesjava.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingLocalDataSource implements SettingsDataSource {


    private AppExecutors appExecutors;
    private SettingsDao settingsDao;


    private static volatile SettingLocalDataSource INSTANCE = null;

    public SettingLocalDataSource(AppExecutors appExecutors, SettingsDao settingsDao) {
        this.appExecutors = checkNotNull(appExecutors);
        this.settingsDao = checkNotNull(settingsDao);
    }

    public static SettingLocalDataSource
    newInstance(AppExecutors appExecutors,
                SettingsDao settingsDao) {

        if (INSTANCE == null) {

            INSTANCE = new SettingLocalDataSource(appExecutors, settingsDao);

        }

        return INSTANCE;

    }


    @Override
    public void setWifiName(String wifiName) {

        appExecutors.diskIO().execute(() -> settingsDao.updateWifi(wifiName));

    }

    @Override
    public void setTorchState(boolean state) {


        appExecutors.diskIO().execute(() -> settingsDao.updateTorch(state));


    }

    @Override
    public void setBrightness(long brightness) {
        appExecutors.diskIO().execute(() -> settingsDao.updateBrightness(brightness));
    }

    @Override
    public void getConfigurations(ConfigurationCallBack callBack) {

        appExecutors.diskIO().execute(() -> {

            List<Settings> settingsList = settingsDao.getAllSettings();
            Settings settings;
//                if the list is empty initialize it with some default value and put inside the table..
            if (settingsList.isEmpty()) {
                settings = new Settings(100, "Jai Shree Ram", false);
                settingsDao.insertConfiguration(settings);
            } else {
                settings = settingsList.get(0);
            }

            appExecutors.mainThread().execute(() -> callBack.getConfigurationDetails(
                    settings.getBrightNess(),
                    settings.isIsFlashOn(),
                    settings.getWifi()));

        });

    }
}
