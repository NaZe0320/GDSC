package com.oneandonly.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oneandonly.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var exerciseBinding: ActivityExerciseBinding? = null
    private val binding get() = exerciseBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseBinding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}