package com.example.budgetplus.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetplus.R
import kotlinx.android.synthetic.main.groups_item.view.*

class GroupsViewHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(LayoutInflater.from(parent.context).inflate(R.layout.groups_item, parent, false))
    fun bind(test: String) {
        itemView.TWTestName.text = test
    }
}