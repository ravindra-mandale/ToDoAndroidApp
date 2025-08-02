package com.programminginmyway.todoappnew;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListTaskFragment extends Fragment {
    private TextView textViewTaskTitle, textViewDateTime, textViewCategory;
    public ListTaskFragment() {
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        Log.d("#####", "ListTaskFragment#setArguments: 1");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("#####", "ListTaskFragment#onAttach: 2");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("#####", "ListTaskFragment#onCreate: 3");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("#####", "ListTaskFragment#onCreateView: 4");
        return inflater.inflate(R.layout.fragment_list_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewTaskTitle = view.findViewById(R.id.task_title);
        textViewDateTime = view.findViewById(R.id.task_date_time);
        textViewCategory = view.findViewById(R.id.task_date_category);
        Log.d("#####", "ListTaskFragment#onViewCreated: 5");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("#####", "ListTaskFragment#onStart: 6");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("#####", "ListTaskFragment#onResume: 7");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("#####", "ListTaskFragment#onPause: 8");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("#####", "ListTaskFragment#onStop: 9");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("#####", "ListTaskFragment#onDestroyView: 10");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("#####", "ListTaskFragment#onDestroy: 11");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("#####", "ListTaskFragment#onDetach: 12");
    }
}
