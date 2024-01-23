package com.tech.nate.iteneraysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AllTasks extends AppCompatActivity {

    private RecyclerView taskRecycler;
    private ImageView backBtn;
    private TextView title;
    private ArrayList<Model> todayTask;
    private ArrayList<Model> tomorrowTask;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        initView();
        String taskType = getIntent().getStringExtra("taskType");
        title.setText("All " +(taskType.equals("today") ? "Today's" : "Tomorrow's"));

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 45));


        taskRecycler.setHasFixedSize(true);

        todayTask = new ArrayList<>();
        todayTask.add(new Model("Mobile Dev", "Develop a flutter application", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Gym", "Going to the gymn", startTime, endTime, Model.Priority.MEDIUM, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Windows", "Setting up new windows", startTime, endTime, Model.Priority.LOW, Model.TaskState.COMPLETED));
        todayTask.add(new Model("Job", "Local Testing", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Mobile Dev", "Develop a flutter application", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Gym", "Going to the gymn", startTime, endTime, Model.Priority.MEDIUM, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Windows", "Setting up new windows", startTime, endTime, Model.Priority.LOW, Model.TaskState.COMPLETED));
        todayTask.add(new Model("Job", "Local Testing", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Mobile Dev", "Develop a flutter application", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Gym", "Going to the gymn", startTime, endTime, Model.Priority.MEDIUM, Model.TaskState.INCOMPLETE));
        todayTask.add(new Model("Windows", "Setting up new windows", startTime, endTime, Model.Priority.LOW, Model.TaskState.COMPLETED));
        todayTask.add(new Model("Job", "Local Testing", startTime, endTime, Model.Priority.HIGH, Model.TaskState.INCOMPLETE));

        taskAdapter = new TaskAdapter(this, todayTask, 3);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskRecycler.setAdapter(taskAdapter);



    }


    private void initView(){
        taskRecycler = findViewById(R.id.task_recycler);
        title = findViewById(R.id.title);
        backBtn = findViewById(R.id.back);
    }
}