package com.social.pitchtask.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.social.pitchtask.R
import com.social.pitchtask.data.models.task
import com.social.pitchtask.databinding.ActivityTasksBinding
import com.social.pitchtask.presentation.adapters.TaskAdapter

class TasksActivity : AppCompatActivity(), TaskAdapter.CheckBoxListener {

    private lateinit var binding: ActivityTasksBinding
    lateinit var adapter: TaskAdapter
    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTasksBinding.inflate(layoutInflater).also { binding = it }.root)

        window.statusBarColor = resources.getColor(R.color.base)
        binding.progressBar.animate()

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("tasks")

        val taskList = mutableListOf<task>()

        adapter = TaskAdapter(taskList, this)
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear()
                binding.progressBar.visibility = View.GONE

                if(!snapshot.exists()){
                    binding.tvStatus.visibility = View.VISIBLE
                } else{
                    binding.tvStatus.visibility = View.GONE
                    snapshot.children.forEach { taskSnapshot ->
                        val task = taskSnapshot.getValue(task::class.java)
                        if (task != null) {
                            taskList.add(task)
                        }
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fire", "failed to retrieve")
            }

        })

        binding.btnAdd.setOnClickListener {
            Intent(this@TasksActivity, AddTaskActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCheckBoxChecked(key: String) {

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            reference.child(key).removeValue().addOnSuccessListener {
                Toast.makeText(this@TasksActivity, "Item removed successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                    error ->
                Log.d("fire", error.message.toString())
            }
        }, 1000)
    }
}