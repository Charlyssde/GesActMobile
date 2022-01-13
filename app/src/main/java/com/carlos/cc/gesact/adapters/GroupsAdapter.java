package com.carlos.cc.gesact.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.fragments.GroupsFragment;
import com.carlos.cc.gesact.model.GroupModel;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private final List<GroupModel> mValues;
    private EditText name, grado;
    private AppDatabase database;
    private Activity activity;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.CustomTheme_Dialog);
        builder.setPositiveButton(v.getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateGroup(v, holder);
            }
        });
        builder.setNegativeButton(v.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton(v.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle(v.getResources().getString(R.string.edit_element) + " grupo");
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View dialogView = inflater.inflate(R.layout.layout_new_group, null);

        name = dialogView.findViewById(R.id.input_group_name);
        grado =  dialogView.findViewById(R.id.input_grado_name);
        name.setText(holder.mItem.groupName);
        grado.setText(holder.mItem.gradeName);

        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateGroup(View v, ViewHolder holder) {
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