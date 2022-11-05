package com.oneandonly.retrofit_test

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIS {

    @POST("/user/login")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun post_user(
        @Body json_params: PostModel
    ): Call<PostResult>

    @FormUrlEncoded
    @POST("/user/login")
    fun post_users(@FieldMap param: HashMap<String,String>): Call<PostResult>

    companion object {
        private const val BASE_URL = "https://api.yukaigames.com/" // 주소

        fun create(): APIS {

            val gson : Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}