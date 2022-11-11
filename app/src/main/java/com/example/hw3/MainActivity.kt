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
import com.example.hw3.lightsout.LightsOutActivity
import com.example.hw3.pizzaparty.PizzaPartyActivity

const val TAG = "MainActivityLifeCycle" //getString(R.string.tag)
const val KEY_LIGHTS_OUT_WINS = "totalLightsOutWins"
const val KEY_PIZZA_HUNGER_LEVEL = "pizzaHungerLevel"
const val KEY_TOTAL_PIZZAS = "totalPizzas"
const val KEY_PIZZA_PARTY_SIZE = "pizzaPartySize"

class MainActivity : AppCompatActivity() {

    private lateinit var lightsOutGameButton: Button
    private lateinit var pizzaPartyGameButton: Button
    private lateinit var pizzaPartyShareButton: Button
    private lateinit var lightsOutShareButton: Button
    private lateinit var lightsOutWinsView: TextView
    private lateinit var pizzaPartySizeView: TextView
    private lateinit var pizzaHungerLevelView: TextView
    private lateinit var totalPizzasView: TextView
    var totalLightsOutWins = 0
    var pizzaHungerLevel = "?"
    var totalPizzas = 0
    var pizzaPartySize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        var toggleLightTheme = true

        lightsOutGameButton = findViewById(R.id.lights_out_game_button)
        lightsOutGameButton.setOnClickListener(this::onLightsOutButtonGameClick)

        pizzaPartyGameButton = findViewById(R.id.pizza_party_game_button)
        pizzaPartyGameButton.setOnClickListener(this::onPizzaPartyButtonGameClick)


//        lightsOutShareButton = findViewById(R.id.share_lights_out_button)
//        lightsOutShareButton.setOnClickListener(this::onLightsOutShareButtonClick)

        pizzaPartyShareButton = findViewById(R.id.share_pizza_party_button)
        pizzaPartyShareButton.setOnClickListener(this::onPizzaPartyShareButtonClick)
        var extras = getIntent().getExtras()
        if (extras != null) {
            totalLightsOutWins = extras.getInt(KEY_LIGHTS_OUT_WINS)
            pizzaHungerLevel = extras.getString(KEY_PIZZA_HUNGER_LEVEL).toString()
            totalPizzas = extras.getInt(KEY_TOTAL_PIZZAS)
            pizzaPartySize = extras.getInt(KEY_PIZZA_PARTY_SIZE)
        }

        lightsOutWinsView = findViewById(R.id.lights_out_wins_view)
        pizzaPartySizeView = findViewById(R.id.party_size_view)
        pizzaHungerLevelView = findViewById(R.id.hunger_level_view)
        totalPizzasView = findViewById(R.id.total_pizzas_view)


//        if (savedInstanceState != null) {
//            // check if data was changed from intent
//            if (totalLightsOutWins == 0) {
//                totalLightsOutWins = savedInstanceState.getInt(LIGHTS_OUT_WINS_KEY)
//            }
//            if (totalPizzas == 0) {
//                pizzaPartySize = savedInstanceState.getInt(PIZZA_PARTY_SIZE_KEY)
//                totalPizzas = savedInstanceState.getInt(TOTAL_PIZZAS_KEY)
//            }
//        }

        updateMainView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PIZZA_PARTY_SIZE, pizzaPartySize)
        outState.putString(KEY_PIZZA_HUNGER_LEVEL, pizzaHungerLevel)
        outState.putInt(KEY_TOTAL_PIZZAS, totalPizzas)
        outState.putInt(KEY_LIGHTS_OUT_WINS, totalLightsOutWins)
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

    private fun onLightsOutButtonGameClick(view: View) {
        val intent = Intent(this, LightsOutActivity::class.java)
        //intent.putExtra(LIGHTS_OUT_WINS_KEY, totalLightsOutWins)
        startActivity(intent)
    }

    private fun onPizzaPartyButtonGameClick(view: View) {
        val intent = Intent(this, PizzaPartyActivity::class.java)
        startActivity(intent)
    }

    private fun onLightsOutShareButtonClick(view: View) {
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

    private fun onPizzaPartyShareButtonClick(view: View) {
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

    private fun updateMainView() {
        // Lights Out Stats
        lightsOutWinsView.text = lightsOutWinsView.text.toString() + totalLightsOutWins.toString()
        // Pizza Party Stats
        pizzaPartySizeView.text = pizzaPartySizeView.text.toString() + pizzaPartySize.toString()
        pizzaHungerLevelView.text = pizzaHungerLevelView.text.toString() + pizzaHungerLevel.toString()
        totalPizzasView.text = totalPizzasView.text.toString() + totalPizzas.toString()
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