package com.social.pitchtask.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.social.pitchtask.data.models.task
import com.social.pitchtask.databinding.TaskItemBinding

class TaskAdapter(
    val list : List<task>, private val listener: CheckBoxListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: task) {
            binding.tvTitle.text = task.title
            binding.tvBody.text = task.body
            binding.cbDelete.isChecked = task.isChecked

            binding.cbDelete.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    listener.onCheckBoxChecked(task.key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface CheckBoxListener {
        fun onCheckBoxChecked(key : String)
    }

}