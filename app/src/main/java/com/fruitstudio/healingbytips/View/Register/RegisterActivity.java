package com.fruitstudio.healingbytips.View.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;
import com.fruitstudio.healingbytips.Presenter.Register.XuLyDangKyThongTinThanhVien;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements ViewDangKyThongTinThanhVien {

    EditText edEmailRegister, edMatKhauRegister, edNhapLaiMatKhauRegister;
    Button btnDangKy;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    XuLyDangKyThongTinThanhVien xuLyDangKyThongTinThanhVien;
    private String password, email, rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        edEmailRegister = (EditText) findViewById(R.id.edEmailRegister);
        edMatKhauRegister = (EditText) findViewById(R.id.edMatKhauRegister);
        edNhapLaiMatKhauRegister = (EditText) findViewById(R.id.edNhapLaiMatKhauRegister);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        progressDialog = new ProgressDialog(this);

        xuLyDangKyThongTinThanhVien = new XuLyDangKyThongTinThanhVien(this);

        //         Làm background full màn hình kể cả status bar
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(KiemTraKetNoiMang()){
                    final String email = edEmailRegister.getText().toString();
                    String password = edMatKhauRegister.getText().toString();
                    String rePassword = edNhapLaiMatKhauRegister.getText().toString();
                    Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

                    if (email.trim().equals("") || !kiemtraemail) {
                        Toast.makeText(RegisterActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    } else if (password.trim().equals("")) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                    } else if (rePassword.trim().equals("") || !rePassword.equals(password)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.setMessage("Vui lòng đợi.....");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        // Đăng ký user bằng email và password
                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();

                                    String userId = task.getResult().getUser().getUid();
                                    ThanhVienModel thanhVien = new ThanhVienModel();
                                    thanhVien.setHoten(email);
                                    thanhVien.setHinhanh("user.png");

                                    xuLyDangKyThongTinThanhVien.XuLyDangKyThongTinThanhVien(userId, thanhVien);

                                    progressDialog.dismiss();
                                    Intent iDangNhap = new Intent(RegisterActivity.this, LogInActivity.class);
                                    startActivity(iDangNhap);
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Email này đã tồn tại.Vui lòng nhập Email mới hoặc sử dụng tính năng Quên mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"Vui lòng kết nối mạng",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void dangKyThanhCong() {

    }

    @Override
    public void dangKyThatBai() {

    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }

}
