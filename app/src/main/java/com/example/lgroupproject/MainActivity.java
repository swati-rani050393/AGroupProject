package com.example.lgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    BottomNavigationView bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar=findViewById(R.id.bottomBar);

        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content,new HomeFragment());
        transaction.commit();

        bottomBar.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                transaction=getSupportFragmentManager().beginTransaction();
                int id=item.getItemId();
                if(id==R.id.home)
                {
                    transaction.replace(R.id.content,new HomeFragment());
                    transaction.commit();
                }
                else if (id==R.id.mycart) {
                    transaction.replace(R.id.content, new MyCartFragment());
                    transaction.commit();
                }
                else if (id==R.id.profile) {
                    transaction.replace(R.id.content, new ProfileFragment());
                    transaction.commit();

                }
                return true;
            }
        });
    }
}