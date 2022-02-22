package com.example.medicationreminderapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Test1 extends Fragment {

    private String title;
    public Test1(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test1, container, false);
        TextView txt = root.findViewById(R.id.txtAon);
        txt.setText(title);
        return root;
    }
}
