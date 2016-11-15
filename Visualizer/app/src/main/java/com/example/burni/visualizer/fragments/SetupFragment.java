package com.example.burni.visualizer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burni.visualizer.R;
import com.example.burni.visualizer.SetupActivity;

public class SetupFragment extends android.app.Fragment {

    private Context context;
    private ProgressDialog loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        Intent i = new Intent(context, SetupActivity.class);
        startActivity(i);
        return inflater.inflate(R.layout.fragment_setup, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}