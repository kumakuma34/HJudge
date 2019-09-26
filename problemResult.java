package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class problemResult extends AppCompatActivity {
    ImageButton undoBtn = null;
    ScrollView studentScroll = null;
    TableLayout scrollTable = null;
    String[][] studentList = {{"학번", "이름", "O"}, {"학번", "이름", "X"}, {"학번", "이름", "O"}, {"학번", "이름", "X"},
            {"학번", "이름", "O"}, {"학번", "이름", "X"}, {"학번", "이름", "O"}, {"학번", "이름", "X"},
            {"학번", "이름", "O"}, {"학번", "이름", "X"}, {"학번", "이름", "X"}, {"학번", "이름", "O"}
            , {"학번", "이름", "O"}, {"학번", "이름", "X"}, {"학번", "이름", "O"}, {"학번", "이름", "O"}
            , {"학번", "이름", "X"}, {"학번", "이름", "O"}, {"학번", "이름", "X"}, {"학번", "이름", "O"}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_result);

        undoBtn = (ImageButton) findViewById(R.id.undoBtn);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), course.class);
                startActivity(intent);
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
