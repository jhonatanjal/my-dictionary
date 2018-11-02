package com.jhonatanlopes.mydictionary.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.util.NetworkUtils
import com.jhonatanlopes.mydictionary.util.OxfordDictionaryJsonUtils

class WordDataViewModel : ViewModel() {
    private lateinit var wordData: MutableLiveData<WordData>

    fun getWordData(): LiveData<WordData> {
        if (!::wordData.isInitialized) {
            wordData = MutableLiveData()
        }
        return wordData
    }

    fun fetchWordData(word: String) = RequestWordDataTask(wordData).execute(word)

    class RequestWordDataTask(private val liveData: MutableLiveData<WordData>) : AsyncTask<String, Void, WordData>() {
        override fun doInBackground(vararg args: String): WordData? {
            val wordDataJson = NetworkUtils().getWordDefinition(args[0])
            return OxfordDictionaryJsonUtils().getWordDataFromJson(wordDataJson)
        }

        override fun onPostExecute(result: WordData?) {
            super.onPostExecute(result)
            liveData.value = result
        }
    }
}