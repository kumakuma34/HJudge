package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class signIn extends AppCompatActivity {
    String[] instituteList = {
            "소속기관", "A대", "B대", "C대", "D대", "E대"
    };
    Spinner insSpinner = null;
    ArrayAdapter<String> instituteAdapter = null;
    EditText idText = null;
    EditText pwText = null;
    EditText pwChkText = null;
    EditText emailTxt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        insSpinner = (Spinner) findViewById(R.id.userInstitute);
        instituteAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                instituteList
        );
        insSpinner.setAdapter(instituteAdapter);
        insSpinner.setSelection(0);

        idText = (EditText) findViewById(R.id.userID);
        pwText = (EditText) findViewById(R.id.userPW);
        pwChkText = (EditText) findViewById(R.id.userChkPW);
        emailTxt = (EditText) findViewById(R.id.userEmail);

        Button signInBtn = (Button) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sInstitute = insSpinner.getSelectedItem().toString();
                String sID = idText.getText().toString();
                String sPW = pwText.getText().toString();
                String sChkPW = pwChkText.getText().toString();
                String sEmail = emailTxt.getText().toString();

                if (sID.length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_LONG).show();
                } else if (sPW.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                } else if (!sChkPW.equals(sPW)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
                } else if (sEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_LONG).show();
                } else if (sInstitute.equals(instituteList[0])) {
                    Toast.makeText(getApplicationContext(), "소속기관을 선택해주세요", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), logIn.class);
                    startActivity(intent);
                }
            }
        });

    }
}
