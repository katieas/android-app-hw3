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
const val LIGHTS_OUT_WINS_KEY = "totalLightsOutWins"
const val PIZZA_HUNGER_LEVEL_KEY = "pizzaHungerLevel"
const val TOTAL_PIZZAS_KEY = "totalPizzas"
const val PIZZA_PARTY_SIZE_KEY = "pizzaPartySize"

class MainActivity : AppCompatActivity() {

    private lateinit var lightsOutGameButton: Button
    private lateinit var pizzaPartyGameButton: Button
    private lateinit var pizzaPartyShareButton: Button
    private lateinit var lightsOutShareButton: Button
    private lateinit var totalPizzasView: TextView
    var totalLightsOutWins = 0
    var pizzaHungerLevel = ""
    var totalPizzas = 0
    var pizzaPartySize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

//        if (savedInstanceState != null) {
//            //
//        }

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
            totalLightsOutWins = extras.getInt(LIGHTS_OUT_WINS_KEY)
            //pizzaHungerLevel = extras.getString(PIZZA_HUNGER_LEVEL_KEY)
            totalPizzas = extras.getInt(TOTAL_PIZZAS_KEY)
            pizzaPartySize = extras.getInt(PIZZA_PARTY_SIZE_KEY)
        }
        Log.d("TOTAL PIZZAS", totalPizzas.toString())

        totalPizzasView = findViewById(R.id.total_pizzas_view)
        updateMainView()
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
        totalPizzasView.text = totalPizzasView.text.toString() + totalPizzas.toString()
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