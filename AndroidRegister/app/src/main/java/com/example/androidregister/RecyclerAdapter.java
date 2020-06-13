package com.example.androidregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Schedule> schedules;

    public RecyclerAdapter(Context context, List<Schedule> list)
    {
        this.inflater = LayoutInflater.from(context);
        this.schedules = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.schedule.setText(schedules.get(position).getSchedule());
        holder.scheduleTeacher.setText(schedules.get(position).getSchedule_teacher());

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView schedule, scheduleTeacher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schedule = itemView.findViewById(R.id.schedule);
            scheduleTeacher = itemView.findViewById(R.id.scheduleTeacher);
        }
    }

}
