package com.programminginmyway.todoappnew;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddTaskDialogFragment extends AppCompatDialogFragment {
    private EditText editTextTask;
    private EditText editTextDate;
    private Spinner spinnerCategory;

    public static AddTaskDialogFragment newInstance() {

        Bundle args = new Bundle();

        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_fragment_dialog_addtask, null);
        alertDialog.setView(view);
        alertDialog.setTitle(R.string.dialog_title);
        alertDialog.setNegativeButton(R.string.text_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton(R.string.button_text_login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        editTextTask = view.findViewById(R.id.edittext_task);
        editTextDate = view.findViewById(R.id.edittext_due_date);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        return  alertDialog.create();
    }
}
