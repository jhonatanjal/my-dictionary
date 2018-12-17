package com.jhonatanlopes.mydictionary.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.repository.WordDataRepository

class WordDataViewModel : ViewModel() {
    private val repository = WordDataRepository

    fun search(word: String): LiveData<WordData> {
        return repository.search(word)
    }
}