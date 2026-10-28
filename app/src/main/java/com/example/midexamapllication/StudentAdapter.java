package com.example.midexamapllication;

import android.app.AlertDialog;
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

    private Context context;
    private List<StudentModel> studentList;
    private DBHelper dbHelper;

    public StudentAdapter(Context context, List<StudentModel> studentList, DBHelper dbHelper) {
        this.context = context;
        this.studentList = studentList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentModel student = studentList.get(position);
        holder.tvName.setText("Name: " + student.getName());
        holder.tvStudentId.setText("NIM: " + student.getStudentId());
        holder.tvAge.setText("Age: " + student.getAge());

        // Tombol Edit
        holder.btnEdit.setOnClickListener(v -> showEditDialog(student, position));

        // Tombol Delete
        holder.btnDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteStudent(student.getId());
            if (isDeleted) {
                studentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentList.size());
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal menghapus data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    private void showEditDialog(StudentModel student, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etStudentId = dialogView.findViewById(R.id.etStudentId);
        SeekBar seekBarAge = dialogView.findViewById(R.id.seekBarAge);
        TextView tvAgeValue = dialogView.findViewById(R.id.tvAgeValue);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        Button btnView = dialogView.findViewById(R.id.btnView);

        btnView.setVisibility(View.GONE);
        btnAdd.setText("UPDATE STUDENT");

        etName.setText(student.getName());
        etStudentId.setText(student.getStudentId());

        // Range umur 17-50 (Max SeekBar = 33)
        seekBarAge.setMax(33);
        int initialProgress = student.getAge() - 17;
        if (initialProgress < 0) initialProgress = 0;
        if (initialProgress > 33) initialProgress = 33;

        seekBarAge.setProgress(initialProgress);
        tvAgeValue.setText("Age: " + student.getAge());

        final int[] selectedAge = {student.getAge()};

        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAge[0] = 17 + progress;
                tvAgeValue.setText("Age: " + selectedAge[0]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        AlertDialog dialog = builder.create();

        btnAdd.setOnClickListener(v -> {
            String updatedName = etName.getText().toString().trim();
            String updatedNim = etStudentId.getText().toString().trim();

            if (updatedName.isEmpty() || updatedNim.isEmpty()) {
                Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = dbHelper.updateStudent(student.getId(), updatedName, updatedNim, selectedAge[0]);
            if (isUpdated) {
                student.setName(updatedName);
                student.setStudentId(updatedNim);
                student.setAge(selectedAge[0]);
                notifyItemChanged(position);
                Toast.makeText(context, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Gagal mengupdate data!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStudentId, tvAge;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvAge = itemView.findViewById(R.id.tvAge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}