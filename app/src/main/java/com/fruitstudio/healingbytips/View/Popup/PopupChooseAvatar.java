package com.fruitstudio.healingbytips.View.Popup;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.fruitstudio.healingbytips.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class PopupChooseAvatar extends AppCompatActivity {

    ListView lvCamera;
    List<String> lisCamera;
    private static final int RESQUEST_TAKE_PHOTO = 123;
    private static final int RESQUEST_CHOOSE_PHOTO = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_choose_avatar);

        lvCamera = (ListView) findViewById(R.id.lvPopup);
        setTitle("Select");

        lisCamera = new ArrayList<>();
        lisCamera.add("Camera");
        lisCamera.add("Gallery");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lisCamera);
        lvCamera.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvCamera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    takePicture();
                } else {
                    choosePicture();
                }

            }
        });
    }


    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void choosePicture() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);

        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESQUEST_CHOOSE_PHOTO);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Log.d("bitmap", "onActivityResult: " + bitmap.toString());
//                  Bitmap bm = Bitmap.createScaledBitmap(bitmap,500,600,false); // Resize lại ảnh cho không bị lỗi OutOfBound
                    Intent iGallery = getIntent();
                    iGallery.putExtra("hinhanh", bitmap);
                    setResult(Activity.RESULT_OK, iGallery);
                    finish();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//              Bitmap bm1 = Bitmap.createScaledBitmap(bitmap,500,600,false);// Resize lại ảnh cho không bị lỗi OutOfBound
                Intent iCamera = getIntent();
                iCamera.putExtra("hinhanh", bitmap);
                setResult(Activity.RESULT_OK, iCamera);
                finish();
            }
        }
    }
}
