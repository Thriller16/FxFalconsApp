package app.fxfalcons.com;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import app.fxfalcons.com.falconforex.R;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    NavigationView navigationView;
    BottomNavigationViewEx bottomNavigationView;
    FirebaseAuth mFireAuth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireAuth = FirebaseAuth.getInstance();


        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FxFalcons");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setIconSize(25, 25);
        bottomNavigationView.setTextSize(11);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new DashboardFragment());
        checkVerification();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        getSupportActionBar().setTitle("FxFalcons");
                        fragment = new DashboardFragment();
                        loadFragment(fragment);
                        checkVerification();
                        break;

                    case R.id.myaccount:
                        getSupportActionBar().setTitle("My Account");
                        fragment = new MyAccountFragment();
                        loadFragment(fragment);
                        checkVerification();
                        break;

//                    case R.id.tasks:
//                        getSupportActionBar().setTitle("Tasks");
//                        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction3.replace(R.id.holder_layout, new TasksFragment());
//                        fragmentTransaction3.commit();
//                        break;
//
//                    case R.id.add_home_work:
//                        startActivity(new Intent(MainActivity.this, AddHomeWork.class));
//                        break;
//
//                    case R.id.exams:
//                        startActivity(new Intent(MainActivity.this, AddExams.class));
//                        break;
//
//
//                    case R.id.grades:
//                        getSupportActionBar().setTitle("Grades");
//                        FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction4.replace(R.id.holder_layout, new GradesFragment());
//                        fragmentTransaction4.commit();
//                        break;
//
//                    case R.id.courses:
//                        getSupportActionBar().setTitle("Courses");
//                        FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction5.replace(R.id.holder_layout, new CoursesFragment());
//                        fragmentTransaction5.commit();
//                        break;
//
//                    case R.id.notes:
//                        getSupportActionBar().setTitle("Notes");
//                        FragmentTransaction fragmentTransaction6 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction6.replace(R.id.holder_layout, new NotesFragment());
//                        fragmentTransaction6.commit();
//                        break;
//
                    case R.id.logout_nav:
                        mFireAuth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        fragment = new DashboardFragment();
                        loadFragment(fragment);
                        checkVerification();
                        return true;

                    case R.id.courses:
                        fragment = new CoursesFragment();
                        loadFragment(fragment);
                        checkVerification();
                        return true;


                    case R.id.refferals:
                        fragment = new ReferralsFragment();
                        loadFragment(fragment);
                        checkVerification();
                        return true;

                    case R.id.transactions:
                        fragment = new TransactionsFragment();
                        loadFragment(fragment);
                        checkVerification();
                        return true;

                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        FirebaseUser currentUser = mFireAuth.getCurrentUser();
//        if (currentUser == null) {
////            sendToStart();
//        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkVerification() {
        if (mFireAuth.getCurrentUser().isEmailVerified()) {
        } else {
            snackbar = Snackbar.make(bottomNavigationView, "Please verify your email address and sign in again to enjoy our services", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.bgcolor));
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
