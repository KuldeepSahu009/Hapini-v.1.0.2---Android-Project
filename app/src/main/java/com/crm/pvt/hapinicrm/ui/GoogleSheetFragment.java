package com.crm.pvt.hapinicrm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.Splashscreen;
import com.crm.pvt.hapinicrm.databinding.FragmentGoogleSheetBinding;


public class GoogleSheetFragment extends Fragment {
    private FragmentGoogleSheetBinding bindings;
    private WebView wvGoogleSheets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindings = FragmentGoogleSheetBinding.inflate(inflater , container , false);
        return bindings.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindings.ivBackFromGoogleSheetFrag.setOnClickListener(v->
                Navigation.findNavController(v).navigateUp());
        wvGoogleSheets = bindings.wvGoogleSheet;
        wvGoogleSheets.getSettings().setJavaScriptEnabled(true);
        wvGoogleSheets.loadUrl("https://docs.google.com/spreadsheets/d/1jM0EnmyCYlSO9YRLHk-gJetdtC54DEJpPKGU_LK42Vc/edit#gid=0");
    }
}