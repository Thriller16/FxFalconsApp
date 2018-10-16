package app.falconforex.com.falconforex;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    NavigationView navigationView;
    BottomNavigationViewEx bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
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

        //This is to load the home fragment at the beginning of the process beggining process
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.holder_layout, new HomeFragment());
//        fragmentTransaction.commit();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.home:
//                        getSupportActionBar().setTitle("School Planner");
//                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction1.replace(R.id.holder_layout, new HomeFragment());
//                        fragmentTransaction1.commit();
//                        break;
//
//                    case R.id.calender:
//                        getSupportActionBar().setTitle("Calender");
//                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction2.replace(R.id.holder_layout, new CalenderFragment());
//                        fragmentTransaction2.commit();
//                        break;
//
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
//                    case R.id.logout_nav:
//                        mFireAuth.signOut();
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                }
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
