package com.arife.adnbikerider.AppData.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arife.adnbikerider.R;
import com.arife.adnbikerider.mvp.v.CreateGroup;
import com.github.clans.fab.FloatingActionButton;

import es.dmoral.toasty.Toasty;

public class FragmentGroups extends Fragment implements View.OnClickListener {
    private View v;
    private FloatingActionButton create_group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //FragmentGroups fragmentGroups = new FragmentGroups();
        this.v = inflater.inflate(R.layout.groups_fragment, container, false);
        this.create_group = v.findViewById(R.id.create_group);
        this.create_group.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_group:

                break;
        }
    }
}
