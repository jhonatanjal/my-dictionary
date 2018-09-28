package com.jhonatanlopes.mydictionary.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import com.jhonatanlopes.mydictionary.R
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.util.NetworkUtils
import com.jhonatanlopes.mydictionary.util.OxfordDictionaryJsonUtils
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            requestFocus()
        }
        return true
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            val word = intent.getStringExtra(SearchManager.QUERY)
            RequestWordDataTask().execute(word)
        }
    }

    inner class RequestWordDataTask : AsyncTask<String, Unit, WordData?>() {
        override fun doInBackground(vararg args: String): WordData? {
            val result = NetworkUtils().getWordDefinition(args[0])

            return if (result != null)
                OxfordDictionaryJsonUtils().getWordDataFromJson(result)
            else
                null
        }

        override fun onPostExecute(result: WordData?) {
            if (result != null) {
                tv_hello_word.text = result.toString()
            } else
                tv_hello_word.text = resources.getString(R.string.error_message)
        }
    }
}

