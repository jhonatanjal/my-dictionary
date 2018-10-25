package com.jhonatanlopes.mydictionary.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jhonatanlopes.mydictionary.R
import com.jhonatanlopes.mydictionary.model.WordData
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.fragment_word_info.*


class WordInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        fillsWordInfo(arguments?.getParcelable("word-data"))
    }

    private fun fillsWordInfo(wordData: WordData?) {
        if (wordData != null) {
            word_info_word_id.text = wordData.wordId
            word_info_pronunciation.text = wordData.pronunciation
            word_info_definition.text = wordData.definition
            word_info_exemple.text = wordData.exemple
        } else {
            showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        activity?.fragment_container?.visibility = View.INVISIBLE
        val errorMessage = resources.getString(R.string.error_message)
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}
