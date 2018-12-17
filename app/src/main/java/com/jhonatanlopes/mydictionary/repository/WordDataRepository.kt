package com.jhonatanlopes.mydictionary.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.service.OxfordDictionaryService
import com.jhonatanlopes.mydictionary.util.OxfordDictionaryJsonUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

object WordDataRepository {

    private val service: OxfordDictionaryService
    private val wordData = MutableLiveData<WordData>()

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://od-api.oxforddictionaries.com/api/v1/entries/")
                .build()
        service = retrofit.create(OxfordDictionaryService::class.java)
    }

    fun search(word: String): LiveData<WordData> {

        val call = service.definition(word)
        call.enqueue(object: Callback<ResponseBody?> {
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.d("- OxfReq", "fail to find word")
            }

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                val json = response.body()?.string()
                Log.i("- OxfReq", json)
                val wordDataFromJson = OxfordDictionaryJsonUtils()
                        .getWordDataFromJson(json)
                wordData.postValue(wordDataFromJson)
            }
        })

        return wordData
    }
}