package com.example.notesjava.settings;

import com.example.notesjava.db.source.SettingsDataSource;
import com.example.notesjava.db.source.local.DataSource.SettingLocalDataSource;
import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingsPresenter implements SettingsContract.Presenter {

    SettingsDataSource dataSource;
    SettingsContract.Views views;

    public SettingsPresenter(SettingLocalDataSource dataSource, SettingsContract.Views views) {
        this.dataSource = checkNotNull(dataSource);
        this.views = checkNotNull(views);
        this.views.setPresenter(this);
    }

    @Override
    public void onTorchClicked(boolean status) {

        dataSource.setTorchState(status);
        getConfigurationDetails();
    }

    @Override
    public void onBrightnessChanged(long value) {

        dataSource.setBrightness(value);
        getConfigurationDetails();
    }

    @Override
    public void onWifiClicked(String wifiName) {
        dataSource.setWifiName(wifiName);
        getConfigurationDetails();
    }

    @Override
    public void getConfigurationDetails() {

        dataSource.getConfigurations(new SettingsDataSource.ConfigurationCallBack() {
            @Override
            public void getConfigurationDetails(int brightness, boolean torch, String wifiName) {
                views.brightness(brightness);
                views.torch(torch);
                views.wifi(wifiName);
            }
        });

    }

    @Override
    public void start() {
        getConfigurationDetails();
    }
}
