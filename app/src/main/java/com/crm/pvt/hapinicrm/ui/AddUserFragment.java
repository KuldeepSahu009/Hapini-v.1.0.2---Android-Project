package com.crm.pvt.hapinicrm.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.crm.pvt.hapinicrm.R;
import com.crm.pvt.hapinicrm.databinding.FragmentAddUserBinding;

public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
    Dialog dialog;
    Button okButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public static String addUserType;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new Dialog(getContext());

       binding.cvAddCRMUser.setOnClickListener(v -> {
           addUserType = "CRM";
           Navigation.findNavController(v).navigate(AddUserFragmentDirections.actionAddUserFragmentToAddUserFormDetailsFragment());
       });
        binding.cvAddDataEntryUser.setOnClickListener(v -> {
                setUpCustomDialogBox();
        });
        binding.cvAddVideoEditorUser.setOnClickListener(v -> {
            setUpCustomDialogBox();
        });
        binding.ivBackFromAddUserFragment.setOnClickListener(v->
                Navigation.findNavController(v).navigateUp());
    }

    private void setUpCustomDialogBox(){
        dialog = new Dialog(dialog.getContext());
        dialog.setContentView(R.layout.custom_dialogue_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}