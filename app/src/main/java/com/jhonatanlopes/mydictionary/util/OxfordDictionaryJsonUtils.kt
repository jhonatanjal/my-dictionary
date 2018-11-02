package com.jhonatanlopes.mydictionary.util

import com.jhonatanlopes.mydictionary.model.WordData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class OxfordDictionaryJsonUtils {

    fun getWordDataFromJson(json: String?): WordData? {
        if (json == null) return null

        val jsonObject = JSONObject(json)
        return buildWordData(jsonObject)
    }

    private fun buildWordData(jsonObject: JSONObject): WordData? {
        val wordId: String
        val example: String
        val pronunciation: String
        val definitions: List<String>

        try {
            val lexicalEntries: JSONArray = getLexicalEntriesFrom(jsonObject)
            val senses: JSONArray = getSensesFrom(lexicalEntries)

            wordId = getWordIdFrom(jsonObject)
            example = getExempleFrom(senses)
            pronunciation = getPronunciationFrom(lexicalEntries)
            definitions = getDefinitionsFrom(senses)

        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

        return WordData(wordId, example, pronunciation, definitions)
    }

    private fun getDefinitionsFrom(senses: JSONArray): List<String> {
        val definitions = mutableListOf<String>()
        addDefinitonsToList(senses, definitions)
        return definitions.toList()
    }

    private fun addDefinitonsToList(senses: JSONArray, definitions: MutableList<String>) {
        for (index in 0 until senses.length()) {
            val sense = senses.getJSONObject(index)
            if (sense.has("definitions")) {
                sense.getJSONArray("definitions").getString(0).let {
                    definitions.add(it)
                }
            }
        }
    }

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

    private fun getLexicalEntriesFrom(jsonObject: JSONObject) = jsonObject
            .getJSONArray("results")
            .getJSONObject(0).getJSONArray("lexicalEntries")

    private fun getWordIdFrom(jsonObject: JSONObject) = jsonObject.getJSONArray("results")
            .getJSONObject(0).getString("id")
}