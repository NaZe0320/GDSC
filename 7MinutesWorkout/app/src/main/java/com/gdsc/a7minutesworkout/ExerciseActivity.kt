package com.gdsc.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc.a7minutesworkout.databinding.ActivityExerciseBinding
import com.gdsc.a7minutesworkout.R
import com.gdsc.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var exerciseBinding: ActivityExerciseBinding? = null
    private val binding get() = exerciseBinding!!

    private var restTimer: CountDownTimer?= null
    private var restProgress = 0
    private var restTimerDuration: Long = 1
    private var exerciseTimer: CountDownTimer?= null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 1
    private lateinit var exerciseList : ArrayList<ExerciseModel>
    private var currentExercisePosition = -1

    private lateinit var tts: TextToSpeech
    private lateinit var player: MediaPlayer

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseBinding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun setupRestView() {

        try {
            val soundURI = Uri.parse("android.resource://com.gdsc.a7minutesworkout/"+ R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player.isLooping = false
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.tvUpComingLabel.visibility = View.VISIBLE
        binding.tvUpComingExerciseName.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding.tvUpComingExerciseName.text = exerciseList[currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration*1000,1000) {
            override fun onTick(p0: Long) {
                restProgress ++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++

                exerciseList[currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExerciseName.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.tvUpComingLabel.visibility = View.INVISIBLE
        binding.tvUpComingExerciseName.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        exerciseTimer?.cancel()
        exerciseProgress = 0

        speakOut(exerciseList[currentExercisePosition].getName())

        binding.ivImage.setImageResource(exerciseList[currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList[currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000,1000) {
            override fun onTick(p0: Long) {
                exerciseProgress ++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }
            override fun onFinish() {
                exerciseList[currentExercisePosition].setIsSelected(false)
                exerciseList[currentExercisePosition].setIsCompleted(true)
                exerciseAdapter?.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].setIsSelected(false)
                    exerciseList[currentExercisePosition].setIsCompleted(true)
                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
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
/*        if (tts != null ){
            tts?.stop()
            tts?.shutdown()
        }*/
        tts.stop()
        tts.shutdown()

/*        if (player != null) {
            player!!.stop()
        }*/
        player.stop()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS","The Language specified is not supported!")
        } else {
            Log.e("TTS","Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        Log.d("BackPressed" ,"Pressed")
    }
}