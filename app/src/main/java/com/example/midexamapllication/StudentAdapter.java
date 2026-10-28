package com.example.midexamapllication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<StudentModel> students;
    Context context;
    DBHelper dbHelper;

    public StudentAdapter(List<StudentModel> students, Context context) {
        this.students = students;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final StudentModel studentModel = students.get(position);

        holder.textViewID.setText("Database ID: " + studentModel.getId());
        holder.editTextName.setText(studentModel.getName());
        holder.editTextStudentID.setText(studentModel.getStudentId());

        // Mengatur Nilai Awal SeekBar Sesuai Umur Tersimpan
        int currentAge = studentModel.getAge();
        holder.seekBarItemAge.setMax(33); // 50 - 17 = 33
        holder.seekBarItemAge.setProgress(currentAge - 17);
        holder.tvItemAgeLabel.setText("Student Age: " + currentAge);

        // Listener saat SeekBar Digeser pada Item List
        holder.seekBarItemAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newAge = 17 + progress;
                holder.tvItemAgeLabel.setText("Student Age: " + newAge);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Tombol Edit Sesuai Struktur PPT Dosen
        holder.btnEdit.setOnClickListener(v -> {
            String strName = holder.editTextName.getText().toString().trim();
            String strStudentID = holder.editTextStudentID.getText().toString().trim();
            int updatedAge = 17 + holder.seekBarItemAge.getProgress();

            if (strName.isEmpty() || strStudentID.isEmpty()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.updateStudent(new StudentModel(studentModel.getId(), strName, strStudentID, updatedAge));
            notifyDataSetChanged();
            ((Activity) context).finish();
            context.startActivity(((Activity) context).getIntent());
        });

        // Tombol Delete Sesuai Struktur PPT Dosen
        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteStudent(studentModel.getId());
            students.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID, tvItemAgeLabel;
        EditText editTextName, editTextStudentID;
        SeekBar seekBarItemAge;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            tvItemAgeLabel = itemView.findViewById(R.id.tvItemAgeLabel);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextStudentID = itemView.findViewById(R.id.editTextStudentID);
            seekBarItemAge = itemView.findViewById(R.id.seekBarItemAge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}