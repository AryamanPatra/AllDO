package com.example.alldo.ui.elements;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldo.R;
import com.example.alldo.data.SimpleTask;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

// GLOBAL VARIABLES
    private final List<SimpleTask> simpleTaskList;
    private final OnItemClickListener listener;



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


        public void bind(final CheckBox checkBox, final TextView textView, final OnItemClickListener listener) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemTaskClick(checkBox, textView);
                }
            });
        }
    }

//    STEP 1: Initialize the dataset of adapter
    public CustomAdapter(List<SimpleTask> dataset,OnItemClickListener listener){
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
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        TextView tv = (TextView) holder.getLinearLayout().findViewById(R.id.titleSimpleTask);
        holder.bind(holder.getLinearLayout().findViewById(R.id.check_box_task),holder.getLinearLayout().findViewById(R.id.titleSimpleTask), listener);
        tv.setText(simpleTaskList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return simpleTaskList.size();
    }
}
