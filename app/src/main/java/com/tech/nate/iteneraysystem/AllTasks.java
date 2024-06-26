package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class AllTasks extends AppCompatActivity implements TaskAdapter.TaskClickedInterface {

    private RecyclerView taskRecycler;
    private ImageView backBtn;
    private TextView title;
    private ArrayList<Model> tasks;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        initView();
        String taskType = getIntent().getStringExtra("taskType");
        title.setText("All " + (taskType.equals("today") ? "Today's" : "Tomorrow's"));

        taskRecycler.setHasFixedSize(true);
        tasks = new ArrayList<>();

        taskAdapter = new TaskAdapter(this, tasks, this);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskRecycler.setAdapter(taskAdapter);

        fetchTasksFromFirebase();

        backBtn.setOnClickListener(v -> this.finish());
    }

    private void initView() {
        taskRecycler = findViewById(R.id.task_recycler);
        title = findViewById(R.id.title);
        backBtn = findViewById(R.id.back);
    }

    private void fetchTasksFromFirebase() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child("Itinerary").child(Objects.requireNonNull(firebaseAuth.getUid()));

        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    String taskId = taskSnapshot.child("taskId").getValue(String.class);
                    String taskName = taskSnapshot.child("taskName").getValue(String.class);
                    String taskDescription = taskSnapshot.child("taskDescription").getValue(String.class);
                    String startTime = taskSnapshot.child("startTime").getValue(String.class); // Adjust this according to your data structure
                    String endTime = taskSnapshot.child("endTime").getValue(String.class); // Adjust this according to your data structure
                    String day = taskSnapshot.child("day").getValue(String.class);
                    String priority = taskSnapshot.child("priority").getValue(String.class);
                    String taskState = taskSnapshot.child("taskState").getValue(String.class);
                    String dateStr = taskSnapshot.child("date").getValue(String.class);


                    // Parse other fields as needed from dataSnapshot

                    // Create Model object and add to tasks list
                    Model taskModel = new Model();
                    taskModel.setTaskId(taskId);
                    taskModel.setTaskName(taskName);
                    taskModel.setTaskDescription(taskDescription);
                    taskModel.setStartTime(startTime);
                    taskModel.setEndTime(endTime);
                    taskModel.setDay(day);
                    taskModel.setPriority(Model.Priority.valueOf(priority));
                    taskModel.setTaskState(Model.TaskState.valueOf(taskState));


                    if (dateStr != null) {
                        SimpleDateFormat firebaseDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            Date date = firebaseDateFormat.parse(dateStr);
                            if (date != null) {
                                SimpleDateFormat displayDateFormat = new SimpleDateFormat("d, MMM", Locale.getDefault());
                                String displayDate = displayDateFormat.format(date);
                                // Update the UI with the formatted date
                                //etDate.setText(displayDate);
                                taskModel.setDisplayDate(displayDate);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                    // Set other fields in taskModel

                    tasks.add(taskModel);
                }
                taskAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    @Override
    public void onTaskClicked(int position) {
       taskEdit(tasks.get(position));
    }

    private void taskEdit(Model model){
        Intent intent = new Intent(AllTasks.this, EditTask.class);
        intent.putExtra("tasks", model);
        startActivity(intent);
    }
}

