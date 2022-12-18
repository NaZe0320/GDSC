package com.gdsc.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.gdsc.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var _binding: ActivityBmiBinding ?= null
    private val binding get() = _binding!!

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        if (supportActionBar != null ){

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        setVisibleView(true)

        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnCalculate.setOnClickListener {

            when (currentVisibleView) {
                METRIC_UNITS_VIEW -> {
                    if (checkValidMetric()) {
                        setBmiTextView(calculationMetricBMI())
                    } else {
                        makeToast("빈칸을 채워주세요")
                    }
                }
                US_UNITS_VIEW -> {
                    if (checkValidUS()) {
                        setBmiTextView(calculationUSBMI())
                    } else {
                        makeToast("빈칸을 채워주세요")
                    }
                }
            }

        }

        binding.rgUnits.setOnCheckedChangeListener { radioGroup, id ->
            when(id) {
                R.id.rbMetricUnits -> {
                    setVisibleView(true)
                    currentVisibleView = METRIC_UNITS_VIEW
                }
                R.id.rbUsUnits -> {
                    setVisibleView(false)
                    currentVisibleView = US_UNITS_VIEW
                }
            }
        }
    }

    private fun setVisibleView(visible: Boolean) { //True : Metric / False : US
        binding.rlMetricView.isVisible = visible
        binding.rlUsView.isInvisible = visible
    } //Fragment 로 변경하면 좋을듯?

    private fun checkValidMetric(): Boolean  {
        return binding.etMetricUnitHeight.text.toString().isNotEmpty() &&
                binding.etMetricUnitWeight.text.toString().isNotEmpty()
    }

    private fun checkValidUS(): Boolean  {
        return binding.etUsMetricUnitHeightFeet.text.toString().isNotEmpty() &&
                binding.etUsMetricUnitWeight.text.toString().isNotEmpty() &&
                binding.etUsMetricUnitHeightInch.text.toString().isNotEmpty()
    }

    private fun calculationMetricBMI() : Float {
        val height: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
        val weight: Float = binding.etMetricUnitWeight.text.toString().toFloat()

        return  weight / (height * height)
    }

    private fun calculationUSBMI() : Float {
        val heightFeet: Float = binding.etUsMetricUnitHeightFeet.text.toString().toFloat()
        val heightInch: Float = binding.etUsMetricUnitHeightInch.text.toString().toFloat()
        val weight: Float = binding.etUsMetricUnitWeight.text.toString().toFloat()

        val height = heightInch + heightFeet * 12
        return 703 * (weight / (height * height))
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