package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class main extends AppCompatActivity {
    String[] courseArr = {"인터넷", "OS", "임베", "디비", "인터넷", "OS", "임베", "디비", "인터넷",
            "OS", "임베", "디비", "인터넷", "OS", "임베", "디비", "인터넷", "OS", "임베", "디비", "인터넷",
            "OS", "임베", "디비", "인터넷", "OS", "임베", "디비", "인터넷",
            "OS", "임베", "디비", "인터넷", "OS", "임베", "디비", "인터넷", "OS", "임베", "디비"};

    String userID = null;
    TextView tvLogOut = null;
    Button plusBtn = null;
    Button minusBtn = null;
    TextView tvHello = null;
    ScrollView courseScroll = null;
    LinearLayout scrollLinear = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        tvLogOut = (TextView) findViewById(R.id.tvLogOut);
        plusBtn = (Button) findViewById(R.id.plusBtn);
        minusBtn = (Button) findViewById(R.id.minusBtn);
        tvHello = (TextView) findViewById(R.id.tvHello);

        tvHello.setText(userID + "님 안녕하세요");

        courseScroll = (ScrollView) findViewById(R.id.courseScroll);
        scrollLinear = (LinearLayout) findViewById(R.id.scrollLinear);

        TextView[] tvCourse = new TextView[courseArr.length];
        for (int i = 0; i < courseArr.length; i++) {
            tvCourse[i] = new TextView(this);
            tvCourse[i].setText("강의 이름 "+ Integer.toString(i));
            //tvCourse[i].setText(courseArr[i]);
            tvCourse[i].setTextSize(30);
            tvCourse[i].setWidth(scrollLinear.getWidth());
            //  tvCourse[i].setHeight(tvCourse[i].getHeight()*3);
            tvCourse[i].setGravity(Gravity.CENTER);
            scrollLinear.addView(tvCourse[i]);
            tvCourse[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "선택되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), course.class);
                    intent.putExtra("courseName", this.toString()); //넘겨주는 이름 어케 바꿀지 생각하기
                    startActivity(intent);
                }
            });
        }

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), logIn.class);
                startActivity(intent);
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //삭제 구현하기
                Toast.makeText(getApplicationContext(), "-", Toast.LENGTH_LONG).show();
            }
        });

    }
}
