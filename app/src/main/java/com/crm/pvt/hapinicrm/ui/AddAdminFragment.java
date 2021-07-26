package com.crm.pvt.hapinicrm.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAddAdminBinding;

public class AddAdminFragment extends Fragment {
Dialog add;
    private FragmentAddAdminBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         binding.addcrmadmincardview.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                add.show();
                                                            }
                                                        }
                 );
         crmadmin();
    }

    private void crmadmin() {
        add = new Dialog(getContext());
        //add.setContentView(R.layout.crmadminform);
        add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        add.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add.getWindow().getAttributes().gravity= Gravity.TOP;
    }

}