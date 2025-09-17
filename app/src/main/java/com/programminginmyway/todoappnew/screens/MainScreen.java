package com.programminginmyway.todoappnew.screens;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.programminginmyway.todoappnew.Adapters.ToDoAdapter;
import com.programminginmyway.todoappnew.AddNewTask;
import com.programminginmyway.todoappnew.database.DatabaseHandler;
import com.programminginmyway.todoappnew.DialogCloseListener;
import com.programminginmyway.todoappnew.R;
import com.programminginmyway.todoappnew.RecyclerItemTouchHelper;
import com.programminginmyway.todoappnew.ShowAlertDialog;
import com.programminginmyway.todoappnew.ThemeSetter;
import com.programminginmyway.todoappnew.model.ToDoModelNew;

import java.util.Collections;
import java.util.List;

public class MainScreen extends BaseSecureActivity implements DialogCloseListener {
    SharedPreferences sp;
    public static final String USER_LOGIN_KEY = "USERLOGIN";
    private String keyDarkTheme = "isDarkTheme";
    private FloatingActionButton floatingActionButton;
    private TextView taskTextView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModelNew> taskList;
    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(getString(R.string.SHARED_PREFERENCE_NAME), MODE_PRIVATE);
        Log.d("####", "onCreate: ");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainScreen.this);
//                alertDialogBuilder.setTitle(R.string.alert_dialog_title_logout);
//                alertDialogBuilder.setMessage(R.string.alert_dialog_message_logout);
//                alertDialogBuilder.setCancelable(false);
//                alertDialogBuilder.setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putBoolean(USER_LOGIN_KEY, false);
//                        editor.apply();
//                        finish();
//                        startActivity(new Intent(MainScreen.this, LoginScreen.class));
//                    }
//                });
//                alertDialogBuilder.setNegativeButton(R.string.negative_text, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//            }
//        });

        db = new DatabaseHandler(this);
        db.openDatabase();
        taskTextView = findViewById(R.id.tasksText);
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, MainScreen.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            showAddTaskFragment();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });
    }

    private void showAddTaskFragment() {
//        AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance();
//        addTaskDialogFragment.show(getSupportFragmentManager(), "Add Task Dialog");
        AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.light_theme_menu) {
            ThemeSetter.applyTheme(false);
            return true;
        } else if (item.getItemId() == R.id.dark_theme_menu) {
            ThemeSetter.applyTheme(true);
            return true;
        } else if (item.getItemId() == R.id.logout_menu) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.alert_dialog_title_logout);
            alertDialogBuilder.setMessage(R.string.alert_dialog_message_logout);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(USER_LOGIN_KEY, false);
                    editor.apply();
                    finish();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainScreen.this, LoginScreen.class));
                }
            });
            alertDialogBuilder.setNegativeButton(R.string.negative_text, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("####", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("####", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("####", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("####", "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("####", "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("####", "onDestroy: ");
    }

    public void showExitDialog() {
        final AlertDialog.Builder alertDialogBuilder = ShowAlertDialog.createAlertDialogForCloseApp(this);
        alertDialogBuilder.setPositiveButton(R.string.positive_text, (dialog, which) -> {
            finishAffinity();
        });
        alertDialogBuilder.setNegativeButton(R.string.negative_text, (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}