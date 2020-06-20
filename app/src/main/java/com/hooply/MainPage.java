package com.hooply;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hooply.ui.dashboard.PostsFragment;
import com.hooply.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainPage extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        actionBar = getSupportActionBar();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemReselectedListener(selectListener);

        actionBar.setTitle("Home");
        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemReselectedListener selectListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    //handle clicks
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            actionBar.setTitle("Home");
//                            loadFragment();
                        case R.id.navigation_posts:
                            //goo posts
                    }
                }
            };

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}