package com.example.hw3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hw3.lightsout.LightsOutActivity
import com.example.hw3.pizzaparty.PizzaPartyActivity

const val TAG = "MainActivityLifeCycle" //getString(R.string.tag)
const val KEY_LIGHTS_OUT_WINS = "totalLightsOutWins"
const val KEY_PIZZA_HUNGER_LEVEL = "pizzaHungerLevel"
const val KEY_TOTAL_PIZZAS = "totalPizzas"
const val KEY_PIZZA_PARTY_SIZE = "pizzaPartySize"
const val KEY_TOGGLE_LIGHT_MODE = "toggleLightMode"

class MainActivity : AppCompatActivity() {

    private lateinit var lightsOutGameButton: Button
    private lateinit var pizzaPartyGameButton: Button
    private lateinit var pizzaPartyShareButton: Button
    private lateinit var lightsOutShareButton: Button
    private lateinit var lightsOutWinsView: TextView
    private lateinit var pizzaPartySizeView: TextView
    private lateinit var pizzaHungerLevelView: TextView
    private lateinit var totalPizzasView: TextView
    private var totalLightsOutWins = 0
    private var pizzaHungerLevel = "?"
    private var totalPizzas = 0
    private var pizzaPartySize = 0
    private var toggleLightMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        lightsOutGameButton = findViewById(R.id.lights_out_game_button)
        lightsOutGameButton.setOnClickListener(this::onLightsOutButtonGameClick)

        pizzaPartyGameButton = findViewById(R.id.pizza_party_game_button)
        pizzaPartyGameButton.setOnClickListener(this::onPizzaPartyButtonGameClick)


        lightsOutShareButton = findViewById(R.id.share_lights_out_button)
        lightsOutShareButton.setOnClickListener(this::onLightsOutShareButtonClick)

        pizzaPartyShareButton = findViewById(R.id.share_pizza_party_button)
        pizzaPartyShareButton.setOnClickListener(this::onPizzaPartyShareButtonClick)
        val extras = getIntent().getExtras()
        if (extras != null) {
            totalLightsOutWins = extras.getInt(KEY_LIGHTS_OUT_WINS)
            pizzaHungerLevel = extras.getString(KEY_PIZZA_HUNGER_LEVEL).toString()
            totalPizzas = extras.getInt(KEY_TOTAL_PIZZAS)
            pizzaPartySize = extras.getInt(KEY_PIZZA_PARTY_SIZE)
            toggleLightMode = extras.getBoolean(KEY_TOGGLE_LIGHT_MODE)
        }

        lightsOutWinsView = findViewById(R.id.lights_out_wins_view)
        pizzaPartySizeView = findViewById(R.id.party_size_view)
        pizzaHungerLevelView = findViewById(R.id.hunger_level_view)
        totalPizzasView = findViewById(R.id.total_pizzas_view)


        if (savedInstanceState != null) {
            // check if data was changed from intent
//            if (totalLightsOutWins == 0) {
//                totalLightsOutWins = savedInstanceState.getInt(LIGHTS_OUT_WINS_KEY)
//            }
//            if (totalPizzas == 0) {
//                pizzaPartySize = savedInstanceState.getInt(PIZZA_PARTY_SIZE_KEY)
//                totalPizzas = savedInstanceState.getInt(TOTAL_PIZZAS_KEY)
//            }
            toggleLightMode = savedInstanceState.getBoolean(KEY_TOGGLE_LIGHT_MODE)
        }


        updateMainView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PIZZA_PARTY_SIZE, pizzaPartySize)
        outState.putString(KEY_PIZZA_HUNGER_LEVEL, pizzaHungerLevel)
        outState.putInt(KEY_TOTAL_PIZZAS, totalPizzas)
        outState.putInt(KEY_LIGHTS_OUT_WINS, totalLightsOutWins)
        outState.putBoolean(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
    }

    // APP BAR
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
                toggleLightMode = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.action_dark_theme -> {
                toggleLightMode = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // toggle light/dark mode menu item
        val lightMode = menu.findItem(R.id.action_light_theme)
        val darkMode = menu.findItem(R.id.action_dark_theme)
        if (toggleLightMode) {
            lightMode.isVisible = false
            darkMode.isVisible = true
        }
        else {
            lightMode.isVisible = true
            darkMode.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun onLightsOutButtonGameClick(view: View) {
        val intent = Intent(this, LightsOutActivity::class.java)
        //intent.putExtra(LIGHTS_OUT_WINS_KEY, totalLightsOutWins)
        intent.putExtra(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
        startActivity(intent)
    }

    private fun onPizzaPartyButtonGameClick(view: View) {
        val intent = Intent(this, PizzaPartyActivity::class.java)
        intent.putExtra(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
        startActivity(intent)
    }

    private fun onLightsOutShareButtonClick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        // Supply extra that is plain text
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Lights Out Game")
        intent.putExtra(Intent.EXTRA_TEXT, "Lights Out Wins: $totalLightsOutWins")

        // If at least one app can handle intent, allow user to choose
        if (intent.resolveActivity(packageManager) != null) {
            val chooser = Intent.createChooser(intent, "Share Lights Out Game Statistics")
            startActivity(chooser)
        }
    }

    private fun onPizzaPartyShareButtonClick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        // Supply extra that is plain text
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Party Game")
        intent.putExtra(Intent.EXTRA_TEXT, "Party Size: $pizzaPartySize")
        intent.putExtra(Intent.EXTRA_TEXT, "Hunger Level: $pizzaHungerLevel")
        intent.putExtra(Intent.EXTRA_TEXT, "Total Pizzas: $totalPizzas")

        // If at least one app can handle intent, allow user to choose
        if (intent.resolveActivity(packageManager) != null) {
            val chooser = Intent.createChooser(intent, "Share Pizza Calculation")
            startActivity(chooser)
        }
    }

    private fun updateMainView() {
        // Lights Out Stats
        lightsOutWinsView.text = getString(R.string.lights_out_wins_title, totalLightsOutWins)
        // Pizza Party Stats
        pizzaPartySizeView.text = getString(R.string.pizza_party_size_title, pizzaPartySize)
        pizzaHungerLevelView.text = getString(R.string.pizza_hunger_level_title, pizzaHungerLevel)
        totalPizzasView.text = getString(R.string.total_pizzas_title, totalPizzas)
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