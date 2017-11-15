package com.fruitstudio.healingbytips.View.LogIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Main.MainActivity;
import com.fruitstudio.healingbytips.View.Register.RegisterActivity;
import com.fruitstudio.healingbytips.View.Reminder.ReminderActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener {

    EditText edEmail, edPassword;
    Button btnDangNhap, btnGoogle, btnFacebook;
    TextView tvQuenMatKhau, tvDangKyMoi;
    GoogleApiClient googleApiClient;
    public static final int REQUEST_SIGIN_GOOGLE = 1111;
    public static int KIEMTRA_PROVIDER_DANG_NHAP = 0;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    LoginManager loginManager;
    List<String> permissionFacebook = Arrays.asList("email", "public_profile");
    String tendangnhap = "";
    String tendangnhapfacebook = "";
    String photoUrl = "";
    SharedPreferences sfDangNhap;
    SharedPreferences.Editor edThongTinDangNhap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();
        loginManager = LoginManager.getInstance();

//        firebaseAuth.signOut();
//        loginManager.logOut();


        callbackManager = CallbackManager.Factory.create();

        edEmail = (EditText) findViewById(R.id.edEmailLogin);
        edPassword = (EditText) findViewById(R.id.edMatKhauLogin);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnGoogle = (Button) findViewById(R.id.btnSignInGoogle);
        btnFacebook = (Button) findViewById(R.id.btnSignInFacebook);
        tvQuenMatKhau = (TextView) findViewById(R.id.tvQuenMatKhau);
        tvDangKyMoi = (TextView) findViewById(R.id.tvDangKyMoi);
        progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);

        btnDangNhap.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        tvQuenMatKhau.setOnClickListener(this);
        tvDangKyMoi.setOnClickListener(this);

        tvQuenMatKhau.setPaintFlags(tvQuenMatKhau.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDangKyMoi.setPaintFlags(tvDangKyMoi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        sfDangNhap = getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
        edThongTinDangNhap = sfDangNhap.edit();

        TaoCliendGoogle();

//         Làm background full màn hình kể cả status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    // Khởi tạo cliend Google dưới thiết bị
    private void TaoCliendGoogle() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhap:
                if (KiemTraKetNoiMang()) {
                    DangNhapEmailPassword();

                } else {
                    Toast.makeText(LogInActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSignInGoogle:
                if (KiemTraKetNoiMang()) {
                    DangNhapGoogle(googleApiClient);

                } else {
                    Toast.makeText(LogInActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnSignInFacebook:
                if (KiemTraKetNoiMang()) {
                    DangNhapFacebook();

                } else {
                    Toast.makeText(LogInActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tvDangKyMoi:
                Intent iRegister = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(iRegister);
                break;
            case R.id.tvQuenMatKhau:
                Intent iReminder = new Intent(LogInActivity.this, ReminderActivity.class);
                startActivity(iReminder);
                break;
        }
    }

    private void DangNhapEmailPassword() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString();
        Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if (email.trim().equals("") || !kiemtraemail) {
            Toast.makeText(LogInActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        } else if (password.trim().equals("")) {
            Toast.makeText(LogInActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Vui lòng đợi.....");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void DangNhapFacebook() {
        loginManager.logInWithReadPermissions(this, permissionFacebook);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANG_NHAP = 2;
                final String tokenId = loginResult.getAccessToken().getToken();
                Log.d("kiemtra", "onSuccess: " + tokenId + " - " + loginResult.getAccessToken().toString());
                String facebookUserId = loginResult.getAccessToken().getUserId();
                Log.d("fbid", "onSuccess: " + facebookUserId);
                photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
                edThongTinDangNhap.putString("avatar",photoUrl);
                edThongTinDangNhap.commit();
                String URLGraphFacebook = "https://graph.facebook.com/" + facebookUserId + "?access_token=" + tokenId;

                StringRequest stringRequest = new StringRequest(URLGraphFacebook, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            tendangnhapfacebook = jsonObject.getString("name");
                            edThongTinDangNhap.putString("tendangnhapfacebook",tendangnhapfacebook);
                            edThongTinDangNhap.commit();
                            ChungThucDangNhapFirebase(tokenId);
                            Log.d("alo", "onResponse: " + tendangnhapfacebook);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LogInActivity.this,"Đã có lỗi trong quá trình đăng nhập",Toast.LENGTH_SHORT).show();

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(LogInActivity.this);
                requestQueue.add(stringRequest);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    // Khởi tạo form đăng nhập của google.Ở đây bạn sẽ điền các thông tin về user và password tài khoản google của bạn
    private void DangNhapGoogle(GoogleApiClient googleApiClient) {
        KIEMTRA_PROVIDER_DANG_NHAP = 1; // Đánh dấu để biết chúng ta đang đâng nhập bằng Google
        Intent iGoogle = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(iGoogle, REQUEST_SIGIN_GOOGLE);
    }

    // Sau khi điền đầy đủ thông tin đăng nhập google thì dữ liệu sẽ được trả về ở hàm onActivityResult.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGIN_GOOGLE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data); // Lấy dữ liệu trả về
                GoogleSignInAccount account = signInResult.getSignInAccount(); // Từ dữ liệu trả về lấy ra account
                String tokenId = account.getIdToken(); // Từ account lấy ra tokenId
                String tendangnhapgoogle = account.getDisplayName();
                edThongTinDangNhap.putString("tendangnhapgoogle",tendangnhapgoogle);
                edThongTinDangNhap.commit();
                if (account.getPhotoUrl() != null) {
                    photoUrl = account.getPhotoUrl().toString();
                    edThongTinDangNhap.putString("avatar",photoUrl);
                    edThongTinDangNhap.commit();
                }
                ChungThucDangNhapFirebase(tokenId);
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Sau khi lấy được tokenId của account ta tiến hành chứng thực tokenId đó đã đăng nhập vào Firebase
    private void ChungThucDangNhapFirebase(String tokenId) {
        if (KIEMTRA_PROVIDER_DANG_NHAP == 1) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId, null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {

                }
            });
        } else if (KIEMTRA_PROVIDER_DANG_NHAP == 2) {
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenId);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    // Hàm lắng nghe sự thay đổi đăng nhập hay đăng xuất của account
    @Override
    public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            edThongTinDangNhap.putString("userid",userId);
            edThongTinDangNhap.commit();
            progressDialog.setMessage("Vui lòng đợi.....");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            for (UserInfo user : firebaseAuth.getCurrentUser().getProviderData()) {
                String userProvider = user.getProviderId();
                if (userProvider.equals("password")) {
                    String firebaseUserId = firebaseUser.getUid();
                    DatabaseReference fd = FirebaseDatabase.getInstance().getReference().child("thanhviens").child(firebaseUserId);

                    fd.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ThanhVienModel thanhvien = dataSnapshot.getValue(ThanhVienModel.class);
                            tendangnhap = thanhvien.getHoten();
                            photoUrl = thanhvien.getHinhanh();
                            final String duongdanhinh = thanhvien.getHinhanh();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien/" + photoUrl);
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    photoUrl = uri.toString();
                                    progressDialog.dismiss();
                                    Intent iTrangChu = new Intent(LogInActivity.this, MainActivity.class);
                                    iTrangChu.putExtra("avatar", photoUrl);
                                    iTrangChu.putExtra("duongdan",duongdanhinh);
                                    iTrangChu.putExtra("tendangnhap", tendangnhap);
                                    iTrangChu.putExtra("kiemtrataikhoandangnhap",1);
                                    startActivity(iTrangChu);
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    break;
                } else if(userProvider.equals("firebase")) {
                    continue;
                }else {
                    String sfTenDangNhapGoogle = sfDangNhap.getString("tendangnhapgoogle","");
                    String sfTenDangNhapFacebook = sfDangNhap.getString("tendangnhapfacebook","");
                    String sfAvatar = sfDangNhap.getString("avatar","");

                    progressDialog.dismiss();
                    Intent iTrangChu = new Intent(LogInActivity.this, MainActivity.class);
                    iTrangChu.putExtra("tendangnhap", sfTenDangNhapGoogle);
                    iTrangChu.putExtra("tendangnhapfacebook",sfTenDangNhapFacebook);
                    iTrangChu.putExtra("avatar", sfAvatar);
                    iTrangChu.putExtra("kiemtrataikhoandangnhap",0);
                    startActivity(iTrangChu);
                    finish();
                    break;
                }

            }
        }
    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) LogInActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
