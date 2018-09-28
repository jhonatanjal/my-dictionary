package com.jhonatanlopes.mydictionary.util

import com.jhonatanlopes.mydictionary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NetworkUtils() {
    private val baseUrl: String = "https://od-api.oxforddictionaries.com/api/v1/entries/"
    private val language = "en"

    fun getWordDefinition(word: String): String? {
        return runHttpRequest(buildUrl(word))
    }

    private fun buildUrl(word: String): String =
            baseUrl + "$language/${word.toLowerCase()}/regions=us"

    private fun runHttpRequest(url: String): String? {
        val client = OkHttpClient()

        val request = Request.Builder()
                .url(url)
                .addHeader("app_id", BuildConfig.OdAppId)
                .addHeader("app_key", BuildConfig.OdAppKey)
                .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.code() == 200) response.body()?.string() else null
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}
