package com.example.notesjava.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.example.notesjava.MainActivity;
import com.example.notesjava.R;
import com.example.notesjava.databinding.FragmentSettingsBinding;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.google.common.base.Preconditions.checkNotNull;

public class SettingsFragment extends Fragment implements SettingsContract.Views {


    public SettingsFragment() {
        // Required empty public constructor
    }

    SettingsContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSettingsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!isCanWriteSettings()) {
            requestCanWriteSettings(getActivity());
        } else {
            setListeners();
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        if (isCanWriteSettings()) {
            setListeners();
        }

    }

    void setListeners() {
        binding.seekBar.setOnSeekBarChangeListener(new SeekBarChangedListener());

        binding.flashLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onTorchClicked(isChecked);
            }
        });
    }

    boolean isCanWriteSettings() {
        return Settings.System.canWrite(getContext());
    }

    @Override
    public void torch(boolean status) {
        binding.flashLight.setChecked(status);
        turnOnFlashLight(status);
    }

    @Override
    public void wifi(String wifiNameConnected) {
        binding.selectWifi.setText(wifiNameConnected);
    }

    @Override
    public void brightness(int value) {
        binding.seekBar.setProgress(value);
        setupLight(getContext(), value);

    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }


    class SeekBarChangedListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            presenter.onBrightnessChanged(seekBar.getProgress());
        }
    }

    void setupLight(Context context, Integer light) {
        try {
            int brightnessMode = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
            );
            if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(
                        context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                );
            }
            Settings.System.putInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    light
            );
        } catch (Exception e) {
            Log.e("setupLight", "Exception " + e);
        }
    }

    void requestCanWriteSettings(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, 0);
        } catch (Exception e) {
            Log.e("requestCanWriteSettings", "requestCanWriteSettings " + e);
        }
    }

    void turnOnFlashLight(boolean value) {
        CameraManager camManager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = camManager.getCameraIdList()[0];
            camManager.setTorchMode(cameraId, value);   //Turn ON
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}