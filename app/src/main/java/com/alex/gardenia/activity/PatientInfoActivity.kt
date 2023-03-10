package com.alex.gardenia.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.alex.gardenia.MainActivity
import com.alex.gardenia.R
import com.alex.gardenia.adapter.PatientHistoryAdapter
import com.alex.gardenia.databinding.ActivityPatientInfoBinding
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PatientInfoActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPatientInfoBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPatientInfoBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.patientInfoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewBinding.patientHeightValue.text =
            resources.getString(R.string.patient_height_value).format("173")
        viewBinding.patientWeightValue.text =
            resources.getString(R.string.patient_weight_value).format("63")
        viewBinding.patientSchemeValue.text =
            resources.getString(R.string.single_character).format("S1")
        viewBinding.patientAppendRecodeDate.setOnClickListener {
            showPickDate {
                val date = Date(it)
                val format = SimpleDateFormat("yy/MM/dd E")
                viewBinding.patientAppendRecodeDate.text =
                    resources.getString(R.string.single_character).format(format.format(date))
            }
        }
        viewBinding.patientAppendRecodeTime.setOnClickListener {
            showPickTime {
                val date = (System.currentTimeMillis() - it) / 6000
                viewBinding.patientAppendRecodeTime.text =
                    resources.getString(R.string.single_character).format(date)
            }
        }

        viewBinding.pathRecodeHistoryList.adapter =
            PatientHistoryAdapter(this, R.layout.patient_history_list_item, fakeDate())
    }

    private fun showPickDate(chooseCallBack: (value: Long) -> Unit) {
        CardDatePickerDialog.builder(this)
            .setTitle("选择补入日期")
            .setOnChoose { chooseCallBack(it) }.build().show()
    }

    private fun showPickTime(chooseCallBack: (value: Long) -> Unit) {
        CardDatePickerDialog.builder(this)
            .setTitle("选择补入时长")
            .setDisplayType(DateTimeConfig.MIN)
            .setOnChoose { chooseCallBack(it) }.build().show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun fakeDate(): ArrayList<Pair<String, String>> {
        val list = ArrayList<Pair<String, String>>()
        var value = 1667972724000
        var date = Date(1667972724000)
        val format = SimpleDateFormat("yy/MM/dd")
        for (i in 0..10) {
            list.add(Pair(format.format(date), "S1 25 Min"))
            value -= 86400000
            date = Date(value)
        }
        return list
    }
}