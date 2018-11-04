package com.jhonatanlopes.mydictionary.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhonatanlopes.mydictionary.R
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.ui.recyclerview.DefinitionsAdapter
import kotlinx.android.synthetic.main.fragment_word_info.*

class WordInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            fillsWordInfo(it.getParcelable("word-data"))
        }
    }

    private fun fillsWordInfo(wordData: WordData) {
        word_info_word_id.text = wordData.wordId
        word_info_pronunciation.text = wordData.pronunciation
        word_info_definitions.adapter = DefinitionsAdapter(context, wordData.definitions)
        word_info_exemple.text = wordData.exemple
    }
}
