package com.example.bt5_5_02;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt5_5_02.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.StudentViewHolder> {
    private List<Student> list;
    private Activity activity;

    public RecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        list = new ArrayList<>();
    }

    public void setStudents(List<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.student_card, parent, false);
        return new StudentViewHolder(v);
    }

    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student s = list.get(position);
        holder.tvIdName.setText(s.getName() + " - " + s.getId());
        if (s.isGender())
            holder.imgGender.setImageResource(R.drawable.male_icon);
        else
            holder.imgGender.setImageResource(R.drawable.female_icon);
        holder.tvMark.setText(" Mark:" + s.getMark());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateDeleteActivity.class);
                intent.putExtra("id", s.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIdName;
        private final ImageView imgGender;
        private final TextView tvMark;

        public StudentViewHolder(@NonNull View v) {
            super(v);
            tvIdName = v.findViewById(R.id.idName);
            imgGender = v.findViewById(R.id.img);
            tvMark = v.findViewById(R.id.mark);
        }
    }
}
