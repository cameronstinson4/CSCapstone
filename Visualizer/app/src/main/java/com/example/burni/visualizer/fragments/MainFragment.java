package com.example.burni.visualizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.example.burni.visualizer.SetupManager;

public class MainFragment extends android.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (!((MainActivity) getActivity()).getSetupManager().isSetup()) {
            SetupFragment setFrag= new SetupFragment();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, setFrag, null)
                    .addToBackStack(null)
                    .commit();
        }

        return inflater.inflate(R.layout.fragment_main,container,false);
    }
}