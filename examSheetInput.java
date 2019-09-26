package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class examSheetInput extends AppCompatActivity {

    String[][] studentList = {{"학번", "이름", "done"}, {"학번", "이름", "X"}, {"학번", "이름", "done"}, {"학번", "이름", "X"},
            {"학번", "이름", "done"}, {"학번", "이름", "X"}, {"학번", "이름", "done"}, {"학번", "이름", "X"},
            {"학번", "이름", "done"}, {"학번", "이름", "X"}, {"학번", "이름", "X"}, {"학번", "이름", "done"}
            , {"학번", "이름", "done"}, {"학번", "이름", "done"}, {"학번", "이름", "done"}, {"학번", "이름", "done"}
            , {"학번", "이름", "done"}, {"학번", "이름", "done"}, {"학번", "이름", "X"}, {"학번", "이름", "done"}};


    String[] examList = {
            "시험", "중간", "기말", "퀴즈"
    };
    String[] languageList = {
            "언어", "C", "C++", "Java", "Python"
    };
    String[] problemList = {
            "문제", "1", "2", "3", "4", "5"
    };
    ImageButton undoBtn = null;
    Button doneBtn = null;
    Spinner examSpinner = null;
    Spinner languageSpinner = null;
    Spinner problemSpinner = null;

    ScrollView studentScroll = null;
    TableLayout scrollTable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_sheet_input);

        undoBtn = (ImageButton) findViewById(R.id.undoBtn);
        doneBtn = (Button) findViewById(R.id.doneBtn);

        examSpinner = (Spinner) findViewById(R.id.examSpinner);
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        problemSpinner = (Spinner) findViewById(R.id.problemSpinner);

        ArrayAdapter<String> examAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                examList
        );
        examSpinner.setAdapter(examAdapter);
        examSpinner.setSelection(0);
        examSpinner.setGravity(Gravity.LEFT);

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                languageList
        );
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setSelection(0);
        languageSpinner.setGravity(Gravity.LEFT);

        ArrayAdapter<String> problemAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                problemList
        );
        problemSpinner.setAdapter(problemAdapter);
        problemSpinner.setSelection(0);
        problemSpinner.setGravity(Gravity.LEFT);

        doneBtn.setOnClickListener(new View.OnClickListener() {
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

        studentScroll = (ScrollView) findViewById(R.id.studentScroll);
        scrollTable = (TableLayout) findViewById(R.id.scrollTable);
        TableRow[] studentRow = new TableRow[studentList.length];
        ;
        TextView[][] rowComponent = new TextView[studentList.length][studentList[0].length];
        for (int i = 0; i < studentList.length; i++) {
            studentRow[i] = new TableRow(this);
            for (int j = 0; j < studentList[i].length; j++) {
                rowComponent[i][j] = new TextView(this);
                if (j != 2) {
                    rowComponent[i][j].setText(studentList[i][j] + Integer.toString(i));
                } else {
                    rowComponent[i][j].setText(studentList[i][j]);
                }
                rowComponent[i][j].setTextSize(20);
                studentRow[i].addView(rowComponent[i][j]);
            }
            scrollTable.addView(studentRow[i]);
        }


    }
}
