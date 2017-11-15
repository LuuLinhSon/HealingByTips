package com.fruitstudio.healingbytips.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh.FragmentBinhLuan;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh.FragmentNoiDungBenh;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentKhac;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentTreSoSinh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/19/2017.
 */

public class AdapterViewPagerChiTietBenh extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> tenFragmentList = new ArrayList<String>();

    public AdapterViewPagerChiTietBenh(FragmentManager fm) {
        super(fm);

        fragmentList.add(new FragmentNoiDungBenh());
        fragmentList.add(new FragmentBinhLuan());

        tenFragmentList.add("Nội dung");
        tenFragmentList.add("Bình luận");
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tenFragmentList.get(position);
    }
}
