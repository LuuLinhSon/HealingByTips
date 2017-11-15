package com.fruitstudio.healingbytips.View.Popup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fruitstudio.healingbytips.R;

public class PopupChangeName extends AppCompatActivity {

    EditText edChangName;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_change_name);

        setTitle("Tên mới của bạn");

        edChangName = (EditText) findViewById(R.id.edChangeName);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeName = edChangName.getText().toString();
                if(!changeName.trim().equals("")){
                    Intent intent = getIntent();
                    intent.putExtra("name",changeName);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(PopupChangeName.this,"Vui lòng điền thông tin!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
