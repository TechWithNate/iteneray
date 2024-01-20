package com.tech.nate.iteneraysystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Model> tasks;
    private int taskSize;

    public TaskAdapter(Context context, ArrayList<Model> tasks, int taskSize) {
        this.context = context;
        this.tasks = tasks;
        this.taskSize = taskSize;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        Model taskModel = tasks.get(position);
        holder.taskName.setText(taskModel.getTaskName());
        //holder.date.setText(taskModel.getStartTime());

        if (taskModel.getPriority().equals(Model.Priority.HIGH)){
            holder.priority.setBackgroundColor(Color.parseColor("#FACBBA"));
        } else if (taskModel.getPriority().equals(Model.Priority.MEDIUM)) {
            holder.priority.setBackgroundColor(Color.parseColor("#FAD9FF"));
        }else {
            holder.priority.setBackgroundColor(Color.parseColor("#D7F0FF"));
        }

//        if (taskModel.getTaskState().equals(Model.TaskState.COMPLETED)){
//
//        }else {
//            holder.status.isChecked();
//        }

    }

    @Override
    public int getItemCount() {
        return taskSize;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName;
        private TextView date;
        private MaterialRadioButton status;
        private RelativeLayout priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.task_name);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            priority = itemView.findViewById(R.id.priority_color);
        }
    }

}
