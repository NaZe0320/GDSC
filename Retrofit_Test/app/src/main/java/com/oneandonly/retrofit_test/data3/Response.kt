package com.oneandonly.retrofit_test.data3


import com.google.gson.annotations.SerializedName

data class Response(
    var elements: List<Element?>?,
    var leastSell: Int?,
    var name: String?
)