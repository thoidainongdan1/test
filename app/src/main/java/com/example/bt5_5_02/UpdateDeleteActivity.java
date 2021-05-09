package com.example.bt5_5_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bt5_5_02.model.Student;

public class UpdateDeleteActivity extends AppCompatActivity {
    private Button btUpdate,btDelete,btBack;
    private EditText txtId,txtName,txtMark;
    private RadioButton male,female;
    private SQLiteStudentOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        init();
        db = new SQLiteStudentOpenHelper(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Student student = db.getStudentById(id);
        txtId.setText(id+"");
        txtName.setText(student.getName());
        txtMark.setText(student.getMark()+"");
        if(student.isGender()) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(txtId.getText().toString());
                    db.deleteStudent(id);

                    finish();
                } catch (Exception e) {

                }
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(txtId.getText().toString());
                String name = txtName.getText().toString();
                boolean gender = false;
                if(male.isChecked()) {
                    gender = true;
                }
                double mark = Double.parseDouble(txtMark.getText().toString());
                Student student = new Student(id, name, gender, mark);
                db.updateStudent(student);

                finish();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init() {
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);
        btBack=findViewById(R.id.btBack);
        txtId=findViewById(R.id.stID);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
    }
}