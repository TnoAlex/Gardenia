package com.alex.gardenia.utils

import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry


fun showRingPieChart(
    chart: PieChart,
    color: List<String>,
    ringSize: Float,
    holeColor: String,
    legend: Boolean
) {
//设置每份所占数量
    val list: MutableList<PieEntry> = ArrayList()
    list.add(PieEntry(3.0f, "已完成"))
    list.add(PieEntry(7.0f, "未完成"))

    // 设置每份的颜色
    val colors: MutableList<Int> = ArrayList()
//    colors.add(Color.parseColor("#729FFE"))
//    colors.add(Color.parseColor("#F5F4F4"))
    for (i in color) {
        colors.add(Color.parseColor(i))
    }
    val pieChartManager = PieChartManager(chart)
    pieChartManager.showRingPieChart(list, colors, ringSize, holeColor, legend)
}