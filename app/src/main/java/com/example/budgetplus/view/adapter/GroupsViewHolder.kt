package com.example.budgetplus.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.example.budgetplus.R
import com.example.budgetplus.extensions.hide
import com.example.budgetplus.extensions.show
import com.example.budgetplus.model.response.GroupDetailsResponseModelItem
import kotlinx.android.synthetic.main.groups_item.view.*


class GroupsViewHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) :
            this(LayoutInflater.from(parent.context).inflate(R.layout.groups_item, parent, false))

    fun bind(groupInfo: GroupDetailsResponseModelItem) {


        if (groupInfo != null &&
            groupInfo.expenseGroup != null &&
            groupInfo.expenseGroup.groupedExpenses != null &&
            groupInfo.expenseGroup.groupedExpenses.size > 1
        ) {

            val anyChart = itemView.AAChartView as AnyChartView
            itemView.CLGroupInfoTitle.show()
            itemView.CLIfNoExpense.hide()

            val pie = AnyChart.pie()

            val data: MutableList<DataEntry> = ArrayList()
            for (groupedExpens in groupInfo.expenseGroup.groupedExpenses) {
                data.add(ValueDataEntry(groupedExpens.category, groupedExpens.categoryTotal))
            }
            pie.data(data)

            groupInfo.name?.let {

                pie.title(it)
                pie.legend().title().enabled(true)
                pie.title()
                    .fontSize("35")
                    .fontColor("#4a154b")

                groupInfo.description?.let{description->
                    pie.legend().title()
                        .text(description)
                        .fontStyle("italic")
                        .padding(0.0, 0.0, 10.0, 0.0)
                }
                pie.labels().position("outside")
                pie.legend()
                    .position("center-bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL)
                    .align(Align.CENTER)

                anyChart.setChart(pie)
            }
        } else {
            itemView.CLGroupInfoTitle.hide()
            itemView.CLIfNoExpense.show()
            groupInfo.name?.let {
                itemView.TWGroupTitle.text = it
            }
        }
    }
}