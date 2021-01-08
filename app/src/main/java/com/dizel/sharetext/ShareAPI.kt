package com.dizel.sharetext

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShareAPI {
    @GET("api")
    fun notesAdd(
            @Query("method") method: String,
            @Query("v") v: Int,
            @Query("secret") secret: String,
            @Query("quote") quote: String,
            @Query("text") text: String
    ): Call<ResponseBody>
}