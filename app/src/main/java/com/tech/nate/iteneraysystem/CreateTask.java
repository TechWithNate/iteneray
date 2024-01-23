package com.tech.nate.iteneraysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class CreateTask extends AppCompatActivity {

    private ImageView back;
    private ImageView leftSwipe;
    private ImageView rightSwipe;
    private TextView date;
    private RecyclerView dateRecycler;
    private EditText taskName;
    private EditText taskDescription;
    private RelativeLayout startTime;
    private RelativeLayout endTime;
    private ChipGroup priorityChip;
    private Chip highPriority;
    private Chip mediumPriority;
    private Chip lowPriority;
    private SwitchMaterial getAlert;
    private MaterialButton createTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initViews();

        createTaskBtn.setOnClickListener(v -> createTask());
    }

    private void initViews(){
        back = findViewById(R.id.back);
        leftSwipe = findViewById(R.id.swipe_left);
        rightSwipe = findViewById(R.id.swipe_right);
        date = findViewById(R.id.date);
        dateRecycler = findViewById(R.id.date_recycler);
        taskName = findViewById(R.id.task_name);
        taskDescription = findViewById(R.id.task_description);
        startTime = findViewById(R.id.start_time_layout);
        endTime = findViewById(R.id.end_time_layout);
        priorityChip = findViewById(R.id.priority_chip);
        highPriority = findViewById(R.id.priority_high);
        mediumPriority = findViewById(R.id.priority_medium);
        lowPriority = findViewById(R.id.priority_low);
        getAlert = findViewById(R.id.get_alert);
        createTaskBtn = findViewById(R.id.create_task);

    }

    private void createTask(){
        // TODO: create and send task to database
    }

}