package com.programminginmyway.todoappnew.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programminginmyway.todoappnew.DialogCloseListener
import com.programminginmyway.todoappnew.data.database.roomDb.database.AppDatabase
import com.programminginmyway.todoappnew.data.database.roomDb.dao.TaskDao
import com.programminginmyway.todoappnew.data.database.roomDb.model.TaskList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.programminginmyway.todoappnew.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

class AddNewTask : BottomSheetDialogFragment() {

    private lateinit var newTaskText: EditText
    private lateinit var selectDateTimeButton: Button
    private lateinit var newTaskSaveButton: Button
    private lateinit var taskDao: TaskDao
    // Calendar instance to manage the selected date and time state
    private val calendar: Calendar = Calendar.getInstance()

    companion object {
        const val TAG = "ActionBottomDialog"

        fun newInstance(): AddNewTask {
            return AddNewTask()
            
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.new_task, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        // Initialize DAO
        taskDao = AppDatabase.getDatabase(requireContext()).taskDao()
        Log.d(TAG, "onCreateView: ")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        newTaskText = view.findViewById(R.id.newTaskText)
        newTaskSaveButton = view.findViewById(R.id.newTaskButton)
        selectDateTimeButton = view.findViewById(R.id.selectDateTimeButton)

        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            newTaskText.setText(task)
            // If a dateTime exists, parse it and set it
            // Check if the dateTime Long exists in the bundle
            if (bundle.containsKey("dateTime")) {
                // CORRECT WAY: Get the Long from the bundle.
                val dateTimeMillis = bundle.getLong("dateTime")

                // Set the Calendar instance's time using the Long.
                calendar.timeInMillis = dateTimeMillis

                // Update the UI button to show the correct date.
                updateDateTimeButtonText()
            }
            if (!task.isNullOrEmpty()) {
                newTaskSaveButton.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                )
            } else {
                newTaskSaveButton.visibility = View.GONE
            }
        }

        // Set an initial default date/time text for the button if not updating
        if (!isUpdate) {
            updateDateTimeButtonText()
        }

        newTaskText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    newTaskSaveButton.isEnabled = false
                    newTaskSaveButton.setTextColor(Color.GRAY)
                } else {
                    newTaskSaveButton.isEnabled = true
                    newTaskSaveButton.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Set a click listener for the date time button
        selectDateTimeButton.setOnClickListener {
            showDatePicker()
        }

        val finalIsUpdate = isUpdate
        newTaskSaveButton.setOnClickListener {
            val text = newTaskText.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                val taskId = bundle!!.getInt("id")
                val task = TaskList(
                    id = taskId,
                    status = 0,
                    taskTitle = text,
                    dateTime = calendar.time
                )
                if (finalIsUpdate) {
                    taskDao.updateTask(task)
                } else {
                    taskDao.insertTask(task)
                }
            }
            dismiss()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                // After picking the date, immediately show the TimePicker
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // --- NEW: Function to show the TimePickerDialog ---
    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                // Update the button text with the selected date and time
                updateDateTimeButtonText()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            // Use 'true' for 24-hour format or 'false' for AM/PM format
            true
        )
        timePickerDialog.show()
    }

    // --- NEW: Helper function to update the button's text ---
    private fun updateDateTimeButtonText() {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        selectDateTimeButton.text = sdf.format(calendar.time)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity: Activity? = getActivity()
        if (activity is DialogCloseListener) {
            activity.handleDialogClose(dialog)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: ")
    }
    
}