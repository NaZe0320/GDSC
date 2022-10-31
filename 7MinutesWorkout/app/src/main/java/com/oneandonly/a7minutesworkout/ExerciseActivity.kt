package com.oneandonly.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.oneandonly.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var exerciseBinding: ActivityExerciseBinding? = null
    private val binding get() = exerciseBinding!!

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseName = "Exercise Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseBinding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    private fun setupRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(10000,1000) {
            override fun onTick(p0: Long) {
                restProgress ++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        binding.flProgressBar.visibility = View.INVISIBLE
        binding.tvTitle.text = exerciseName

        binding.flExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(30000,1000) {
            override fun onTick(p0: Long) {
                exerciseProgress ++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Here now we end the exercise.",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun on(){

    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }
        exerciseBinding = null

        if (exerciseTimer != null ) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }
}