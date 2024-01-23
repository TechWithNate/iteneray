package com.tech.nate.iteneraysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private MaterialCardView search;
    private TextView seeAll;
    private TextView seeAll1;
    private TextView seeAll2;
    private TextView dailyTaskCompleted;
    private TextView progressPercentage;
    private ProgressBar progressBar;
    private RecyclerView todayRecycler;
    private RecyclerView tomorrowRecycler;
    private FloatingActionButton addFab;
    private ArrayList<Model> todayTask;
    private ArrayList<Model> tomorrowTask;
    private TaskAdapter taskAdapter;
    private TaskAdapter tomorrowAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        //Sample Time
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 45));

        todayRecycler.setHasFixedSize(true);
        todayTask = new ArrayList<>();
        todayTask.add(new Model("Mobile Dev", "Develop a flutter application", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Gym", "Going to the gymn", startTime, endTime, Model.Priority.MEDIUM, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Windows", "Setting up new windows", startTime, endTime, Model.Priority.LOW, Model.TaskState.COMPLETED));
        todayTask.add(new Model("Job", "Local Testing", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));

        taskAdapter = new TaskAdapter(this, todayTask, 3);
        todayRecycler.setLayoutManager(layoutManager);
        todayRecycler.setAdapter(taskAdapter);

        tomorrowRecycler.setHasFixedSize(true);
        tomorrowTask = new ArrayList<>();
        tomorrowTask.add(new Model("Assemble", "Develop a flutter application", startTime, endTime, Model.Priority.LOW, Model.TaskState.INCOMPLETE));
        tomorrowTask.add(new Model("Job", "Going to the gymn", startTime, endTime, Model.Priority.MEDIUM, Model.TaskState.INCOMPLETE));
        tomorrowTask.add(new Model("Shop", "Setting up new windows", startTime, endTime, Model.Priority.LOW, Model.TaskState.COMPLETED));
        tomorrowTask.add(new Model("Job", "Local Testing", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        tomorrowAdapter = new TaskAdapter(this, tomorrowTask, 3);
        tomorrowRecycler.setLayoutManager(layoutManager);
        tomorrowRecycler.setAdapter(tomorrowAdapter);





        addFab.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, CreateTask.class));
        });
    }

    private void initViews(){
        search = findViewById(R.id.search);
        seeAll = findViewById(R.id.see_all);
        seeAll1 = findViewById(R.id.see_all1);
        seeAll2 = findViewById(R.id.see_all2);
        dailyTaskCompleted = findViewById(R.id.daily_task_completed);
        progressBar = findViewById(R.id.progress_bar);
        progressPercentage = findViewById(R.id.progress_percentage);
        todayRecycler = findViewById(R.id.today_recycler);
        tomorrowRecycler = findViewById(R.id.tomorrow_recycler);
        addFab = findViewById(R.id.add_fab);
        layoutManager = new LinearLayoutManager(this);
    }

}