package com.carlos.cc.gesact.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.activities.MainActivity;
import com.carlos.cc.gesact.activities.StudentSelected;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.StudentModel;

import java.io.Serializable;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private final List<StudentModel> mValues;
    private AppDatabase database;
    private Activity activity;

    public StudentsAdapter(List<StudentModel> items, Activity activity) {
        mValues = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_students, parent, false);
        database = AppDatabase.getInstance(parent.getContext());
        return new StudentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.studentName.setText(holder.mItem.studentName);
        holder.studentLastName.setText(holder.mItem.studentLastName + " " + holder.mItem.studentSurname);
        holder.studentGroup.setText(holder.mItem.GroupModel.groupName + holder.mItem.GroupModel.gradeName);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMeClick(holder);
            }
        });
    }

    private void onMeClick(ViewHolder holder) {

        Intent intent = new Intent(holder.mView.getContext(), StudentSelected.class);
        intent.putExtra("data", holder.mItem.student_id);
        holder.mView.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView studentName, studentLastName, studentGroup;
        public StudentModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            studentName = (TextView) view.findViewById(R.id.element_student_name);
            studentLastName = (TextView) view.findViewById(R.id.element_student_last_name);
            studentGroup = view.findViewById(R.id.element_student_group);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + studentName.getText() + "'";
        }
    }
}
