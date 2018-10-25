package com.jhonatanlopes.mydictionary.util

import com.jhonatanlopes.mydictionary.model.WordData
import org.json.JSONArray
import org.json.JSONObject

class OxfordDictionaryJsonUtils {

    fun getWordDataFromJson(json: String): WordData {
        val jsonObject = JSONObject(json)
        return buildWordData(jsonObject)
    }

    private fun buildWordData(jsonObject: JSONObject): WordData {
        val lexicalEntries: JSONArray = getLexicalEntriesFrom(jsonObject)
        val senses: JSONArray = getSensesFrom(lexicalEntries)

        val wordId: String = getWordIdFrom(jsonObject)
        val example: String = getExempleFrom(senses)
        val pronunciation: String = getPronunciationFrom(lexicalEntries)
        val definition: String = getDefinitionFrom(senses)

        return WordData(wordId, example, pronunciation, definition)
    }

    private fun getDefinitionFrom(senses: JSONArray) =
            senses.getJSONObject(0).getJSONArray("definitions")
                    .getString(0)

    private fun getPronunciationFrom(lexicalEntries: JSONArray): String {
        return lexicalEntries.getJSONObject(0)
                .getJSONArray("pronunciations").getJSONObject(0)
                .getString("phoneticSpelling")
    }

    private fun getExempleFrom(senses: JSONArray) =
            senses.getJSONObject(0).getJSONArray("examples")
                    .getJSONObject(0).getString("text")

    private fun getSensesFrom(lexicalEntries: JSONArray): JSONArray {
        return lexicalEntries.getJSONObject(0)
                .getJSONArray("entries").getJSONObject(0)
                .getJSONArray("senses")
    }

    private fun getLexicalEntriesFrom(jsonObject: JSONObject) = jsonObject.getJSONArray("results")
            .getJSONObject(0).getJSONArray("lexicalEntries")

    private fun getWordIdFrom(jsonObject: JSONObject) =
            jsonObject.getJSONArray("results").getJSONObject(0)
                    .getString("id")
}