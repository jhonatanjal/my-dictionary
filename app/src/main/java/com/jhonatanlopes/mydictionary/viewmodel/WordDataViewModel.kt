package com.jhonatanlopes.mydictionary.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import android.util.Log
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

    fun fetchWordData(word: String) {
        RequestWordDataTask(wordData).execute(word)
        //wordData.value = WordData("Teste", "Teste", "Testes", "Tsstes")
    }

    class RequestWordDataTask(private val liveData: MutableLiveData<WordData>) : AsyncTask<String, Void, WordData>() {
        override fun doInBackground(vararg args: String): WordData? {
            val wordDataJson = NetworkUtils().getWordDefinition(args[0])

            return if (wordDataJson != null)
                OxfordDictionaryJsonUtils().getWordDataFromJson(wordDataJson)
            else
                null
        }

        override fun onPostExecute(result: WordData?) {
            super.onPostExecute(result)
            Log.d("onPostExecute", "msdd-terminei")
            liveData.value = result
        }
    }
}