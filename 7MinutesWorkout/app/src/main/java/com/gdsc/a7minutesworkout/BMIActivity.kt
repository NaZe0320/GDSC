package com.gdsc.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gdsc.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var _binding: ActivityBmiBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        if (supportActionBar != null ){

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnCalculate.setOnClickListener {
            if (checkValid()) {
                setBmiTextView(calculationBMI())
            }
        }
    }

    private fun checkValid(): Boolean  {
        return binding.etMetricUnitHeight.text.toString().isNotEmpty() &&
                binding.etMetricUnitWeight.text.toString().isNotEmpty()
    }

    private fun calculationBMI() : Float {
        val height: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
        val weight: Float = binding.etMetricUnitWeight.text.toString().toFloat()

        return  weight / (height * height)
    }

    private fun setBmiTextView(bmi: Float) {
        binding.tvBMIValue.text = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        when (bmi) {
            in 0f..18.5f-> {
                setLabel(BMI.LOW)
            }
            in 18.5f..23f -> {
                setLabel(BMI.NORMAL)
            }
            in 23f..25f -> {
                setLabel(BMI.OVERWEIGHT)
            }
            in 25f..30f -> {
                setLabel(BMI.OBESITY)
            }
            else -> {
                setLabel(BMI.SEVERE_OBESITY)
            }
        }
    }

    private fun setLabel(bmi: BMI) {
        binding.tvBMIType.text = bmi.Label
        binding.tvBMIDescription.text = bmi.Description

        binding.llDisplayBMIResult.visibility = View.VISIBLE
    }
}