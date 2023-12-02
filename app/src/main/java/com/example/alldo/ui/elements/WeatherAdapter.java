package com.example.alldo.ui.elements;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldo.R;
import com.example.alldo.data.models.WeatherTask;

import java.util.Calendar;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
//    GLOBAL VARIABLES
    private final List<WeatherTask> dataset;
    private final OnWeatherItemClickListener listener;
    private final Context context;

//    STEP 1: Initialize dataset
    public WeatherAdapter(Context context,List<WeatherTask> dataset, OnWeatherItemClickListener listener){
        this.context = context;
        this.dataset = dataset;
        this.listener = listener;
    }

//    STEP 2: Create new views
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_task_item,parent,false);
        return new WeatherAdapter.ViewHolder(view);
    }

//    STEP 3: Replace contents of the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout pl = holder.getParentLayout();
        TextView titleTv = pl.findViewById(R.id.titleWeatherTask);
        TextView descTv = pl.findViewById(R.id.descWeatherTask);
        CheckBox completedCheckbox = pl.findViewById(R.id.completed_checkbox_weather);
        CheckBox markImpCheckBox = pl.findViewById(R.id.markImpWeatherTask);
        TextView wtiTime = pl.findViewById(R.id.time_range_tv_wti);
        TextView wtiTemp = pl.findViewById(R.id.temp_range_tv_wti);

        WeatherTask data = dataset.get(position);
        Calendar[] timeRange = {data.getWeather().getMin_time(),data.getWeather().getMax_time()};
        float[] tempRange = {data.getWeather().getMin_temp(),data.getWeather().getMax_temp()};

        holder.bind(completedCheckbox,data,pl,markImpCheckBox,listener);

        titleTv.setText(data.getTitle());
        descTv.setText(data.getDetails());
        descTv.setVisibility(View.GONE);

        if (descTv.getText()!=null && !descTv.getText().equals(""))
            descTv.setVisibility(View.VISIBLE);

        if (data.isCheck()){
            completedCheckbox.setChecked(true);
            descTv.setPaintFlags(descTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            descTv.setAlpha(0.4f);
            titleTv.setPaintFlags(titleTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            titleTv.setAlpha(0.55f);
        }

        if(data.isMarkImp()){
            markImpCheckBox.setChecked(true);
            pl.setBackground(context.getDrawable(R.drawable.mark_imp_bg));
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.weatherTaskll);
        }

        public void bind(final CheckBox completed, final WeatherTask weatherTask, final LinearLayout ll,final CheckBox markImp, final OnWeatherItemClickListener listener){

            completed.setOnClickListener(v->{listener.onItemTaskCheckClick(completed,weatherTask,ll);});

            markImp.setOnClickListener(v->{listener.onItemTaskMarkImpClick(weatherTask,markImp,ll);});

            ll.setOnClickListener(v->{listener.onItemTaskBodyClick(weatherTask,ll);});
        }

        LinearLayout getParentLayout(){
            return parentLayout;
        }
    }

}
