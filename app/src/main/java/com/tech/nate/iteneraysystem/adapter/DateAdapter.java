package com.tech.nate.iteneraysystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.nate.iteneraysystem.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<Calendar> dates;
    private Context context;



    public DateAdapter(Context context) {
        this.context = context;
        this.dates = generateDates();
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Calendar date = dates.get(position);

        holder.itemView.setTag(date);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int adapterPosition = holder.getBindingAdapterPosition();
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    setSelectedPosition(adapterPosition);
//                    Calendar selectedDate = dates.get(adapterPosition);
//                    if (selectedDate != null && dateItemClickListener != null) {
//                        String formattedSelectedDate = new SimpleDateFormat("EEE MMM dd, yyyy", Locale.getDefault()).format(selectedDate.getTime());
//                        dateItemClickListener.onDateItemClick(formattedSelectedDate);
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }


    public static class DateViewHolder extends RecyclerView.ViewHolder {

        private TextView textDate;
        private TextView textDay;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textDay = itemView.findViewById(R.id.textDay);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bind(Calendar date, boolean isSelected) {
            String formattedDay = new SimpleDateFormat("EEE", Locale.getDefault()).format(date.getTime());
            String formattedDate = new SimpleDateFormat("dd", Locale.getDefault()).format(date.getTime());
            textDate.setText(formattedDay);
            textDay.setText(formattedDate);

            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.selected_bg);
            } else {
                itemView.setBackgroundResource(android.R.color.transparent);
            }
        }
    }

    private List<Calendar> generateDates() {
        List<Calendar> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            dates.add((Calendar) calendar.clone());
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

}

