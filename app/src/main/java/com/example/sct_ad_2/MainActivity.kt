package com.example.sct_ad_2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sct_ad_2.databinding.ActivityMainBinding

/**
 * MainActivity
 *
 * A simple To-Do List app that allows the user to:
 *  - Add a new task to a list
 *  - Delete a task by long-pressing on it
 *
 * All tasks are stored in memory only (ArrayList), meaning
 * the list will reset every time the app is closed/restarted.
 *
 * Uses:
 *  - ViewBinding for view access (no findViewById)
 *  - ListView + ArrayAdapter for displaying tasks
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding instance to safely access views from activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // In-memory list that holds all the task strings
    private val taskList = ArrayList<String>()

    // Adapter that connects taskList (data) to the ListView (UI)
    private lateinit var taskAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ListView, adapter, and button click listeners
        setupListView()
        setupAddButton()
        setupDeleteOnLongClick()
    }

    /**
     * Sets up the ArrayAdapter and attaches it to the ListView.
     */
    private fun setupListView() {
        // simple_list_item_1 is a built-in Android layout for a single line of text
        taskAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            taskList
        )
        binding.listViewTasks.adapter = taskAdapter
    }

    /**
     * Sets up the click listener for the "Add Task" button.
     * Validates that the input is not empty before adding.
     */
    private fun setupAddButton() {
        binding.btnAddTask.setOnClickListener {
            val taskText = binding.editTextTask.text.toString().trim()

            if (taskText.isEmpty()) {
                // Empty input validation
                Toast.makeText(this, getString(R.string.empty_task_warning), Toast.LENGTH_SHORT).show()
            } else {
                // Add the new task to the list
                taskList.add(taskText)

                // Refresh the ListView to show the newly added task
                taskAdapter.notifyDataSetChanged()

                // Clear the input field after adding
                binding.editTextTask.text.clear()

                // Show confirmation toast
                Toast.makeText(this, getString(R.string.task_added), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Sets up a long-click listener on the ListView items.
     * Long-pressing a task removes it from the list.
     */
    private fun setupDeleteOnLongClick() {
        binding.listViewTasks.setOnItemLongClickListener { _, _, position, _ ->
            // Get the task name before removing it (for the toast message)
            val removedTask = taskList[position]

            // Remove the task from the in-memory list
            taskList.removeAt(position)

            // Refresh the ListView to reflect the deletion
            taskAdapter.notifyDataSetChanged()

            // Show confirmation toast
            Toast.makeText(
                this,
                getString(R.string.task_deleted, removedTask),
                Toast.LENGTH_SHORT
            ).show()

            // Returning true marks the long click as handled/consumed
            true
        }
    }
}
