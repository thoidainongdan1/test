package com.example.bt5_5;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt5_5.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btAdd,btGet,btUpdate,btDelete,btAll;
    private EditText txtId,txtName,txtMark;
    private RadioButton male,female;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SQLiteStudentOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        db = new SQLiteStudentOpenHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                boolean gender = false;
                if(male.isChecked()) {
                    gender = true;
                }
                double mark = Double.parseDouble(txtMark.getText().toString());
                Student student = new Student(name, gender, mark);
                db.addStudent(student);
            }
        });

        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Student> list = db.getAll();
                adapter.setStudents(list);
                recyclerView.setAdapter(adapter);
            }
        });

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(txtId.getText().toString());
                    Student student = db.getStudentById(id);
                    txtName.setText(student.getName());
                    txtMark.setText(student.getMark()+"");
                    if(student.isGender()) {
                        male.setChecked(true);
                    } else {
                        female.setChecked(true);
                    }
                    txtId.setEnabled(false);
                    btAdd.setEnabled(false);
                    btUpdate.setEnabled(true);
                    btDelete.setEnabled(true);
                } catch(Exception e) {

                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(txtId.getText().toString());
                    db.deleteStudent(id);
                } catch (Exception e) {

                }
                txtId.setEnabled(true);
                btAdd.setEnabled(true);
                btUpdate.setEnabled(false);
                btDelete.setEnabled(false);
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
                txtId.setEnabled(true);
                btAdd.setEnabled(true);
                btUpdate.setEnabled(false);
                btDelete.setEnabled(false);
            }
        });
    }

    public void init() {
        btAdd=findViewById(R.id.btAdd);
        btGet=findViewById(R.id.btGet);
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);
        btAll=findViewById(R.id.btAll);
        txtId=findViewById(R.id.stID);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        btUpdate.setEnabled(false);
        btDelete.setEnabled(false);
        recyclerView=findViewById(R.id.recyclerView);
    }
}