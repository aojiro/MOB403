package com.example.lab3_mob403;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab3_mob403.Fragment.Fragment_Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewByID();

        initBottomNav();

    }

    private void initBottomNav() {
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        positionFragment(R.id.bottom_bai1);
        loadFragment(new Fragment_Login());
    }

    private void initViewByID() {
        this.bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void positionFragment(int id) {
        bottomNavigationView.getMenu().findItem(id).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_bai1:
                loadFragment(new Fragment_Login());
                positionFragment(R.id.bottom_bai1);
                break;
        }
        return true;
    }
}