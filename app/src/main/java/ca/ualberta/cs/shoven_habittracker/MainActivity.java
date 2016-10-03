/*
Habit Tracker: Android app to track daily habits.
Copyright (C) 2016  Sarah Hoven shoven@ualberta.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.ualberta.cs.shoven_habittracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private static final String FILENAME = "file.sav";
    private int dayOfWeek = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek() % 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WeeklyScheduleController.setContext(this);
        loadFromFile();

        TextView dateTextView = (TextView) findViewById(R.id.todayDateTextView);
        setDateToday(dateTextView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setNavigator(navigationView);

        ListView listView = (ListView) findViewById(R.id.mainHabitsListView);
        final ArrayList<Habit> habitList = WeeklyScheduleController.getWeeklySchedule().getDailySchedule(dayOfWeek).getHabits();
        final ArrayAdapter<Habit> habitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, habitList);
        listView.setAdapter(habitAdapter);

        listView.setOnItemClickListener(this);

        WeeklyScheduleController.getWeeklySchedule().addListener(new Listener() {
            @Override
            public void update() {
                habitAdapter.notifyDataSetChanged();
            }
        });
    }

    public void floatingActionButtonClicked(View v) {
        Intent intent = new Intent(MainActivity.this, NewHabitActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {

        } else if ( id == R.id.nav_all_habits) {
            Intent intent = new Intent(MainActivity.this, AllHabitsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.check_if_clear);
            builder.setPositiveButton(R.string.clear_all_data, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(MainActivity.this, "All data cleared", Toast.LENGTH_SHORT).show();
                    clearData();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDateToday(TextView textView) {
        LocalDateTime now = new LocalDateTime(DateTimeZone.forID("Canada/Mountain"));
        textView.setText(now.toString("EEEE, MMMM dd, yyyy", Locale.CANADA));
    }

    public void setNavigator(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, HabitHomepageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("activity", "MainActivity");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylist
            Type listType = new TypeToken<WeeklySchedule>(){}.getType();

            WeeklyScheduleController.setWeeklySchedule((WeeklySchedule) gson.fromJson(in, listType));
        } catch (FileNotFoundException e) {
            // Create a brand new weekly schedule if we can't find the file.
            WeeklyScheduleController.getWeeklySchedule();
        } catch (NullPointerException e) {
            WeeklyScheduleController.getWeeklySchedule();
        }
    }

    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(WeeklyScheduleController.getWeeklySchedule(), out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // rethrow
            throw new RuntimeException(e);
        } catch (IOException e) {
            // rethrow
            throw new RuntimeException(e);
        }
    }

    private void clearData() {
        File dir = getFilesDir();
        File file = new File(dir, FILENAME);
        try {
            boolean deleted = file.delete();
        } catch (SecurityException e) {
            throw new RuntimeException();
        }
    }
}
