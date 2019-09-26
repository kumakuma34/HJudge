package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logIn extends AppCompatActivity {

    Button signInBtn = null;
    Button logInBtn = null;
    EditText idText = null;
    EditText pwText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signInBtn = (Button) findViewById(R.id.signInBtn);
        logInBtn = (Button) findViewById(R.id.logInBtn);
        idText = (EditText) findViewById(R.id.userID);
        pwText = (EditText) findViewById(R.id.userPW);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signIn.class);
                startActivity(intent);
            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sID = idText.getText().toString();
                String sPW = pwText.getText().toString();

                if (sID.length() == 0 || sPW.length() == 0) { //값 없는경우 안넘어갈 것
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), main.class);
                    intent.putExtra("userID", sID);
                    startActivity(intent);
                }
            }
        });

    }
}
