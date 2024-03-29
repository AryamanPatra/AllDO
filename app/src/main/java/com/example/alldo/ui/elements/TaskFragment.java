package com.example.alldo.ui.elements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alldo.R;
import com.example.alldo.data.models.SimpleTask;
import com.example.alldo.databinding.ActivityHomeBinding;
import com.example.alldo.ui.states.SimpleTaskViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }
    public TaskFragment(ActivityHomeBinding homeBinding){
        this.homeBinding = homeBinding;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    RecyclerView rv;
    List<SimpleTask> dataset;
    ActivityHomeBinding homeBinding;
    CustomAdapter customAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback;
    private SimpleTaskViewModel simpleTaskViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        simpleTaskViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(SimpleTaskViewModel.class);
        dataset = new ArrayList<>();

        rv = requireView().findViewById(R.id.taskRVs);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

//        To add custom on Item onClick listener to recycler view
        customAdapter = new CustomAdapter(this.getActivity(),dataset, new OnItemClickListener() {
            @Override
            public void onItemTaskCheckClick(CheckBox checkBox,SimpleTask simpleTask, TextView textView) {
                LinearLayout ll = (LinearLayout)textView.getParent();
                TextView descTv = ll.findViewById(R.id.descSimpleTask);
                LinearLayout alarmLl = ll.findViewById(R.id.alarmSimpleTask);
                if(checkBox.isChecked()) {
                    descTv.setPaintFlags(descTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    descTv.setAlpha(0.4f);
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    textView.setAlpha(0.55f);
                    alarmLl.setVisibility(View.GONE);
                    simpleTask.setCheck(true);
                    simpleTaskViewModel.update(simpleTask);
                }
                else {
                    descTv.setPaintFlags(descTv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    descTv.setAlpha(0.8f);
                    textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    textView.setAlpha(1f);
                    if(!((TextView)alarmLl.findViewById(R.id.alarmDateSimpleTask)).getText().toString().equals("Tomorrow"))
                        alarmLl.setVisibility(View.VISIBLE);
                    simpleTask.setCheck(false);
                    simpleTaskViewModel.update(simpleTask);
                }
            }

            @Override
            public void onItemTaskBodyClick(SimpleTask simpleTask, TextView textView) {
                Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                intent.putExtra("chosenTaskID",simpleTask.getId());
                startActivity(intent);
            }

            @Override
            public void onItemTaskMarkImpClick(SimpleTask simpleTask,CheckBox checkBox, TextView textView) {
                LinearLayout ll = (LinearLayout) ((ViewGroup)textView.getParent());
                if(checkBox.isChecked()){
                    ll.setBackground(getResources().getDrawable(R.drawable.mark_imp_bg));
                }
                else{
                    ll.setBackground(new ColorDrawable(Color.TRANSPARENT));
                }
                simpleTask.setMarkImp(checkBox.isChecked());
                simpleTaskViewModel.update(simpleTask);
            }
        });
        rv.setAdapter(customAdapter);

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                SimpleTask deleteTask = dataset.get(viewHolder.getAdapterPosition());
                dataset.remove(viewHolder.getAdapterPosition());
                simpleTaskViewModel.delete(deleteTask);
                customAdapter.notifyDataSetChanged();
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rv);

//        loading stored data from database
        simpleTaskViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<SimpleTask>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<SimpleTask> simpleTasks) {
                Log.d("Room debug",simpleTasks.size()+"");
                dataset.clear();
                dataset.addAll(simpleTasks);
                customAdapter.notifyDataSetChanged();
            }
        });

    }

//    Passing data from Host Activity to create Task in this fragment
    @SuppressLint("NotifyDataSetChanged")
    public void updateTaskList(SimpleTask task){
        simpleTaskViewModel.insert(task);
        customAdapter.notifyDataSetChanged();
    }
    public int getDataSetSize(){
        return dataset.size();
    }
}