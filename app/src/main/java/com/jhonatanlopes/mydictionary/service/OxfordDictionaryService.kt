package com.jhonatanlopes.mydictionary.service

import com.jhonatanlopes.mydictionary.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface OxfordDictionaryService {

    @Headers(
            "app_id: ${BuildConfig.OdAppId}",
            "app_key: ${BuildConfig.OdAppKey}"
    )
    @GET("en/{word}/regions=us")
    fun definition(@Path("word") word: String): Call<ResponseBody>
}