package com.example.budgetplus.groups.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetplus.model.response.GroupDetailsResponseModel


class GroupsAdapterWithViewPager: RecyclerView.Adapter<GroupsViewHolder>() {

    var list: GroupDetailsResponseModel = GroupDetailsResponseModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(
            list[position]
        )
    }
    fun setItem(list: GroupDetailsResponseModel) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size


}