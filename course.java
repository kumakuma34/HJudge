package com.capstone.hjudge;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class course extends AppCompatActivity {

    String[][] examList = {{"퀴즈", "1", "2", "3", "4"}, {"퀴즈", "1", "2", "3", "4"},
            {"퀴즈", "1", "2", "3", "4"}, {"퀴즈", "1", "2", "3", "4"},
            {"퀴즈", "1", "2", "3", "4"}, {"퀴즈", "1", "2", "3", "4"},
            {"퀴즈", "1", "2", "3", "4"}, {"퀴즈", "1", "2", "3", "4"},
            {"퀴즈", "1", "2", "3", "4"}, {"퀴즈", "1", "2", "3", "4"}};

    String courseName = null;
    TextView tvCourse = null;
    Button sheetRegBtn = null;
    Button examRegBtn = null;
    ImageButton undoBtn = null;

    ScrollView examScroll = null;
    LinearLayout scrollLinear = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        tvCourse = (TextView) findViewById(R.id.tvCourse);
        sheetRegBtn = (Button) findViewById(R.id.sheetRegBtn);
        examRegBtn = (Button) findViewById(R.id.examRegBtn);
        undoBtn = (ImageButton) findViewById(R.id.undoBtn);

        // tvCourse.setText(courseName);
        tvCourse.setText("Course");

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });
        sheetRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "선택되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), examSheetInput.class);
                intent.putExtra("problem", this.toString()); //넘겨주는 이름 어케 바꿀지 생각하기
                startActivity(intent);
            }
        });
        examRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                examSet();
            }
        });

        examScroll = (ScrollView) findViewById(R.id.examScroll);
        scrollLinear = (LinearLayout) findViewById(R.id.scrollLinear);

        TextView[] tvExam = new TextView[examList.length];
        for (int i = 0; i < examList.length; i++) {
            tvExam[i] = new TextView(this);
            tvExam[i].setText(examList[i][0] + Integer.toString(i));
            tvExam[i].setTextSize(30);
            tvExam[i].setGravity(Gravity.CENTER);
            tvExam[i].setWidth(scrollLinear.getWidth());
            scrollLinear.addView(tvExam[i]);

            tvExam[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseClick(1);
                }
            });
        }
    }

    void examSet() {
        final EditText examName = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("시험 등록");
        builder.setMessage("시험 이름을 입력해주세요");
        builder.setView(examName);
        builder.setPositiveButton("등록",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (examName.getText().toString().length() == 0) {
                            Toast.makeText(getApplicationContext(), "시험을 이름이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    void courseClick(final int i) {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add(examList[i][0] + "으로 이동");
        for (int j = 1; j < examList[i].length; j++) {
            ListItems.add(examList[i][j]);
        }
        ListItems.add("+");
        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이동");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                if (pos == 0) {
                    Intent intent = new Intent(getApplicationContext(), examResult.class);
                    intent.putExtra("exam", this.toString()); //넘겨주는 이름 어케 바꿀지 생각하기
                    startActivity(intent);
                } else if (pos == examList[i].length) {
                    Intent intent = new Intent(getApplicationContext(), problemSet.class);
                    intent.putExtra("exam", pos); //넘겨주는 이름 어케 바꿀지 생각하기
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), problemResult.class);
                    intent.putExtra("problem", pos); //넘겨주는 이름 어케 바꿀지 생각하기
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

}
