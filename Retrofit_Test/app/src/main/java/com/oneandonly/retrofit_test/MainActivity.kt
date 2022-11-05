package com.oneandonly.retrofit_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.oneandonly.retrofit_test.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.postbutton.setOnClickListener {
            val data = PostModel(binding.idedt.text.toString(),binding.pwdedt.text.toString())

            val params = HashMap<String, String>()
            params["id"] = binding.idedt.text.toString()
            params["password"] = binding.pwdedt.text.toString()

            with(APIS) {
                create().post_users(params).enqueue(object : Callback<PostResult> {
                        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                            Log.d("log",response.toString())
                            Log.d("log",response.body().toString())
                            if(response.body().toString().isNotEmpty())
                                binding.text.text= (response.body().toString());
                        }

                        override fun onFailure(call: Call<PostResult>, t: Throwable) {
                            // 실패
                            Log.d("log",t.message.toString())
                            Log.d("log","fail")
                        }
                    })
            }
        }

    }

}