package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class problemSet extends AppCompatActivity {

    TextView tvCourse = null;
    TextView tvProblem = null;
    Button regBtn = null;
    ImageButton undoBtn = null;
    ImageButton plusBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_set);

        tvCourse = findViewById(R.id.tvCourse);
        tvProblem = findViewById(R.id.tvProblem);
        regBtn = (Button) findViewById(R.id.regBtn);
        undoBtn = (ImageButton) findViewById(R.id.undoBtn);
        plusBtn = (ImageButton) findViewById(R.id.plusBtn);

        tvCourse.setText("Course Name");
        tvProblem.setText("Exam - Problem");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), course.class);
                startActivity(intent);
            }
        });
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "+", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
