package com.example.notesjava.settings;

import com.example.notesjava.BasePresenter;
import com.example.notesjava.BaseView;
import com.example.notesjava.db.source.SettingsDataSource.*;

public interface SettingsContract {


    interface Presenter extends BasePresenter {


        void onTorchClicked(boolean status);

        void onBrightnessChanged(long value);

        void onWifiClicked(String wifiName);

        void getConfigurationDetails();


    }

    interface Views extends BaseView<Presenter> {

        void torch(boolean status);

        void wifi(String wifiNameConnected);

        void brightness(int value);

    }

}
