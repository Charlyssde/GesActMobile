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
        holder.criterionValue.setText((int) holder.mItem.criterionValue);
        holder.criterionMinValue.setText((CharSequence) holder.criterionMinValue);
        holder.criterionMaxValue.setText((CharSequence) holder.criterionMaxValue);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView criterionName, criterionValue, criterionMinValue, criterionMaxValue;
        public CriterionModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            criterionName = view.findViewById(R.id.input_criterion_name);
            criterionValue = view.findViewById(R.id.input_criterion_value);
            criterionMinValue = view.findViewById(R.id.input_criterion_min_value);
            criterionMaxValue = view.findViewById(R.id.input_criterion_max_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + criterionName.getText() + "'";
        }
    }

}
