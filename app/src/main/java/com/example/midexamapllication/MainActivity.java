package com.example.midexamapllication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd, btnView;
    private EditText editTextName, editTextStudentID;
    private SeekBar seekBarAge;
    private TextView tvAgeLabel;

    // Umur Minimal 17
    private int selectedAge = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextStudentID = findViewById(R.id.editTextStudentID);
        seekBarAge = findViewById(R.id.seekBarAge);
        tvAgeLabel = findViewById(R.id.tvAgeLabel);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        // Setting SeekBar: Rentang 17 sampai 50 (33 langkah)
        seekBarAge.setMax(33); // 50 - 17 = 33
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAge = 17 + progress;
                tvAgeLabel.setText("Student Age: " + selectedAge);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String studentId = editTextStudentID.getText().toString().trim();

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter all data", Toast.LENGTH_SHORT).show();
            } else {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                StudentModel studentModel = new StudentModel(name, studentId, selectedAge);
                dbHelper.addStudent(studentModel);
                Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();

                // Reset Input
                editTextName.setText("");
                editTextStudentID.setText("");
                seekBarAge.setProgress(0);
                tvAgeLabel.setText("Student Age: 17");
            }
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewStudentActivity.class);
            startActivity(intent);
        });
    }
}