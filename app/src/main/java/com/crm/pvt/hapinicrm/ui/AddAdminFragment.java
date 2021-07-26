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
Dialog add,add2,add3;
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
         binding.adddataentryadmincardview.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 add2.show();
             }
         });
        binding.addvideoeditoradmincardview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                add3.show();
            }
        });
         crmadmin();
         dataentryadmin();
         videoeditor();

    }

    private void videoeditor() {
        add3=new Dialog(getContext());
        add3.setContentView(R.layout.videoeditorloginform);
        add3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        add3.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add3.getWindow().getAttributes().gravity= Gravity.TOP;
    }

    private void dataentryadmin() {
        add2=new Dialog(getContext());
        add2.setContentView(R.layout.dataentryloginform);
        add2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        add2.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add2.getWindow().getAttributes().gravity= Gravity.TOP;
    }

    private void crmadmin() {
        add=new Dialog(getContext());
        add.setContentView(R.layout.crmadminform);
        add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        add.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.MATCH_PARENT);
        add.getWindow().getAttributes().gravity= Gravity.TOP;
    }

}