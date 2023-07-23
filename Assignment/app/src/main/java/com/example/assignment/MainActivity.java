package com.example.assignment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment.Fragment.Fragment_Information_Account;
import com.example.assignment.Fragment.Fragment_Login;
import com.example.assignment.Fragment.Fragment_User_Info;
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
        positionFragment(R.id.bottom_asm);
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
        int itemId = item.getItemId();
        if (itemId == R.id.bottom_asm) {
            loadFragment(new Fragment_Login());
        } else if (itemId == R.id.bottom_user) {
            loadFragment(new Fragment_User_Info());
        }
        positionFragment(itemId);
        return true;
    }
}