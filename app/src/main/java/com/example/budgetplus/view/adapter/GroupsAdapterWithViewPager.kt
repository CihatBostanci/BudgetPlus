package com.example.budgetplus.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GroupsAdapterWithViewPager : RecyclerView.Adapter<GroupsViewHolder>() {

    var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(list[position])
    }
    fun setItem(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

}