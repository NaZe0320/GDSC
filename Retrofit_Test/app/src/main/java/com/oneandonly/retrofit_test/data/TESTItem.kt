package com.oneandonly.retrofit_test.data


import com.google.gson.annotations.SerializedName

data class TESTItem(
    var batters: Batters?,
    var id: String?,
    var name: String?,
    var ppu: Double?,
    var topping: List<Topping?>?,
    var type: String?
)