package com.carlos.cc.gesact.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AsyncNotedAppOp;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.adapters.GroupsAdapter;
import com.carlos.cc.gesact.adapters.StudentsAdapter;
import com.carlos.cc.gesact.database.AppDatabase;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.model.StudentModel;
import com.carlos.cc.gesact.services.Option;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<GroupModel> grupos;
    private ArrayList<Option> values;
    private AppDatabase database;
    private Spinner menu_groups;
    private Context context;
    private ArrayList<StudentModel> items;
    private EditText name, lastName, surname, birthday, contact, search;
    private ImageButton btnSearchStudent;
    private Button btnNewStudent;
    private TextView cleanSearch;
    private GroupModel selectedGroup = null;
    private StudentsAdapter adapter;
    private Option option;


    public StudentsFragment(){}

    public static StudentsFragment newInstance() {
        StudentsFragment fragment = new StudentsFragment();
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
        View view = inflater.inflate(R.layout.fragment_students_list, container, false);
        database = AppDatabase.getInstance(getContext());
        menu_groups = view.findViewById(R.id.spinner_groups);
        values = new ArrayList<>();
        values.add(new Option(0,"TODOS"));
        init(view);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                items = (ArrayList<StudentModel>) database.studentDao().getAll();
                adapter = new StudentsAdapter(items, getActivity());
                context = view.getContext();
                recyclerView = (RecyclerView) view.findViewById(R.id.list_students);
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

        btnNewStudent = view.findViewById(R.id.btn_add_student);
        btnSearchStudent =  view.findViewById(R.id.btn_search_student);
        cleanSearch = view.findViewById(R.id.txt_clean_search);
        search = view.findViewById(R.id.txt_search_students);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    search.setText("");
                }
            }
        });
        btnNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddClick(v);
            }
        });
        btnSearchStudent.setOnClickListener(new View.OnClickListener() {
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
                    recyclerView.setAdapter(new StudentsAdapter(items, getActivity()));
                }else{
                    for (int i = 0; i < grupos.size(); i++) {
                        if(option.getKey() == grupos.get(i).groupId){
                            selectedGroup = grupos.get(i);
                            ArrayList<StudentModel> itemsFiltered = getFilteredItems(option);
                            recyclerView.setAdapter(new StudentsAdapter(itemsFiltered, getActivity()));
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cleanSearchClick(View v) {
        recyclerView.setAdapter(adapter);
        cleanSearch.setVisibility(View.GONE);

    }

    private void buttonSearchClick(View v) {
        if(search.getText().toString().equals("")){
            recyclerView.setAdapter(adapter);
        }else{
            cleanSearch.setVisibility(View.VISIBLE);
            search.clearFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            recyclerView.setAdapter(new StudentsAdapter(getSearchItem(), getActivity()));
        }

    }

    private void buttonAddClick(View v) {
        if(selectedGroup == null){
            Toast.makeText(getContext(), "Por favor seleccione un grupo primero", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
            builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveNewStudent();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle(getResources().getString(R.string.new_element) + " estudiante");
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_new_student, null);

            name = dialogView.findViewById(R.id.input_student_name);
            lastName = dialogView.findViewById(R.id.input_student_last_name);
            surname = dialogView.findViewById(R.id.input_student_surname);
            birthday = dialogView.findViewById(R.id.input_student_birthday);
            contact = dialogView.findViewById(R.id.input_student_contact);

            birthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // +1 because January is zero
            final String selectedDate = day + " / " + (month+1) + " / " + year;
            birthday.setText(selectedDate);
        }
    });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

    }

    private void saveNewStudent() {

        if(emptyFields()){
            Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
        }else{
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    database.studentDao().insertAll(new StudentModel(
                            name.getText().toString(),
                            lastName.getText().toString(),
                            surname.getText().toString(),
                            birthday.getText().toString(),
                            contact.getText().toString(),
                            selectedGroup));
                    items = (ArrayList<StudentModel>) database.studentDao().getAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new StudentsAdapter(getFilteredItems(option), getActivity()));
                        }
                    });
                    search.clearFocus();
                }
            });
        }

    }

    private ArrayList<StudentModel> getFilteredItems(Option option) {
        ArrayList<StudentModel> toReturn = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {
            if(items.get(j).GroupModel.groupId == option.getKey()){
                toReturn.add(items.get(j));
            }
        }
        return toReturn;
    }

    private ArrayList<StudentModel> getSearchItem(){
        ArrayList<StudentModel> toReturn = new ArrayList<>();
        for (int j = 0; j < items.size(); j++) {
            if(items.get(j).studentName.contains(search.getText().toString()) ||
                    items.get(j).studentLastName.contains(search.getText().toString())){
                toReturn.add(items.get(j));
            }
        }
        return toReturn;
    }

    private boolean emptyFields() {
        return name.getText().toString().equals("") || lastName.getText().toString().equals("") || surname.getText().toString().equals("")
                || birthday.getText().toString().equals("") || contact.getText().toString().equals("");
    }

}
