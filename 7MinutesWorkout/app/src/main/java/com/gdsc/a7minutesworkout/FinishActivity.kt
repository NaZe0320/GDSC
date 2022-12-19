package com.gdsc.a7minutesworkout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gdsc.a7minutesworkout.database.HistoryDao
import com.gdsc.a7minutesworkout.database.HistoryEntity
import com.gdsc.a7minutesworkout.WorkOutApp
import com.gdsc.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity: AppCompatActivity() {

    private var _binding: ActivityFinishBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyDao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)

        setSupportActionBar(binding.toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.d("DatabaseTest","$date")
        }
    }
}