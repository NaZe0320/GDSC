package com.gdsc.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.gdsc.a7minutesworkout.database.HistoryDao
import com.gdsc.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var _binding: ActivityHistoryBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        if (supportActionBar != null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)

    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect() {
                for (i in it) {
                    Log.d("HistoryTest", "Date : $i")

                }
            }
        }
    }


}