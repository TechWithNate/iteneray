package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Home extends AppCompatActivity implements TaskAdapter.TaskClickedInterface {

    private MaterialCardView search;
    private TextView seeAll;
    private TextView seeAll1;
    private TextView seeAll2;
    private TextView dailyTaskCompleted;
    private TextView progressPercentage;
    private ProgressBar progressBar;
    private RecyclerView todayRecycler;
    private FloatingActionButton addFab;
    private TextView dailyTask;

    private ArrayList<Model> tasks;
    private TaskAdapter taskAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();


        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, tasks, this);
        todayRecycler.setHasFixedSize(true);
        todayRecycler.setLayoutManager(layoutManager);
        todayRecycler.setAdapter(taskAdapter);


        fetchTasksFromFirebase();
        dailyTask.setText("This are you tasks today");




        seeAll1.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, AllTasks.class);
            intent.putExtra("taskType", "today");
            startActivity(intent);
        });


        addFab.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, CreateTask.class));
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                taskAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void initViews(){
        searchBar = findViewById(R.id.search_btn);
        //seeAll = findViewById(R.id.see_all);
        seeAll1 = findViewById(R.id.see_all1);
        //dailyTaskCompleted = findViewById(R.id.daily_task_completed);
        ///progressBar = findViewById(R.id.progress_bar);
        //progressPercentage = findViewById(R.id.progress_percentage);
        todayRecycler = findViewById(R.id.today_recycler);
        addFab = findViewById(R.id.add_fab);
        layoutManager = new LinearLayoutManager(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dailyTask = findViewById(R.id.daily_task);
    }


    private void fetchTasksFromFirebase() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference().child("Itinerary").child(Objects.requireNonNull(firebaseAuth.getUid()));

        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
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
                    taskModel.setTaskName(taskName);
                    taskModel.setTaskDescription(taskDescription);
                    taskModel.setStartTime(startTime);
                    taskModel.setEndTime(endTime);
                    taskModel.setDay(day);
//                    taskModel.setPriority(Model.Priority.valueOf(priority));
//                    taskModel.setTaskState(Model.TaskState.valueOf(taskState));


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
        tasks.get(position);
        Toast.makeText(this, "Task: "+position, Toast.LENGTH_SHORT).show();
    }
}