package com.fruitstudio.healingbytips.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentBaBau;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentCoXuongKhop;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentHoHap;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentKhac;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentMat;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentRangHamMat;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentSinhDuc;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentTaiMuiHong;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentThanKinh;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentTieuHoa;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentTimMach;
import com.fruitstudio.healingbytips.View.Main.FragmentMain.FragmentTreSoSinh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/27/2017.
 */

public class AdapterViewPager extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> tenFragmentList = new ArrayList<String>();

    public AdapterViewPager(FragmentManager fm) {
        super(fm);

        fragmentList.add(new FragmentThanKinh());
        fragmentList.add(new FragmentHoHap());
        fragmentList.add(new FragmentTieuHoa());
        fragmentList.add(new FragmentTimMach());
        fragmentList.add(new FragmentCoXuongKhop());
        fragmentList.add(new FragmentSinhDuc());
        fragmentList.add(new FragmentRangHamMat());
        fragmentList.add(new FragmentTaiMuiHong());
//        fragmentList.add(new FragmentMat());
        fragmentList.add(new FragmentBaBau());
        fragmentList.add(new FragmentTreSoSinh());
//        fragmentList.add(new FragmentKhac());

        tenFragmentList.add("Thần kinh");
        tenFragmentList.add("Hô hấp");
        tenFragmentList.add("Tiêu hóa");
        tenFragmentList.add("Tim mạch");
        tenFragmentList.add("Cơ Xương Khớp");
        tenFragmentList.add("Sinh dục");
        tenFragmentList.add("Răng hàm mặt");
        tenFragmentList.add("Tai mũi họng");
//        tenFragmentList.add("Mắt");
        tenFragmentList.add("Bà bầu");
        tenFragmentList.add("Trẻ sơ sinh");
//        tenFragmentList.add("Các bệnh khác");

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
