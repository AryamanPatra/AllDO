package com.example.alldo.ui.elements;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;

import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

// GLOBAL VARIABLES
    private final List<SimpleTask> simpleTaskList;
    private final OnItemClickListener listener;
    private final Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            linearLayout = (LinearLayout) view.findViewById(R.id.simpleTaskll);
        }


        public LinearLayout getLinearLayout() {
            return linearLayout;
        }


        public void bind(final CheckBox checkBox,final SimpleTask simpleTask, final TextView textView,final CheckBox markImp, final OnItemClickListener listener) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemTaskCheckClick(checkBox,simpleTask, textView);
                }
            });

            markImp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemTaskMarkImpClick(simpleTask,markImp,textView);
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemTaskBodyClick(simpleTask,textView);
                }
            });
        }
    }

//    STEP 1: Initialize the dataset of adapter
    public CustomAdapter(Context context,List<SimpleTask> dataset,OnItemClickListener listener){
        this.context = context;
        simpleTaskList = dataset;
        this.listener = listener;
    }

//    STEP 2: Create new views
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_task_item,parent,false);
        return new ViewHolder(view);
    }

// STEP 3: Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView titleTv = (TextView) holder.getLinearLayout().findViewById(R.id.titleSimpleTask);
        TextView descTv = (TextView) holder.getLinearLayout().findViewById(R.id.descSimpleTask);
        LinearLayout alarmLl = (LinearLayout) holder.getLinearLayout().findViewById(R.id.alarmSimpleTask);
        LinearLayout parentLl = (LinearLayout) titleTv.getParent();
        CheckBox taskCheckbox = (CheckBox) holder.getLinearLayout().findViewById(R.id.check_box_task);
        CheckBox markImpCheckbox = (CheckBox) holder.getLinearLayout().findViewById(R.id.markImpSimpleTask);
        TextView alarmTimeTv = (TextView) alarmLl.findViewById(R.id.alarmTimeSimpleTask);
        TextView alarmDateTv = (TextView) alarmLl.findViewById(R.id.alarmDateSimpleTask);
        ImageView alarmRepeatImv = (ImageView) alarmLl.findViewById(R.id.alarmRepeatImv);
        holder.bind(holder.getLinearLayout().findViewById(R.id.check_box_task),simpleTaskList.get(position),holder.getLinearLayout().findViewById(R.id.titleSimpleTask), holder.getLinearLayout().findViewById(R.id.markImpSimpleTask), listener);
        titleTv.setText(simpleTaskList.get(position).getTitle());
        descTv.setText(simpleTaskList.get(position).getDetails());
        descTv.setVisibility(View.GONE);

        Calendar instanceCalendar = simpleTaskList.get(position).getAlarm();
        if(instanceCalendar!=null){
            alarmLl.setVisibility(View.VISIBLE);
            alarmTimeTv.setText(instanceCalendar.get(Calendar.HOUR_OF_DAY)+":"+instanceCalendar.get(Calendar.MINUTE));
            alarmDateTv.setText(instanceCalendar.get(Calendar.DAY_OF_MONTH)+"/"+(instanceCalendar.get(Calendar.MONTH)+1)+"/"+instanceCalendar.get(Calendar.YEAR));
            if(simpleTaskList.get(position).getRepeat()>0)
                alarmRepeatImv.setVisibility(View.VISIBLE);
        }

        if(!(simpleTaskList.get(position).getDetails().equals("") || simpleTaskList.get(position).getDetails()==null))
            descTv.setVisibility(View.VISIBLE);

        if(simpleTaskList.get(position).isCheck()){
            taskCheckbox.setChecked(simpleTaskList.get(position).isCheck());
            descTv.setPaintFlags(descTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            descTv.setAlpha(0.4f);
            titleTv.setPaintFlags(titleTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            titleTv.setAlpha(0.55f);
            alarmLl.setVisibility(View.GONE);
        }

        if(simpleTaskList.get(position).isMarkImp()){
            markImpCheckbox.setChecked(true);
            parentLl.setBackground(context.getDrawable(R.drawable.mark_imp_bg));
        }
    }

    @Override
    public int getItemCount() {
        return simpleTaskList.size();
    }

}
