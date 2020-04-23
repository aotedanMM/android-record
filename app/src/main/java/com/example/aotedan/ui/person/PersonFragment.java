package com.example.aotedan.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.R;

public class PersonFragment extends Fragment {
    private View person;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        person = inflater.inflate(R.layout.fragment_person, container, false);
        initView();
        return person;
    }
    private void initView(){
        TextView title_bar = person.findViewById(R.id.title_bar);
        title_bar.setText("人员监测");
    }
}
