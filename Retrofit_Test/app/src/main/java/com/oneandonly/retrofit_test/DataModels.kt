package com.oneandonly.retrofit_test

import android.media.session.MediaSession
import retrofit2.Response

data class PostModel(
    var id: String?,
    var pw: String?
)

data class PostResult(
    var path: String?,
    var result: String?,
    var message: String?,
    var response: Any?,
    var token: String?,
    var id: String?,
    var pw: String?
)