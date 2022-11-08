package com.example.hw3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3.lightsout.LightsOutActivity
import com.example.hw3.pizzaparty.PizzaPartyActivity

const val TAG = "MainActivityLifeCycle" //getString(R.string.tag)

class MainActivity : AppCompatActivity() {



    private lateinit var lightsOutGameButton: Button
    private lateinit var pizzaPartyGameButton: Button
    private lateinit var pizzaPartyShareButton: Button
    private lateinit var lightsOutShareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

//        if (savedInstanceState != null) {
//            //
//        }

        var toggleLightTheme = true

        lightsOutGameButton = findViewById(R.id.lights_out_game_button)
        lightsOutGameButton.setOnClickListener {
            val intent = Intent(this, LightsOutActivity::class.java)
            startActivity(intent)
        }

        pizzaPartyGameButton = findViewById(R.id.pizza_party_game_button)
        pizzaPartyGameButton.setOnClickListener {
            val intent = Intent(this, PizzaPartyActivity::class.java)
            startActivity(intent)
        }


        lightsOutShareButton = findViewById(R.id.share_lights_out_button)
        lightsOutShareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            // Supply extra that is plain text
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Lights Out Game")
            intent.putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/")

            // If at least one app can handle intent, allow user to choose
            if (intent.resolveActivity(packageManager) != null) {
                val chooser = Intent.createChooser(intent, "Share Lights Out Game Statistics")
                startActivity(chooser)
            }
        }

        pizzaPartyShareButton = findViewById(R.id.share_pizza_party_button)
        pizzaPartyShareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            // Supply extra that is plain text
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Party Game")
            intent.putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/")

            // If at least one app can handle intent, allow user to choose
            if (intent.resolveActivity(packageManager) != null) {
                val chooser = Intent.createChooser(intent, "Share Pizza Calculation")
                startActivity(chooser)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        //val item = menu.findItem((R.id.action_light_theme)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.action_lights_out -> {
                lightsOutGameButton.performClick()
                true
            }
            R.id.action_pizza_party -> {
                pizzaPartyGameButton.performClick()
                true
            }
            R.id.action_light_theme -> {
                setTheme(R.style.Theme_Menu)
                setTheme(R.style.Theme_LightsOut)
                setTheme(R.style.Theme_PizzaParty)
                true
            }
            R.id.action_dark_theme -> {
                setTheme(R.style.Theme_MenuDark)
                setTheme(R.style.Theme_LightsOutDark)
                setTheme(R.style.Theme_PizzaPartyDark)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
}