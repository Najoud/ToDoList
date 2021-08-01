package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.todolist.Model.DialogCloseListener;
import com.example.todolist.Model.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Uitals.DatabaseHandler;
import com.example.todolist.Uitals.RecyclerItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends  AppCompatActivity implements DialogCloseListener {
    public RecyclerView tasksRecycler;
    public ToDoAdapter taskAdapter;
    public List<ToDoModel> taskList;
    public FloatingActionButton fab;
    public DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        getSupportActionBar().hide();


        taskList = new ArrayList<>();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecycler = findViewById(R.id.tasksRecyclerView);
        tasksRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(db,MainActivity.this);
        tasksRecycler.setAdapter(taskAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecycler);

        fab = findViewById(R.id.fab);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        taskAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });


    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        MenuItem item = menu.findItem(R.id.search_bar);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreatOptionMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }


}