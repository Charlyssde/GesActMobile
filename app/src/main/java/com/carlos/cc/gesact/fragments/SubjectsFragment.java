package com.carlos.cc.gesact.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.adapters.SubjectsAdapter;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.SubjectModel;
import com.carlos.cc.gesact.services.Option;

import java.util.ArrayList;

public class SubjectsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<GroupModel> grupos;
    private ArrayList<Option> values;
    private AppDatabase database;
    private Spinner menu_groups;
    private Context context;
    private ArrayList<SubjectModel> items;
    private EditText name, kind, search;
    private ImageButton btnSearchSubject;
    private Button btnNewSubject;
    private TextView cleanSearch;
    private GroupModel selectedGroup = null;
    private SubjectsAdapter adapter;
    private Option option;

    public SubjectsFragment() {
        // Required empty public constructor
    }

    public static SubjectsFragment newInstance() {
        SubjectsFragment fragment = new SubjectsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subjects_list, container, false);
        database = AppDatabase.getInstance(getContext());
        menu_groups = view.findViewById(R.id.spinner_groups);
        values = new ArrayList<>();
        values.add(new Option(0,"TODOS"));
        init(view);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                items = (ArrayList<SubjectModel>) database.subjectDao().getAll();
                adapter = new SubjectsAdapter(items, getActivity());
                context = view.getContext();
                recyclerView = (RecyclerView) view.findViewById(R.id.list_subjects);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
                grupos = (ArrayList<GroupModel>) database.groupDao().getAll();
                for (int i = 0; i < grupos.size(); i++) {
                    values.add(new Option(grupos.get(i).groupId, grupos.get(i).groupName));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Option> adapter = new ArrayAdapter<Option>(getContext(), R.layout.spinner_item, values);
                        adapter.setDropDownViewResource(R.layout.spinner_item);
                        menu_groups.setAdapter(adapter);
                    }
                });
            }
        });

        return view;
    }

    private void init(View view) {

        btnNewSubject = view.findViewById(R.id.btn_add_subject);
        btnSearchSubject =  view.findViewById(R.id.btn_search_subject);
        cleanSearch = view.findViewById(R.id.txt_clean_search);
        search = view.findViewById(R.id.txt_search_subjects);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    search.setText("");
                }
            }
        });
        btnNewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddClick(v);
            }
        });
        btnSearchSubject.setOnClickListener(new View.OnClickListener() {
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
        menu_groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = (Option) parent.getSelectedItem();
                if(option.getKey() == 0){
                    selectedGroup = null;
                    recyclerView.setAdapter(new SubjectsAdapter(items, getActivity()));
                }else{
                    for (int i = 0; i < grupos.size(); i++) {
                        if(option.getKey() == grupos.get(i).groupId){
                            selectedGroup = grupos.get(i);
                            //TODO: CHANGE THIS FOR FILTER BY GROUP
                            ArrayList<SubjectModel> itemsFiltered = getFilteredItems(option);
                            recyclerView.setAdapter(new SubjectsAdapter(itemsFiltered, getActivity()));
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<SubjectModel> getFilteredItems(Option option) {
        ArrayList<SubjectModel> toReturn = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {
            if(items.get(j).GroupModel.groupId == option.getKey()){
                toReturn.add(items.get(j));
            }
        }
        return toReturn;
    }

    private ArrayList<SubjectModel> getSearchItem(){
        ArrayList<SubjectModel> toReturn = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {
            if(items.get(j).subjectName.contains(search.getText().toString())){
                toReturn.add(items.get(j));
            }
        }
        return toReturn;
    }

    private void buttonSearchClick(View v) {
        if(search.getText().toString().equals("")){
            recyclerView.setAdapter(adapter);
        }else{
            cleanSearch.setVisibility(View.VISIBLE);
            search.clearFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            //TODO: CHANGE THIS FOR FILTER
            recyclerView.setAdapter(new SubjectsAdapter(getSearchItem(), getActivity()));
        }

    }

    private void cleanSearchClick(View v) {
        recyclerView.setAdapter(adapter);
        cleanSearch.setVisibility(View.GONE);

    }

    private void buttonAddClick(View v) {
        if(selectedGroup == null){
            Toast.makeText(getContext(), "Por favor seleccione un grupo primero", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
            builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveNewSubject();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle(getResources().getString(R.string.new_element) + " materia");
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_new_subject, null);

            name = dialogView.findViewById(R.id.input_subject_name);
            kind = dialogView.findViewById(R.id.input_subject_kind);

            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void saveNewSubject() {
        if(emptyFields()){
            Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
        }else{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    database.subjectDao().insertAll(new SubjectModel(
                            name.getText().toString(),
                            kind.getText().toString(),
                            selectedGroup));
                    items = (ArrayList<SubjectModel>) database.subjectDao().getAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new SubjectsAdapter(getFilteredItems(option), getActivity()));
                        }
                    });
                    search.clearFocus();
                }
            });
        }

    }

    private boolean emptyFields() {
        return name.getText().toString().equals("") || kind.getText().toString().equals("");
    }

}