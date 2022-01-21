package com.carlos.cc.gesact.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.fragments.GroupsFragment;
import com.carlos.cc.gesact.model.CriterionModel;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.SubjectModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private final List<GroupModel> mValues;
    private EditText name, grado;
    private AppDatabase database;
    private Activity activity;
    private AlertDialog alertDialog;

    public GroupsAdapter(List<GroupModel> items, Activity activity) {
        mValues = items;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_groups, parent, false);
        database = AppDatabase.getInstance(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMeClick(v,holder, position);
            }
        });
        holder.groupName.setText(holder.mItem.groupName);
        holder.groupGradeName.setText(holder.mItem.gradeName);
        holder.groupStatus.setText(holder.mItem.groupStatus ? "Activo" : "Inactivo");
    }

    private void onMeClick(View v,ViewHolder holder, int position) {
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View dialogView = inflater.inflate(R.layout.layout_new_group, null);

        name = dialogView.findViewById(R.id.input_group_name);
        grado =  dialogView.findViewById(R.id.input_grado_name);
        name.setText(holder.mItem.groupName);
        grado.setText(holder.mItem.gradeName);

        alertDialog = new AlertDialog.Builder(v.getContext(), R.style.CustomTheme_Dialog)
                .setTitle(v.getResources().getString(R.string.edit_element) + " materia")
                .setView(dialogView)
                .setPositiveButton(v.getResources().getString(R.string.save), null)
                .setNeutralButton(v.getResources().getString(R.string.delete), null)
                .setNegativeButton(v.getResources().getString(R.string.cancel), null)
                .show();

        Button btnPositive, btnCancel, btnDelete;
        btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnDelete = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGroup(v, holder);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup(v, holder);
            }
        });
    }

    private void deleteGroup(View v, ViewHolder holder) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO: DELETE ALSO THE DEPENDENCIES
                GroupModel group = database.groupDao().getGroup(holder.mItem.groupId);
                ArrayList<SubjectModel> subjects = (ArrayList<SubjectModel>) database.subjectDao().getByGroup(group.groupId);
                subjects.forEach(subjectModel -> {
                    ArrayList<CriterionModel> criterions = (ArrayList<CriterionModel>) database.criterionDao().getBySubject(subjectModel.subject_id);
                    if(criterions.size() != 0){
                        criterions.forEach(criterion -> {
                            database.criterionDao().delete(criterion.criterionId);
                        });
                    }
                    database.subjectDao().delete(subjectModel.subject_id);
                });
                database.groupDao().delete(group.groupId);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void updateGroup(View v, ViewHolder holder) {
        if(emptyFields()){
            Toast.makeText(activity, v.getResources().getString(R.string.empty_fields), Toast.LENGTH_LONG).show();
        }else{
            holder.mItem.groupName = name.getText().toString();
            holder.mItem.gradeName = grado.getText().toString();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    database.groupDao().update(holder.mItem);
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
        return name.getText().toString().equals("") || grado.getText().toString().equals("");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView groupName,groupGradeName,groupStatus;
        public GroupModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            groupName = view.findViewById(R.id.element_group_name);
            groupGradeName = view.findViewById(R.id.element_group_grade_name);
            groupStatus = view.findViewById(R.id.element_group_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + groupName.getText() + "'";
        }
    }
}