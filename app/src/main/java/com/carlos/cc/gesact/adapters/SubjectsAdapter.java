package com.carlos.cc.gesact.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText name, kind;
    private AlertDialog alertDialog;

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
                onMeClick(v,holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void onMeClick(View v, SubjectsAdapter.ViewHolder holder, int position) {
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View dialogView = inflater.inflate(R.layout.layout_new_subject, null);
        name = dialogView.findViewById(R.id.input_subject_name);
        kind = dialogView.findViewById(R.id.input_subject_kind);
        name.setText(holder.subjectName.getText());
        kind.setText(holder.subjectKind.getText());

        alertDialog = new AlertDialog.Builder(v.getContext(), R.style.CustomTheme_Dialog)
                .setTitle(v.getResources().getString(R.string.edit_element) + " materia")
                .setView(dialogView)
                .setPositiveButton(v.getResources().getString(R.string.save), null)
                .setNeutralButton(v.getResources().getString(R.string.delete), null)
                .setNegativeButton(v.getResources().getString(R.string.cancel), null)
                .show();

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubject(v, holder);
            }
        });
    }

    private void updateSubject(View v, ViewHolder holder) {
        if (emptyFields()) {
            Toast.makeText(activity, v.getResources().getString(R.string.empty_fields), Toast.LENGTH_LONG).show();
        } else {
            holder.mItem.subjectName = name.getText().toString();
            holder.mItem.subjectKind = kind.getText().toString();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    database.subjectDao().update(holder.mItem);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            });
            alertDialog.dismiss();
        }
    }

    private boolean emptyFields() {
        return name.getText().toString().equals("") || kind.getText().toString().equals("");
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
