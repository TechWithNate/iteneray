package com.tech.nate.iteneraysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.nate.iteneraysystem.adapter.DateAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateTask extends AppCompatActivity {

    private ImageView back;
    private TextView etDate;
    private TextView etDay;
    private MaterialCardView dateLayout;
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
    private TextView startTimeText;
    private TextView endTimeText;

    private int selectedHourStart;
    private int selectedMinuteStart;
    private int selectedHourEnd;
    private int selectedMinuteEnd;
    private String taskStartTime, taskEndTime;

    private String fdate;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean alert;
    String task_name;
    String task_description;
    private Model taskModel;

    private DatePickerDialog.OnDateSetListener setListener;


    // Task Detail to db
    String tName;
    String tDesc;
    String tSTime;
    String tETime;
    String tPriority;
    String tState;
    boolean tGetAlert;
    String tDate;
    String tDay;
    String tDisDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        initViews();
        taskModel = new Model();


        back.setOnClickListener(v -> {
            finish();
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Set the initial date and day to the current date
        setInitialDateAndDay(calendar);

        // Initialize the DatePickerDialog.OnDateSetListener before creating the dialog
        DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // month is 0-based, so we need to add 1 to display the correct month
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());

                String formattedDate = dateFormat.format(date);
                String formattedDay = dayFormat.format(date);

                etDate.setText(formattedDate);
                etDay.setText(formattedDay);
                tDate = formattedDate;
                tDay = formattedDay;
//                taskModel.setDate(formattedDate);
//                taskModel.setDay(formattedDay);
            }
        };

        dateLayout.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CreateTask.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day
            );
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            datePickerDialog.show();
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(true);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showTimePickerDialog(false);
            }
        });

        // Iterate through each chip in the ChipGroup
        for (int i = 0; i < priorityChip.getChildCount(); i++) {
            Chip chip = (Chip) priorityChip.getChildAt(i);
            // Set OnClickListener for each chip
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the text of the clicked chip
                    String chipText = ((Chip) v).getText().toString();

                    // Show which chip is selected
                    switch (chipText) {
                        case "High":
                            //taskModel.setPriority(Model.Priority.HIGH);
                            tPriority = Model.Priority.HIGH.toString();
                            break;
                        case "Medium":
                            //taskModel.setPriority(Model.Priority.MEDIUM);
                            tPriority = Model.Priority.MEDIUM.toString();
                            break;
                        case "Low":
                            //taskModel.setPriority(Model.Priority.LOW);
                            tPriority = Model.Priority.LOW.toString();
                            break;
                        default:
                            // Handle other cases if necessary

                            break;
                    }

                    // Reset chip colors
                    for (int j = 0; j < priorityChip.getChildCount(); j++) {
                        Chip chip = (Chip) priorityChip.getChildAt(j);
                        chip.setChipBackgroundColorResource(R.color.dark);
                        chip.setTextColor(getResources().getColor(R.color.white));
                    }

                    // Set selected chip colors
                    chip.setChipBackgroundColorResource(android.R.color.white);
                    chip.setTextColor(getResources().getColor(android.R.color.black));
                }
            });
        }


        //taskModel.setTaskState(Model.TaskState.INCOMPLETE);
        tState = String.valueOf(Model.TaskState.INCOMPLETE);
        getAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the value to true when the switch is checked and false when not
                alert = isChecked;

                // Handle the color changes based on the state of the switch
                if (isChecked) {
                    // Switch is checked (ON), handle color accordingly
                    getAlert.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    getAlert.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                    tGetAlert = alert;
                    //taskModel.setGetAlert(alert);
                    //Toast.makeText(CreateTask.this, taskModel.getGetAlert().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    // Switch is unchecked (OFF), handle color accordingly
                    getAlert.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.white))); // Change to desired color
                    getAlert.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey))); // Change to desired color
                    tGetAlert = alert;
                    //taskModel.setGetAlert(alert);
                    //Toast.makeText(CreateTask.this, taskModel.getGetAlert().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        createTaskBtn.setOnClickListener(v -> {
          createTask();
        });


    }

    private void showTimePickerDialog(final boolean isStartTime) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (isStartTime) {
                            selectedHourStart = hourOfDay;
                            selectedMinuteStart = minute;
                            startTimeText.setText(formatTime(selectedHourStart, selectedMinuteStart));
                            //taskStartTime = formatTime(selectedHourStart, selectedMinuteStart);
                            //taskModel.setStartTime(formatTime(selectedHourStart, selectedMinuteStart));
                            tSTime = formatTime(selectedHourStart, selectedMinuteStart);
                        } else {
                            selectedHourEnd = hourOfDay;
                            selectedMinuteEnd = minute;
                            endTimeText.setText(formatTime(selectedHourEnd, selectedMinuteEnd));
                            //taskEndTime = formatTime(selectedHourEnd, selectedMinuteEnd);
                            //taskModel.setEndTime(formatTime(selectedHourEnd, selectedMinuteEnd));
                            tETime = formatTime(selectedHourEnd, selectedMinuteEnd);
                        }
                    }
                }, hour, minute, false);



        timePickerDialog.show();
    }


    private String formatTime(int hourOfDay, int minute) {
        String time;
        String period = "AM";
        if (hourOfDay >= 12) {
            period = "PM";
            if (hourOfDay != 12) {
                hourOfDay -= 12;
            }
        }
        if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        time = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute, period);
        return time;
    }

    private void initViews(){
        back = findViewById(R.id.back);
        dateLayout = findViewById(R.id.date_layout);
        etDate = findViewById(R.id.etDate);
        taskName = findViewById(R.id.task_name);
        taskDescription = findViewById(R.id.task_description);
        startTime = findViewById(R.id.start_time_layout);
        endTime = findViewById(R.id.end_time_layout);
        startTimeText = findViewById(R.id.start_time);
        endTimeText = findViewById(R.id.end_time);
        priorityChip = findViewById(R.id.priority_chip);
        highPriority = findViewById(R.id.priority_high);
        mediumPriority = findViewById(R.id.priority_medium);
        lowPriority = findViewById(R.id.priority_low);
        getAlert = findViewById(R.id.get_alert);
        createTaskBtn = findViewById(R.id.create_task);
        etDay = findViewById(R.id.etDay);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Itinerary").child(Objects.requireNonNull(firebaseAuth.getUid()));
    }

    private void setInitialDateAndDay(Calendar calendar) {
        Date date = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());

        String formattedDate = dateFormat.format(date);
        String formattedDay = dayFormat.format(date);

        etDate.setText(formattedDate);
        etDay.setText(formattedDay);
        tDate = formattedDate;
        tDay = formattedDay;
//        taskModel.setDate(formattedDate);
//        taskModel.setDay(formattedDay);
    }

    private void createTask(){
        if (taskName.getText().toString().isEmpty() && taskDescription.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter task name and description", Toast.LENGTH_SHORT).show();
        }else {
            tName = taskName.getText().toString();
            tDesc = taskDescription.getText().toString();
//            taskModel.setTaskName(taskName.getText().toString());
//            taskModel.setTaskDescription(taskDescription.getText().toString());
            addToDatabase();
        }
    }

    private void addToDatabase() {
        String pushId = databaseReference.push().getKey();
        if (pushId != null) {
            Model taskModal = new Model(pushId, tName, tDesc, tSTime, tETime, Model.Priority.valueOf(tPriority), Model.TaskState.valueOf(tState), tGetAlert, tDate, tDay);
            databaseReference.child(pushId).setValue(taskModal).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateTask.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                    openHomeActivity();
                } else {
                    Toast.makeText(CreateTask.this, "Task failed to add", Toast.LENGTH_SHORT).show();
                    Log.d("Task Failed", "onComplete: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Failed to create task ID", Toast.LENGTH_SHORT).show();
        }
    }




    private void openHomeActivity(){
        startActivity(new Intent(CreateTask.this, Home.class));
        finish();
    }

}