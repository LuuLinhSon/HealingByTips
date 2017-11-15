package com.fruitstudio.healingbytips.View.Reminder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.fruitstudio.healingbytips.View.Register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edEmailReminder;
    Button btnNhanLaiMatKhau;
    TextView tvDangKyMoi;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        firebaseAuth = FirebaseAuth.getInstance();

        edEmailReminder = (EditText) findViewById(R.id.edEmailReminder);
        btnNhanLaiMatKhau = (Button) findViewById(R.id.btnNhanLaiMatKhau);
        tvDangKyMoi = (TextView) findViewById(R.id.tvDangKyMoiKP);
        progressDialog = new ProgressDialog(this);

        tvDangKyMoi.setPaintFlags(tvDangKyMoi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvDangKyMoi.setOnClickListener(this);
        btnNhanLaiMatKhau.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnNhanLaiMatKhau:
                if(KiemTraKetNoiMang()){
                    String email = edEmailReminder.getText().toString();
                    Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                    if(email.trim().equals("") || !kiemtraemail){
                        Toast.makeText(ReminderActivity.this,"Email không hợp lệ!",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.setMessage("Vui lòng đợi.....");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ReminderActivity.this,"Vui lòng kiểm tra Email để lấy lại mật khẩu",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent iLogin = new Intent(ReminderActivity.this, LogInActivity.class);
                                    startActivity(iLogin);
                                    finish();
                                }else {
                                    Toast.makeText(ReminderActivity.this,"Đã có lỗi xảy ra.Vui lòng thử lại!!!",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(ReminderActivity.this,"Vui lòng kết nối mạng",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tvDangKyMoiKP:
                Intent iRegister = new Intent(ReminderActivity.this, RegisterActivity.class);
                startActivity(iRegister);
                finish();
                break;
        }
    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ReminderActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
