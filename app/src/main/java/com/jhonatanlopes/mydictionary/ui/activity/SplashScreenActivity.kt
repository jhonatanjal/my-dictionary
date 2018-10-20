package com.jhonatanlopes.mydictionary.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.jhonatanlopes.mydictionary.R

class SplashScreenActivity : AppCompatActivity() {

    private val preferenceName = "user_preferences"
    private val alreadyShowedSplashScreen = "already_showed_splash_screen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        shouldShowSplashScreenResolver()
    }

    private fun shouldShowSplashScreenResolver() {
        val preferences = getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

        if (preferences.contains(alreadyShowedSplashScreen)) {
            showDictionary()
        } else {
            addPreferenceAlreadyShowedSplashScreen(preferences)
            showSplashScreen()
        }
    }

    private fun addPreferenceAlreadyShowedSplashScreen(preferences: SharedPreferences) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(alreadyShowedSplashScreen, true)
        editor.apply()
    }

    private fun showSplashScreen() {
        val handler = Handler()
        handler.postDelayed({ showDictionary() }, 2000)
    }

    private fun showDictionary() {
        val intent = Intent(this, DictionaryActivity::class.java)
        startActivity(intent)
        finish()
    }
}
