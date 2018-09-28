package com.jhonatanlopes.mydictionary.util

import com.jhonatanlopes.mydictionary.model.WordData
import org.json.JSONObject

class OxfordDictionaryJsonUtils {

    fun getWordDataFromJson(json: String): WordData {
        val jsonObject = JSONObject(json)

        val wordId = jsonObject.getJSONArray("results").getJSONObject(0)
                .getString("id")

        val lexicalEntries = jsonObject.getJSONArray("results")
                .getJSONObject(0).getJSONArray("lexicalEntries")

        val senses = lexicalEntries.getJSONObject(0)
                .getJSONArray("entries").getJSONObject(0)
                .getJSONArray("senses")

        val example = senses.getJSONObject(0).getJSONArray("examples")
                .getJSONObject(0).getString("text")

        val pronunciation = lexicalEntries.getJSONObject(0)
                .getJSONArray("pronunciations").getJSONObject(0)
                .getString("phoneticSpelling")

        val definition = senses.getJSONObject(0).getJSONArray("definitions")
                .getString(0)

        return WordData(wordId, example, pronunciation, definition)
    }
}