package com.example.project_sci.ui.home_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.support.v4.app.Fragment;

import com.example.project_sci.R;


public class Home_pagefragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_page, container, false);
        final ScrollView textView = root.findViewById(R.id.text_home);
        return root;
    }
}