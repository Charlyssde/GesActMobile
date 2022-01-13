package com.carlos.cc.gesact.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.SubjectModel;

import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder>  {

    private final List<SubjectModel> mValues;
    private AppDatabase database;
    private Activity activity;

    public SubjectsAdapter(List<SubjectModel> mValues, Activity activity) {
        this.mValues = mValues;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_subjects, parent, false);
        database = AppDatabase.getInstance(parent.getContext());
        return new SubjectsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.subjectName.setText(holder.mItem.subjectName);
        holder.subjectKind.setText(holder.mItem.subjectKind);
        holder.subjectGroup.setText(holder.mItem.GroupModel.groupName + holder.mItem.GroupModel.gradeName);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView subjectName, subjectKind, subjectGroup;
        public SubjectModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            subjectName = (TextView) view.findViewById(R.id.elemet_subject_name);
            subjectKind = (TextView) view.findViewById(R.id.element_subject_kind);
            subjectGroup = view.findViewById(R.id.element_subject_group);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + subjectName.getText() + "'";
        }
    }
}
