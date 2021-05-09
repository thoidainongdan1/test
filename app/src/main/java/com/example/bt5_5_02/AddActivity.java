package com.example.bt5_5_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bt5_5_02.model.Student;

public class AddActivity extends AppCompatActivity {
    private Button btOK, btBack;
    private EditText txtName,txtMark;
    private RadioButton male,female;
    private SQLiteStudentOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
        db = new SQLiteStudentOpenHelper(this);

        btOK.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            boolean gender = false;
            if(male.isChecked()) {
                gender = true;
            }
            double mark = Double.parseDouble(txtMark.getText().toString());
            Student student = new Student(name, gender, mark);
            db.addStudent(student);

            finish();
        });

        btBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void init() {
        btOK=findViewById(R.id.btOK);
        btBack=findViewById(R.id.btBack);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
    }
}