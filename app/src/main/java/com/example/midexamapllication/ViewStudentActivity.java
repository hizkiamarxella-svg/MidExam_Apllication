package com.example.midexamapllication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DBHelper(this);

        loadData();
    }

    public void loadData() {
        List<StudentModel> studentList = dbHelper.getAllStudents();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this, studentList, dbHelper);
        recyclerView.setAdapter(adapter);
    }
}