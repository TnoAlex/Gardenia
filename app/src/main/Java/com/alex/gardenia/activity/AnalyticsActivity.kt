package com.alex.gardenia.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alex.gardenia.MainActivity
import com.alex.gardenia.R
import com.alex.gardenia.databinding.ActivityAnalyticsBinding
import com.alex.gardenia.utils.showRingPieChart


class AnalyticsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAnalyticsBinding

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAnalyticsBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        viewBinding.analyticsDrawer.addDrawerListener(
            ActionBarDrawerToggle(
                this,
                viewBinding.analyticsDrawer,
                R.string.bar_open,
                R.string.bar_close
            )
        )
        viewBinding.analyticsLeftBarButton.setOnClickListener {
            viewBinding.analyticsDrawer.openDrawer(Gravity.LEFT)
        }
        initNavigationBar()
        val ico = BitmapFactory.decodeResource(resources, R.drawable.test)
        viewBinding.analyticsUserIco.setImageBitmap(ico)
        showRingPieChart(
            viewBinding.rehabilitationPie,
            listOf("#FFFFFFFF", "#C3B1FD"),
            80f,
            "#8F6FF6",
            false
        )
    }

    @SuppressLint("RtlHardcoded")
    fun initNavigationBar() {
        val navigationBar = viewBinding.navigationBar
        val navigationBarHeader = navigationBar.getHeaderView(0)
        navigationBarHeader.findViewById<ImageView>(R.id.navigation_header_ico)
            .setImageResource(R.drawable.test)
        navigationBarHeader.findViewById<TextView>(R.id.navigation_header_user_name).text = "阿庆"
        navigationBar.menu.findItem(R.id.navigation_menu_analytics).isVisible = false
        navigationBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu_user_center -> {
                    val intent = Intent(this, PatientInfoActivity::class.java)
                    startActivity(intent)
                    it.isChecked = false
                    viewBinding.analyticsDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
                R.id.navigation_menu_main -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    it.isChecked = false
                    viewBinding.analyticsDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
                R.id.navigation_menu_exit -> {
                    it.isChecked = false
                    viewBinding.analyticsDrawer.closeDrawer(Gravity.LEFT)
                    finishAndRemoveTask()
                    return@setNavigationItemSelectedListener false
                }
                else -> {
                    it.isChecked = false
                    viewBinding.analyticsDrawer.closeDrawer(Gravity.LEFT)
                    return@setNavigationItemSelectedListener false
                }
            }
        }
    }
}