package com.test.fragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.test.fragmenttest.databinding.ActivityMainBinding
import com.test.fragmenttest.fragment.FragmentParent

class MainActivity : AppCompatActivity() {

    private var mainBinding: ActivityMainBinding? = null
    private val binding get() = mainBinding!!

    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenFragment.setOnClickListener {
            binding.fragmentFrame.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, FragmentParent())
                .commit()
        }
    }

    override fun onBackPressed() {

        if(false) {
            Log.d("BackPress", "MainActivity")
            if (System.currentTimeMillis() - waitTime >= 2000) {
                waitTime = System.currentTimeMillis()
                Toast.makeText(this,"뒤로가기 버튼 한번 더", Toast.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        } else {
            Log.d("BackPress","프래그먼트 살아 있음")
        }



    }
}