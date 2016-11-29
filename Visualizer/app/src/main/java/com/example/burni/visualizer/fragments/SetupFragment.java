package com.example.burni.visualizer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.example.burni.visualizer.SetupActivity;
import com.example.burni.visualizer.SetupManager;

public class SetupFragment extends android.app.Fragment {

    private Button setupButton;
    private EditText serverWebAddressField;
    private EditText operationNameField;
    private EditText serverPinField;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //context = container.getContext();
        //Intent i = new Intent(context, SetupActivity.class);
        //startActivity(i);
        return inflater.inflate(R.layout.fragment_setup, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButton = (Button) getView().findViewById(R.id.setupButton);
        setupButton.setEnabled(false);
        serverWebAddressField = (EditText) getView().findViewById(R.id.serverWebAddressField);
        operationNameField = (EditText) getView().findViewById(R.id.operationNameField);
        serverPinField = (EditText) getView().findViewById(R.id.serverPinField);

        serverWebAddressField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!serverWebAddressField.getText().toString().trim().isEmpty()
                        && !operationNameField.getText().toString().trim().isEmpty()
                        && !serverPinField.getText().toString().trim().isEmpty())
                    setupButton.setEnabled(true);
                else
                    setupButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        operationNameField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!serverWebAddressField.getText().toString().trim().isEmpty()
                        && !operationNameField.getText().toString().trim().isEmpty()
                        && !serverPinField.getText().toString().trim().isEmpty())
                    setupButton.setEnabled(true);
                else
                    setupButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        serverPinField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!serverWebAddressField.getText().toString().trim().isEmpty()
                        && !operationNameField.getText().toString().trim().isEmpty()
                        && !serverPinField.getText().toString().trim().isEmpty())
                    setupButton.setEnabled(true);
                else
                    setupButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final SetupManager setupManager = ((MainActivity) getActivity()).getSetupManager();

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupManager.setup(serverWebAddressField.getText().toString(), operationNameField.getText().toString(), serverPinField.getText().toString());

                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

            }
        });

        if (setupManager.isSetup()) {
            serverWebAddressField.setText(setupManager.getUrl());
            operationNameField.setText(setupManager.getOperationName());
            serverPinField.setText(setupManager.getPin());
        }
    }
}