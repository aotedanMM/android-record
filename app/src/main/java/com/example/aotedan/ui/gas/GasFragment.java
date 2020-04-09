package com.example.aotedan.ui.gas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.R;

public class GasFragment extends Fragment {

    private GasViewModel gasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gasViewModel =
                ViewModelProviders.of(this).get(GasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gas, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        GasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
