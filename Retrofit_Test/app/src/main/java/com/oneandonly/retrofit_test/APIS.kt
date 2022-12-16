package com.oneandonly.retrofit_test

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
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


    @GET("user/information")
    fun getRequest(@Header("Token-key") token: String?): Call<Any>

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
    try {
        JSONArray jsonArray = new JSONArray();
        for (Cart cart : cartList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", cart.getProduct_id());
            jsonObject.put("name", cart.getName());
            jsonObject.put("price", cart.getPrice());
            jsonObject.put("quantity", cart.getQuantity());
            jsonObject.put("totalprice", cart.getTotalprice());
            jsonObject.put("user_id", cart.getUser_id());
            jsonArray.put(jsonObject);
        }
        Log.e("JSONArray", String.valueOf(jsonArray));
    } catch (JSONException jse) {
        jse.printStackTrace();
    }

}


