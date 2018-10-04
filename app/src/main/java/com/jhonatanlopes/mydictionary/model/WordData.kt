package com.jhonatanlopes.mydictionary.model

import java.io.Serializable

class WordData(
        val wordId: String,
        val exemple: String,
        val pronunciation: String,
        val definition: String) : Serializable {

    override fun toString(): String = "$wordId /$pronunciation/\n$definition\n$exemple"
}