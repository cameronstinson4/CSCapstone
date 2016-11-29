package com.example.burni.visualizer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;

public class SettingsFragment extends android.app.Fragment {


    private Button resetButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetButton = (Button) getView().findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getSetupManager().deleteFile();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            }
        });
    }
}