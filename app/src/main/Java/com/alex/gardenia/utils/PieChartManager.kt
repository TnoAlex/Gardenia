package com.alex.gardenia.utils

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter


class PieChartManager(private val chart: PieChart) {

    init {
        initPieChart()
    }

    private fun initPieChart() {
        //  是否显示中间的洞
        chart.isDrawHoleEnabled = false
        chart.holeRadius = 40f //设置中间洞的大小
        // 半透明圈
        chart.transparentCircleRadius = 30f
        chart.setTransparentCircleColor(Color.WHITE) //设置半透明圆圈的颜色
        chart.setTransparentCircleAlpha(125) //设置半透明圆圈的透明度

        chart.centerTextRadiusPercent = 1f
        chart.setCenterTextTypeface(Typeface.DEFAULT) //中间文字的样式
        chart.setCenterTextOffset(0F, 0F) //中间文字的偏移量
        chart.rotationAngle = 90F // 初始旋转角度
        chart.isRotationEnabled = false// 可以手动旋转
        chart.setUsePercentValues(true) //显示成百分比
        chart.description.isEnabled = false //取消右下角描述


        chart.setDrawEntryLabels(false)

        chart.setExtraOffsets(0f, 0f, 0f, 0f)
        //图标的背景色
        chart.setBackgroundColor(Color.TRANSPARENT)
        //        设置chart图表转动阻力摩擦系数[0,1]
        chart.dragDecelerationFrictionCoef = 0.75f
    }

    fun showRingPieChart(
        list: List<PieEntry>,
        colors: List<Int>,
        ringSize: Float,
        holeColor: String,
        legend: Boolean
    ) {
        //显示为圆环
        chart.legend.isEnabled = legend
        chart.isDrawHoleEnabled = true
        chart.holeRadius = ringSize //设置中间洞的大小
        chart.centerText = "30%"
        chart.setCenterTextColor(Color.WHITE)
        chart.setHoleColor(Color.parseColor(holeColor))
        //数据集合
        val dataset = PieDataSet(list, "")
        //填充每个区域的颜色
        dataset.colors = colors
        //是否在图上显示数值
        dataset.setDrawValues(false)


        dataset.valueLinePart1Length = 0.4f
        //      当值位置为外边线时，表示线的后半段长度。
        dataset.valueLinePart2Length = 0.4f
        //      当ValuePosits为OutsiDice时，指示偏移为切片大小的百分比
        dataset.valueLinePart1OffsetPercentage = 80f
        // 当值位置为外边线时，表示线的颜色。
        dataset.valueLineColor = Color.parseColor("#a1a1a1")
        //        设置Y值的位置是在圆内还是圆外
        dataset.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        //        设置Y轴描述线和填充区域的颜色一致
        dataset.isUsingSliceColorAsValueLineColor = false
        //        设置每条之前的间隙
        dataset.sliceSpace = 0f

        //设置饼状Item被选中时变化的距离
        dataset.selectionShift = 5f
        //填充数据
        val pieData = PieData(dataset)
        //        格式化显示的数据为%百分比
        pieData.setValueFormatter(PercentFormatter())
        //        显示试图
        chart.data = pieData
    }
}