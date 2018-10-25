package com.jhonatanlopes.mydictionary.ui.activity

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.jhonatanlopes.mydictionary.R
import com.jhonatanlopes.mydictionary.model.WordData
import com.jhonatanlopes.mydictionary.ui.fragment.WordInfoFragment
import com.jhonatanlopes.mydictionary.viewmodel.WordDataViewModel
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : AppCompatActivity() {

    private lateinit var model: WordDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        model = ViewModelProviders.of(this).get(WordDataViewModel::class.java)
        model.getWordData().observe(this, Observer<WordData> { wordData ->
            showWordInfo(wordData)
        })
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
            showProgressBar()
            model.fetchWordData(word)
        }
    }

    private fun showWordInfo(wordData: WordData?) {
        val wordInfoFragment = WordInfoFragment()
        val bundle = Bundle()
        bundle.putParcelable("word-data", wordData)
        wordInfoFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, wordInfoFragment)
                .addToBackStack(null)
                .commit()
        hideProgressBar()
    }

    private fun hideProgressBar() {
        dictionary_progress_bar.visibility = View.GONE
        fragment_container.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        fragment_container.visibility = View.INVISIBLE
        dictionary_progress_bar.visibility = View.VISIBLE
    }
}

