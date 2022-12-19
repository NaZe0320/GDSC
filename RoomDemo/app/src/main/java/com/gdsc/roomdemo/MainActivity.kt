package com.gdsc.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.gdsc.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeDao = (application as EmployeeApp).db.employeeDao()

        binding.btnRecord.setOnClickListener {
            addRecord(employeeDao = employeeDao)
        }

    }

    fun addRecord(employeeDao: EmployeeDao) {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()) {
            lifecycleScope.launch {
                employeeDao.insert(employeeEntity = EmployeeEntity(name = name, email = email))
                Toast.makeText(applicationContext,"Record saved", Toast.LENGTH_SHORT).show()
                binding.etName.text.clear()
                binding.etEmail.text.clear()
            }
        } else {
            Toast.makeText(applicationContext,"Name or Email cannot be blank", Toast.LENGTH_SHORT).show()
        }
    }
}