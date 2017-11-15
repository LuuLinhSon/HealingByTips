package com.fruitstudio.healingbytips.View.Main.FragmentMain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fruitstudio.healingbytips.R;

/**
 * Created by Admin on 8/27/2017.
 */

public class FragmentMat extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_mat,container,false);
        return view;
    }
}
