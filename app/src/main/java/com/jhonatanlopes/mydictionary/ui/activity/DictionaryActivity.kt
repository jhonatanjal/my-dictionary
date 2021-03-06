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
import android.widget.Toast
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
            model.search(word).observe(this, Observer<WordData> { wordData ->
                showWordInfo(wordData)
            })
        }
    }

    private fun showWordInfo(wordData: WordData?) {
        if (wordData != null) {
            val wordInfoFragment = WordInfoFragment()

            Bundle().let {
                it.putParcelable("word-data", wordData)
                wordInfoFragment.arguments = it
            }
            addFragmentToContainer(wordInfoFragment)
        } else Toast.makeText(
                this,
                resources.getString(R.string.error_message),
                Toast.LENGTH_SHORT).show()
        hideProgressBar()
    }

    private fun addFragmentToContainer(wordInfoFragment: WordInfoFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, wordInfoFragment)
                .addToBackStack("card_word_info")
                .commit()
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

