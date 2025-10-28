package com.programminginmyway.todoappnew.screens

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.programminginmyway.todoappnew.adapters.TaskAdapter
import com.programminginmyway.todoappnew.DialogCloseListener
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.RecyclerItemTouchHelper
import com.programminginmyway.todoappnew.ShowAlertDialog
import com.programminginmyway.todoappnew.ThemeSetter
import com.programminginmyway.todoappnew.data.database.roomDb.database.AppDatabase
import com.programminginmyway.todoappnew.databinding.ActivityMainBinding
import com.programminginmyway.todoappnew.fragments.AddNewTask
import kotlinx.coroutines.launch
import com.programminginmyway.todoappnew.viewmodel.MainViewModel
import com.programminginmyway.todoappnew.viewmodel.MainViewModelFactory

class MainScreen: BaseSecureActivity(), DialogCloseListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksAdapter: TaskAdapter

    private val taskDao by lazy { AppDatabase.getDatabase(this).taskDao() }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(taskDao, application)
    }

    companion object {
        const val TAG = "MainScreen"
       // lateinit var context: Context
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        //context = this

        // ✅ Setup Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.d(TAG, "onCreate-Main")

        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = TaskAdapter(
            this,
            taskDao = taskDao,
            lifecycleOwner = this // Activity implements LifecycleOwner
        )
        binding.tasksRecyclerView.adapter = tasksAdapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView)

        // Load tasks from ViewModel
        viewModel.tasks.observe(this) { taskList ->
            tasksAdapter.setTasks(taskList)
            tasksAdapter.notifyDataSetChanged()
        }

        binding.floatingActionButton.setOnClickListener {
            showAddTaskFragment()
            //crash analytics demo code
//            FirebaseCrashlytics.getInstance().log("Test crash log")
//            FirebaseCrashlytics.getInstance().recordException(RuntimeException("Test Crash"))
            //throw NullPointerException()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })

        lifecycleScope.launch {
            if (viewModel.isLoggedIn()) return@launch
        }

        // Initial load
        viewModel.loadTasks()
    }

    private fun showAddTaskFragment() {
        AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.todo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.light_theme_menu -> {
                ThemeSetter.applyTheme(false)
                true
            }
            R.id.dark_theme_menu -> {
                ThemeSetter.applyTheme(true)
                true
            }
            R.id.logout_menu -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.alert_dialog_title_logout)
        builder.setMessage(R.string.alert_dialog_message_logout)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.positive_text) { _, _ ->
            viewModel.logout {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            }
        }
        builder.setNegativeButton(R.string.negative_text) { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        viewModel.loadTasks()
    }

    private fun showExitDialog() {
        val builder = ShowAlertDialog.createAlertDialogForCloseApp(this)
        builder.setPositiveButton(R.string.positive_text) { _, _ -> finishAffinity() }
        builder.setNegativeButton(R.string.negative_text) { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: -Main")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: -Main")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: -Main")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: -Main")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: -Main")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: -Main")
    }
}