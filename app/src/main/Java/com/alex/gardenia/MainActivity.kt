package com.alex.gardenia

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.gardenia.activity.AnalyticsActivity
import com.alex.gardenia.activity.PatientInfoActivity
import com.alex.gardenia.adapter.CalendarListAdapter
import com.alex.gardenia.component.CalendarListDecoration
import com.alex.gardenia.utils.showRingPieChart
import com.alex.gardenia.databinding.MainActivityBinding
import com.alex.gardenia.databinding.PatientCalendarListLitemBinding
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: MainActivityBinding

    private lateinit var calendarItemBinding: PatientCalendarListLitemBinding


    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLog()
        setContentView(R.layout.main_activity)
        viewBinding = MainActivityBinding.inflate(layoutInflater)
        calendarItemBinding = PatientCalendarListLitemBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.mainDrawer.addDrawerListener(
            ActionBarDrawerToggle(
                this,
                viewBinding.mainDrawer,
                R.string.bar_open,
                R.string.bar_close
            )
        )
        viewBinding.patientMainLeftBarButton.setOnClickListener {
            viewBinding.mainDrawer.openDrawer(Gravity.LEFT)
        }
        initNavigationBar()
        viewBinding.patientMainGreet.text = "早上好"
        viewBinding.patientMainUserName.text = "阿庆"
        val ico = BitmapFactory.decodeResource(resources, R.drawable.test)
        viewBinding.patientMainUserIco.setImageBitmap(ico)
        viewBinding.patientMainTips.text =
            resources.getString(R.string.single_character).format("最后一次组合治疗总共持续了25分钟")
        addCalendarItem()
        showRingPieChart(
            viewBinding.patientMainPie,
            listOf("#729FFE", "#F5F4F4"),
            60f,
            "#FFFFFFFF",
            true
        )
    }

    private fun addCalendarItem() {
        val list = ArrayList<Pair<String, String>>()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GTM+8"))
        calendar.add(Calendar.DATE, -1)
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val now = calendar.get(Calendar.DAY_OF_MONTH)
        val week = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        for (i in now..lastDay) {
            var index = calendar.get(Calendar.DAY_OF_WEEK) - 1
            if (index < 0)
                index = 0
            list.add(Pair(week[index], i.toString()))
            calendar.add(Calendar.DATE, 1)
        }
        val recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        viewBinding.patientCalendarList.addItemDecoration(CalendarListDecoration())
        viewBinding.patientCalendarList.layoutManager = recyclerViewLayoutManager
        viewBinding.patientCalendarList.adapter = CalendarListAdapter(this, list)

    }

    @SuppressLint("RtlHardcoded")
    fun initNavigationBar() {
        val navigationBar = viewBinding.navigationBar
        val navigationBarHeader = navigationBar.getHeaderView(0)
        navigationBarHeader.findViewById<ImageView>(R.id.navigation_header_ico)
            .setImageResource(R.drawable.test)
        navigationBarHeader.findViewById<TextView>(R.id.navigation_header_user_name).text = "阿庆"
        navigationBar.menu.findItem(R.id.navigation_menu_main).isVisible = false
        navigationBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu_user_center -> {
                    val intent = Intent(this, PatientInfoActivity::class.java)
                    startActivity(intent)
                    it.isChecked = false
                    viewBinding.mainDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
                R.id.navigation_menu_analytics -> {
                    val intent = Intent(this, AnalyticsActivity::class.java)
                    startActivity(intent)
                    it.isChecked = false
                    viewBinding.mainDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
                R.id.navigation_menu_exit -> {
                    it.isChecked = false
                    viewBinding.mainDrawer.closeDrawer(Gravity.LEFT)
                    finishAndRemoveTask()
                    return@setNavigationItemSelectedListener false
                }
                else -> {
                    it.isChecked = false
                    viewBinding.mainDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun initLog() {
        val config = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)
            .enableThreadInfo()
            .enableStackTrace(3)
            .build()
        XLog.init(config)
    }

}