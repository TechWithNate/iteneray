package com.tech.nate.iteneraysystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Model> tasks;
    private ArrayList<Model> taskModelsFull;
    private TaskClickedInterface taskClickedInterface;

    public TaskAdapter(Context context, ArrayList<Model> tasks, TaskClickedInterface taskClickedInterface) {
        this.context = context;
        this.tasks = tasks;
        this.taskModelsFull = new ArrayList<>(tasks);
        this.taskClickedInterface = taskClickedInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model taskModel = tasks.get(position);

        holder.taskName.setText(taskModel.getTaskName());

        holder.date.setText(taskModel.getDay());

//        if (taskModel.getPriority().equals(Model.Priority.HIGH)) {
//            holder.priority.setBackgroundColor(Color.parseColor("#FACBBA"));
//        } else if (taskModel.getPriority().equals(Model.Priority.MEDIUM)) {
//            holder.priority.setBackgroundColor(Color.parseColor("#FAD9FF"));
//        } else {
//            holder.priority.setBackgroundColor(Color.parseColor("#D7F0FF"));
//        }
//
//        holder.status.setChecked(taskModel.getTaskState().equals(Model.TaskState.COMPLETED));

        holder.itemView.setOnClickListener(v -> {
            taskClickedInterface.onTaskClicked(position);
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Model> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(taskModelsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Model item : taskModelsFull) {
                        if (item.getTaskName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tasks.clear();
                tasks.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName;
        private TextView date;
        private MaterialRadioButton status;
        private RelativeLayout priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            date = itemView.findViewById(R.id.date); // Example, adjust as per your layout
            status = itemView.findViewById(R.id.status); // Example, adjust as per your layout
            priority = itemView.findViewById(R.id.priority_color); // Example, adjust as per your layout

        }
    }

    public interface TaskClickedInterface{
        void onTaskClicked(int position);
    }
}
