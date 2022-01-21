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
import com.carlos.cc.gesact.model.CriterionModel;
import com.carlos.cc.gesact.model.SubjectModel;

import java.util.List;

public class CriterionAdapter extends RecyclerView.Adapter<CriterionAdapter.ViewHolder>{
    private final List<CriterionModel> mValues;
    private AppDatabase database;
    private Activity activity;

    public CriterionAdapter(List<CriterionModel> mValues, Activity activity) {
        this.mValues = mValues;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_criterions, parent, false);
        database = AppDatabase.getInstance(parent.getContext());
        return new CriterionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.criterionName.setText(holder.mItem.criterionName);
        holder.criterionValue.setText(String.valueOf(holder.mItem.criterionValue) + "%");
        holder.criterionSubject.setText(holder.mItem.subjectModel.subjectName);
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
        public final TextView criterionName, criterionValue, criterionSubject;
        public CriterionModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            criterionName = view.findViewById(R.id.element_criterion_name);
            criterionValue = view.findViewById(R.id.element_criterion_total_value);
            criterionSubject = view.findViewById(R.id.element_criterion_subject);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + criterionName.getText() + "'";
        }
    }

}
