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

    private EditText etName, etStudentId;
    private SeekBar seekBarAge;
    private TextView tvAgeValue;
    private Button btnAdd, btnView;
    private DBHelper dbHelper;
    private int selectedAge = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etStudentId = findViewById(R.id.etStudentId);
        seekBarAge = findViewById(R.id.seekBarAge);
        tvAgeValue = findViewById(R.id.tvAgeValue);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        dbHelper = new DBHelper(this);

        // Max 33 (17 + 33 = 50)
        seekBarAge.setMax(33);
        tvAgeValue.setText("Age: " + selectedAge);

        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAge = 17 + progress;
                tvAgeValue.setText("Age: " + selectedAge);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String studentId = etStudentId.getText().toString().trim();

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(MainActivity.this, "Isi nama dan NIM!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertStudent(name, studentId, selectedAge);
            if (isInserted) {
                Toast.makeText(MainActivity.this, "Data Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etStudentId.setText("");
                seekBarAge.setProgress(0);
            } else {
                Toast.makeText(MainActivity.this, "Gagal Menambahkan Data!", Toast.LENGTH_SHORT).show();
            }
        });

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewStudentActivity.class);
            startActivity(intent);
        });
    }
}