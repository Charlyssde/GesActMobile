package com.carlos.cc.gesact.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.carlos.cc.gesact.R;
import com.carlos.cc.gesact.fragments.CriterionsFragment;
import com.carlos.cc.gesact.fragments.GroupsFragment;
import com.carlos.cc.gesact.fragments.StudentsFragment;
import com.carlos.cc.gesact.fragments.SubjectsFragment;
import com.carlos.cc.gesact.model.GroupModel;
import com.carlos.cc.gesact.services.Env;
import com.carlos.cc.gesact.services.PreferencesService;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView userName;
    private PreferencesService preferencesService;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        preferencesService = PreferencesService.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

       drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.header_title);
        userName.setText(preferencesService.getProperty(Env.USER_NAME));
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int title;
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_option_groups:
                        title = R.string.my_groups;
                        fragment = GroupsFragment.newInstance();
                        break;
                    case R.id.menu_option_subjects:
                        title = R.string.subjects;
                        fragment = SubjectsFragment.newInstance();
                        break;
                    case R.id.menu_option_students:
                        title = R.string.alumnos;
                        fragment = StudentsFragment.newInstance();
                        break;
                    case R.id.menu_option_criterions:
                        title = R.string.criterions;
                        fragment = CriterionsFragment.newInstance();
                        break;
                    default:
                        title = R.string.app_name;
                        fragment = GroupsFragment.newInstance();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_content, fragment)
                        .commit();

                navigationView.setCheckedItem(item);
                setTitle(getString(title));

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}