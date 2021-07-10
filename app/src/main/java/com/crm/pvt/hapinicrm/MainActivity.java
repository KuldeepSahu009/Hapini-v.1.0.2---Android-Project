package com.crm.pvt.hapinicrm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.crm.pvt.hapinicrm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}