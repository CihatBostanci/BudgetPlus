package com.example.budgetplus.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetplus.R
import com.example.budgetplus.extensions.invisible
import com.example.budgetplus.extensions.show
import com.example.budgetplus.model.StateFriendSpinnerModel


class FriendCheckListAdapter(context: Context, resource: Int, objects: List<StateFriendSpinnerModel>) :
    ArrayAdapter<StateFriendSpinnerModel?>(context, resource, objects) {

    private val mContext: Context = context
    private val listState: ArrayList<StateFriendSpinnerModel> = objects as ArrayList<StateFriendSpinnerModel>
    private val myAdapter: FriendCheckListAdapter = this
    private var isFromView = false

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView: View? = convertView
        var holder: ViewHolder
        if (convertView == null) {
            val layoutInflator = LayoutInflater.from(mContext)
            convertView = layoutInflator.inflate(R.layout.friend_check_list_item, null)
            holder = ViewHolder()
            holder.mTextView = convertView?.findViewById(R.id.TWItemFriendName)
            holder.mCheckBox = convertView?.findViewById(R.id.CBItemFriendCheckBox)
            convertView?.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.mTextView?.text = listState[position].title

        // To check weather checked event fire from getview() or user input
        isFromView = true
        holder.mCheckBox!!.isChecked = listState[position].isSelected
        isFromView = false
        if (position == 0) {
            holder.mCheckBox!!.invisible()
        } else {
            holder.mCheckBox!!.show()
        }
        holder.mCheckBox!!.tag = position
        holder.mCheckBox!!.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                listState[position].isSelected = isChecked
            }
        }
        return convertView!!
    }

    private inner class ViewHolder {
        var mTextView: TextView? = null
        var mCheckBox: CheckBox? = null
    }

}