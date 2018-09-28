package com.jhonatanlopes.mydictionary.model

class WordData(
        val wordId: String,
        val exemple: String,
        val pronunciation: String,
        val definition: String) {

    override fun toString(): String = "$wordId /$pronunciation/\n$definition\n$exemple"
}