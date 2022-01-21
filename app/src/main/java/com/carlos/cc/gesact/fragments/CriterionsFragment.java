package com.carlos.cc.gesact.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.adapters.CriterionAdapter;
import com.carlos.cc.gesact.adapters.SubjectsAdapter;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.CriterionModel;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.SubjectModel;
import com.carlos.cc.gesact.services.Option;

import java.util.ArrayList;

public class CriterionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<GroupModel> grupos;
    private ArrayList<SubjectModel> subjects;
    private ArrayList<Option> values, filters;
    private ArrayList<CriterionModel> items;
    private AppDatabase database;
    private Spinner menuGroups, menuSubjects;
    private Context context;
    private EditText name, value, minValue, maxValue, search;
    private ImageButton btnSearchCriterion;
    private ImageView info;
    private LinearLayout layout;
    private RadioButton radioSimple, radioCalculate;
    private Button btnNewCriterion;
    private TextView cleanSearch;
    private GroupModel selectedGroup = null;
    private SubjectModel selectedSubject = null;
    private CriterionAdapter adapter;
    private Option option;

    public CriterionsFragment() {
        // Required empty public constructor
    }

    public static CriterionsFragment newInstance() {
        CriterionsFragment fragment = new CriterionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_criterions_list, container, false);
        database = AppDatabase.getInstance(getContext());
        values = new ArrayList<>();
        filters = new ArrayList<>();
        filters.add(new Option(0, "TODAS"));
        values.add(new Option(0,"TODOS"));
        init(view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                items = (ArrayList<CriterionModel>) database.criterionDao().getAll();
                Log.e("LONG OF ITEMS->", String.valueOf(items.size()));
                adapter = new CriterionAdapter(items, getActivity());
                context = view.getContext();
                recyclerView = (RecyclerView) view.findViewById(R.id.list_criterions);
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
                        menuGroups.setAdapter(adapter);
                    }
                });
            }
        });

        return view;
    }

    private void init(View view) {
        menuGroups = view.findViewById(R.id.spinner_groups);
        menuSubjects = view.findViewById(R.id.spinner_subjects);
        menuSubjects.setEnabled(false);
        btnNewCriterion = view.findViewById(R.id.btn_add_criterions);
        btnSearchCriterion =  view.findViewById(R.id.btn_search_criterion);
        cleanSearch = view.findViewById(R.id.txt_clean_search);
        search = view.findViewById(R.id.txt_search_criterions);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    search.setText("");
                }
            }
        });
        btnNewCriterion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddClick(v);
            }
        });
        btnSearchCriterion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                buttonSearchClick(v);
            }
        });
        cleanSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanSearchClick(v);
            }
        });
        menuGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = (Option) parent.getSelectedItem();
                if(option.getKey() == 0){
                    filters = new ArrayList<>();
                    filters.add(new Option(0, "TODAS"));
                    selectedGroup = null;
                    selectedSubject = null;
                    menuSubjects.setSelection(0);
                    menuSubjects.setEnabled(false);
                    recyclerView.setAdapter(new CriterionAdapter(items, getActivity()));
                }else{
                    filters = new ArrayList<>();
                    filters.add(new Option(0, "TODAS"));
                    for (int i = 0; i < grupos.size(); i++) {
                        if(option.getKey() == grupos.get(i).groupId){
                            selectedGroup = grupos.get(i);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    subjects = (ArrayList<SubjectModel>) database.subjectDao().getByGroup(selectedGroup.groupId);
                                    for (int i = 0; i < subjects.size(); i++) {
                                        Log.e("SIZE OF SUBJECTS->", String.valueOf(subjects.get(i).subject_id) + subjects.get(i).subjectName + subjects.get(i).GroupModel.groupId);
                                        filters.add(new Option(subjects.get(i).subject_id, subjects.get(i).subjectName));
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ArrayAdapter<Option> adapter = new ArrayAdapter<Option>(getContext(), R.layout.spinner_item, filters);
                                            adapter.setDropDownViewResource(R.layout.spinner_item);
                                            menuSubjects.setAdapter(adapter);
                                        }
                                    });
                                }
                            });
                            menuSubjects.setEnabled(true);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        menuSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = (Option) parent.getSelectedItem();
                if(option.getKey() == 0){
                    selectedSubject = null;
                    recyclerView.setAdapter(new CriterionAdapter(items, getActivity()));
                }else{
                    for (int i = 0; i < subjects.size(); i++) {
                        if(option.getKey() == subjects.get(i).subject_id){
                            selectedSubject = subjects.get(i);
                            //TODO: CHANGE THIS FOR FILTER BY GROUP
                            ArrayList<CriterionModel> itemsFiltered = getFilteredItems();
                            recyclerView.setAdapter(new CriterionAdapter(itemsFiltered, getActivity()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buttonAddClick(View v) {

        if(selectedGroup == null){
            Toast.makeText(getContext(), "Por favor seleccione un grupo primero", Toast.LENGTH_SHORT).show();
        }else if(selectedSubject == null){
            Toast.makeText(getContext(), "Por favor seleccione una materia", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
            builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveNewCriterion();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}});
            builder.setTitle(getResources().getString(R.string.new_element) + " criterio de la materia: " + selectedSubject.subjectName);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_new_criterion, null);

            name = dialogView.findViewById(R.id.input_criterion_name);
            value = dialogView.findViewById(R.id.input_criterion_value);
            minValue = dialogView.findViewById(R.id.input_criterion_min_value);
            maxValue = dialogView.findViewById(R.id.input_criterion_max_value);
            minValue.setText("1");
            maxValue.setText("1");
            radioSimple = dialogView.findViewById(R.id.radio_simple);
            radioCalculate = dialogView.findViewById(R.id.radio_calculado);
            info = dialogView.findViewById(R.id.img_info);
            layout  = dialogView.findViewById(R.id.layout_range_values);

            radioSimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.setVisibility(View.GONE);
                    minValue.setText("1");
                    maxValue.setText("1");
                }
            });

            radioCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.setVisibility(View.VISIBLE);
                    minValue.setText("");
                    maxValue.setText("");
                }
            });

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "La calificación simple significa únicamente ENTREGADO / NO ENTREGADO. " +
                            "La calificación calculada incluye un valor máximo y uno mínimo y se debe asignar una calificación entre ese rango" +
                            " a cada evidencia que se cree con este criterio", Toast.LENGTH_LONG).show();
                }
            });

            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    private void saveNewCriterion() {
        if(emptyFields()){
            Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
        }else{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    database.criterionDao().insertAll(new CriterionModel(name.getText().toString(), Float.parseFloat(value.getText().toString()),
                            radioSimple.isChecked() ? "simple" : "calculado" ,
                            Float.parseFloat(maxValue.getText().toString()), Float.parseFloat(minValue.getText().toString()), selectedSubject));
                    items = (ArrayList<CriterionModel>) database.criterionDao().getAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new CriterionAdapter(items, getActivity()));
                        }
                    });
                }
            });
        }
    }

    private boolean emptyFields() {
        return name.getText().toString().equals("") || value.getText().toString().equals("") ||
                minValue.getText().toString().equals("") || maxValue.getText().toString().equals("");
    }

    private ArrayList<CriterionModel> getFilteredItems() {
        ArrayList<CriterionModel> toReturn = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {
            if(items.get(j).subjectModel.subject_id == option.getKey()){
                toReturn.add(items.get(j));
            }
        }
        return toReturn;
    }

    private void cleanSearchClick(View v) {
        recyclerView.setAdapter(adapter);
        cleanSearch.setVisibility(View.GONE);
    }

}