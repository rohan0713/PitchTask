package com.social.pitchtask.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.social.pitchtask.R
import com.social.pitchtask.data.models.task
import com.social.pitchtask.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAddTaskBinding.inflate(layoutInflater).also { binding = it }.root)

        window.statusBarColor = resources.getColor(R.color.base)

        binding.ivBack.setOnClickListener {
            finish()
        }

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("tasks")

        binding.btnAdd.setOnClickListener {

            val title = binding.etTask.text
            val body = binding.etBody.text

            if(!title.isNullOrEmpty() && !body.isNullOrEmpty()){

                val TITLE = title.toString().replace(" ", "")
                val BODY = body.toString().replace(" ", "")

                if (!TITLE.isNullOrEmpty() && !BODY.isNullOrEmpty()){

                    val key = ref.push().key
                    key?.let {
                        ref.child(it).setValue(task(key.toString(), TITLE, body.toString(), false))
                        Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else{
                    Toast.makeText(this, "Some Details missing", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Some Details missing", Toast.LENGTH_SHORT).show()
            }
        }
    }
}