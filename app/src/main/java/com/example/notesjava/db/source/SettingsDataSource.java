package com.example.notesjava.db.source;

import com.example.notesjava.db.Settings;

public interface SettingsDataSource {


    interface ConfigurationCallBack {

        void getConfigurationDetails(int brightness, boolean torch, String wifiName);

    }


    void setWifiName(String wifiName);

    void setTorchState(boolean state);

    void setBrightness(long brightness);

    void getConfigurations(ConfigurationCallBack callBack);

}
