package com.carlos.cc.gesact.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.adapters.GroupsAdapter;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.GroupModel;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

    private Button btnAddGroup;
    private ImageButton btnSearchGroup;
    private EditText name, grado, search;
    private RecyclerView recyclerView;
    private Context context;
    private TextView cleanSearch;
    private static ArrayList<GroupModel> items;
    private GroupsAdapter adapter;
    private AppDatabase database;

    public GroupsFragment() { }

    public static GroupsFragment newInstance() {
        GroupsFragment fragment = new GroupsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups_list, container, false);
        context = view.getContext();
        database = AppDatabase.getInstance(context);
        init(view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                items = (ArrayList<GroupModel>) database.groupDao().getAll();

                // Set the adapter
                recyclerView = (RecyclerView) view.findViewById(R.id.list_groups);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new GroupsAdapter(items, getActivity());
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

    private void init(View view) {
        btnAddGroup =  view.findViewById(R.id.btn_add_group);
        btnSearchGroup =  view.findViewById(R.id.btn_search_group);
        cleanSearch = view.findViewById(R.id.txt_clean_search);
        search = view.findViewById(R.id.txt_search_groups);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    search.setText("");
                }
            }
        });
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddClick(v);
            }
        });
        btnSearchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSearchClick(v);
            }
        });
        cleanSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanSearchClick(v);
            }
        });
    }

    private void cleanSearchClick(View v) {
        recyclerView.setAdapter(new GroupsAdapter(items, getActivity()));
        cleanSearch.setVisibility(View.GONE);

    }

    private void buttonSearchClick(View v) {
        if(search.getText().toString().equals("")){
            recyclerView.setAdapter(new GroupsAdapter(items, getActivity()));
        }else{
            cleanSearch.setVisibility(View.VISIBLE);
            search.clearFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            //TODO: CHANGE THIS FOR FILTER
            recyclerView.setAdapter(new GroupsAdapter(items.subList(0, items.size() / 2), getActivity()));
        }

    }

    private void buttonAddClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveNewGroup();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle(getResources().getString(R.string.new_element) + " grupo");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_new_group, null);
        name = dialogView.findViewById(R.id.input_group_name);
        grado =  dialogView.findViewById(R.id.input_grado_name);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveNewGroup() {

        if(grado.getText().toString().equals("") || name.getText().toString().equals("")){
            Toast.makeText(context, "Por favor complete los campos vacíos", Toast.LENGTH_SHORT).show();
        }else{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    database.groupDao().insertAll(new GroupModel(name.getText().toString(), grado.getText().toString(), true));
                    items = (ArrayList<GroupModel>) database.groupDao().getAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new GroupsAdapter(items, getActivity()));
                        }
                    });
                    search.clearFocus();
                }
            });
        }

    }

}