package com.fruitstudio.healingbytips.View.TimKiem;

import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitstudio.healingbytips.Adapter.AdapterRecyclerBenh;
import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Presenter.TimKiem.XuLyTimKiem;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallBackLayDanhSachBenh;

import java.util.List;

public class TimKiemActivity extends AppCompatActivity implements ViewTimKiem{

    EditText edTimKiem;
    RecyclerView recyclerTimKiem;
    XuLyTimKiem xuLyTimKiem;
    TextView tvChuaCoDuLieu;
    ImageView imBackTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        edTimKiem = (EditText) findViewById(R.id.edTimKiem);
        recyclerTimKiem = (RecyclerView) findViewById(R.id.recyclerTimKiem);
        tvChuaCoDuLieu = (TextView) findViewById(R.id.tvChuaCoDuLieu);
        imBackTimKiem = (ImageView) findViewById(R.id.imBackTimKiem);

        edTimKiem.getBackground().setColorFilter(getIdColor(R.color.colorWhile), PorterDuff.Mode.SRC_ATOP);


        xuLyTimKiem = new XuLyTimKiem(this);

        edTimKiem.setHintTextColor(getIdColor(R.color.colorWhile));
        edTimKiem.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String keyword = edTimKiem.getText().toString();
                    String keywordCap = keyword.substring(0, 1).toUpperCase() + keyword.substring(1);
                    xuLyTimKiem.XuLyTimKiem(keywordCap);
                    return true;
                }
                return false;
            }
        });

        imBackTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private int getIdColor(int idcolor) {
        int color = 0;
        if (Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this, idcolor);
        } else {
            color = getResources().getColor(idcolor);
        }
        return color;
    }

    @Override
    public void TimKiemThanhCong(List<BenhModel> benhModelList) {
        recyclerTimKiem.setVisibility(View.VISIBLE);
        AdapterRecyclerBenh adapterRecyclerBenh = new AdapterRecyclerBenh(TimKiemActivity.this,R.layout.layout_custom_benh,benhModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TimKiemActivity.this);
        recyclerTimKiem.setLayoutManager(layoutManager);
        recyclerTimKiem.setAdapter(adapterRecyclerBenh);
        adapterRecyclerBenh.notifyDataSetChanged();

        tvChuaCoDuLieu.setVisibility(View.GONE);
    }

    @Override
    public void TimKiemThatBai() {
        tvChuaCoDuLieu.setVisibility(View.VISIBLE);
        recyclerTimKiem.setVisibility(View.GONE);
    }
}
