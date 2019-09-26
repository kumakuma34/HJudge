package com.capstone.hjudge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class register extends AppCompatActivity {
    ImageButton undoBtn=null;
    ScrollView studentScroll = null;
    TableLayout scrollTable = null;
    Button regBtn=null;

    String[][] studentList = {{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},
            {"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},
            {"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},
            {"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"},{"학번", "이름"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        undoBtn = (ImageButton) findViewById(R.id.undoBtn);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });

        regBtn = (Button) findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });

        studentScroll=(ScrollView) findViewById(R.id.studentScroll);
        scrollTable = (TableLayout) findViewById(R.id.scrollTable);
        TableRow[] studentRow =  new TableRow[studentList.length];;
        TextView[][] rowComponent = new TextView[studentList.length][studentList[0].length];
        for(int i=0;i<studentList.length;i++){
            studentRow[i]=new TableRow(this);
            for(int j=0;j<studentList[i].length;j++){
                rowComponent[i][j] = new TextView(this);
                rowComponent[i][j].setText(studentList[i][j] + Integer.toString(i));
                rowComponent[i][j].setTextSize(20);
                studentRow[i].addView(rowComponent[i][j]);
            }
            scrollTable.addView(studentRow[i]);
        }


    }
}
