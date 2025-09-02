package com.mobile.casemaster;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.content.ContextCompat;
import android.view.Window;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // FrameLayout with id fragment_container required

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Show icons in original colors
        bottomNavigationView.setItemIconTintList(null);

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UppercaseFragment())
                    .commit();
        }

        // Bottom navigation selection using if-else
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_text_to_upper) {
                selectedFragment = new UppercaseFragment();
            } else if (id == R.id.nav_text_to_lower) {
                selectedFragment = new LowercaseFragment();
            } else if (id == R.id.nav_text_to_title) {
                selectedFragment = new TitleCaseFragment();
            } else if (id == R.id.nav_text_to_reverse) {
                selectedFragment = new ReverseTextFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });


        // Optional: set dark status bar
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background_dark));
        WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(window, window.getDecorView());
        insetsController.setAppearanceLightStatusBars(false); // White icons for dark status bar
    }
}
