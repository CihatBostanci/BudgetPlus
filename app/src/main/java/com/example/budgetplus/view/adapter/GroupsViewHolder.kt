package com.example.budgetplus.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetplus.R
import com.example.budgetplus.model.response.GroupDetailsResponseModelItem
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import kotlinx.android.synthetic.main.groups_item.view.*

class GroupsViewHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup) :
            this(LayoutInflater.from(parent.context).inflate(R.layout.groups_item, parent, false))

    fun bind(groupInfo: GroupDetailsResponseModelItem) {
        val chartView = itemView.AAChartView

        groupInfo.name?.let { name->
            groupInfo.description?.let{description->
                    val aaChartModel : AAChartModel = AAChartModel()
                        .chartType(AAChartType.Pie)
                        .title(name)
                        .subtitle(description)
                        .backgroundColor("#ffffff")
                        .dataLabelsEnabled(true)
                        .series(arrayOf(
                            AASeriesElement()
                                    .name("category")
                                    .data(arrayOf(7.0, 6.9, 9.5, 14.5))
                        )
                        )
            //The chart view object calls the instance object of AAChartModel and draws the final graphic
            chartView.aa_drawChartWithChartModel(aaChartModel)
        }
        }

    }
}