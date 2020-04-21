package com.example.aotedan.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.R;

public class PersonFragment extends Fragment {

    private PersonViewModel PersonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersonViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_person, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        PersonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
