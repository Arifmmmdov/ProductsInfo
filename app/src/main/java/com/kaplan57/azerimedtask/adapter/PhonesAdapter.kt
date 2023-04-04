package com.kaplan57.azerimedtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaplan57.azerimedtask.databinding.RecyclerItemBinding
import com.kaplan57.azerimedtask.local_db.entity.PhonesEntity
import kotlin.coroutines.coroutineContext

class PhonesAdapter(private var list:List<PhonesEntity>, val mContext: Context) : RecyclerView.Adapter<PhonesAdapter.PhonesViewHolder>() {

    inner class PhonesViewHolder(val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesViewHolder =
        PhonesViewHolder(RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: PhonesViewHolder, position: Int) {
        holder.binding.apply {
            txtName.text = list[position].phonesName
            txtDescription.text = list[position].phonesDescription

            Glide.with(mContext)
                .load(list[position].phonesImages)
                .into(imgItem)
        }
    }

    override fun getItemCount(): Int = list.size

    fun notifyItemChanged(list: List<PhonesEntity>){
        this.list = list
        notifyDataSetChanged()
    }

}