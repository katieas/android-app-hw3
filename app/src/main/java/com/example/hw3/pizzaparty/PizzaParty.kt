package com.example.hw3.pizzaparty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hw3.*
import com.example.hw3.lightsout.LightsOutActivity

private const val TAG = "PizzaPartyLifecycle"

class PizzaPartyActivity : AppCompatActivity() {

    private lateinit var calcButton: Button
    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var hungerLevel: PizzaCalculator.HungerLevel
    private var totalPizzas = 0
    private var pizzaPartySize = 0
    private var pizzaHungerLevel = ""
    private var totalLightsOutWins = 0
    private var toggleLightMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_party)


        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)
        calcButton = findViewById(R.id.calc_button)

        calcButton.setOnClickListener(this::calculateClick)

        //sets initial value to 0 rather than %d
        numPizzasTextView.text = resources.getString(R.string.total_pizzas, 0)

        val spinner = findViewById<Spinner>(R.id.hungry_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.hungry_array, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //val item = parent?.getItemAtPosition(position) as String
                //Toast.makeText(this@PizzaPartyActivity, item, Toast.LENGTH_SHORT).show()
                hungerLevel = when (parent?.getItemAtPosition(position) as String) {
                    "light" -> PizzaCalculator.HungerLevel.LIGHT
                    "medium" -> PizzaCalculator.HungerLevel.MEDIUM
                    else -> PizzaCalculator.HungerLevel.RAVENOUS
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val extras = getIntent().getExtras()
        if (extras != null) {
            totalLightsOutWins = extras.getInt(KEY_LIGHTS_OUT_WINS)
            pizzaHungerLevel = extras.getString(KEY_PIZZA_HUNGER_LEVEL).toString()
            totalPizzas = extras.getInt(KEY_TOTAL_PIZZAS)
            pizzaPartySize = extras.getInt(KEY_PIZZA_PARTY_SIZE)
            toggleLightMode = extras.getBoolean(KEY_TOGGLE_LIGHT_MODE)
        }

        if (savedInstanceState != null) {
            // we have a saeInstanceState object --> saved data!
            totalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS)
            displayTotal()
            toggleLightMode = savedInstanceState.getBoolean(KEY_TOGGLE_LIGHT_MODE)
        }

    }

    private fun displayTotal() {
        val totalText = getString(R.string.total_pizzas, totalPizzas)
        numPizzasTextView.text = totalText
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TOTAL_PIZZAS, totalPizzas)
        outState.putBoolean(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        totalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS)
//        displayTotal()
//    }

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

    private fun calculateClick(view: View) {
        // Get the text that was typed into the EditText
        val numAttendStr = numAttendEditText.text.toString()

        // Convert the text into an integer
        val numAttend = numAttendStr.toIntOrNull() ?: 0
        pizzaPartySize = numAttend

        pizzaHungerLevel = hungerLevel.toString().lowercase()
        // Get the number of pizzas needed
        val calc = PizzaCalculator(pizzaPartySize, hungerLevel)
        totalPizzas = calc.totalPizzas
        displayTotal()

        // Place totalPizzas into the string resource and display
//        val totalText = getString(R.string.total_pizzas, totalPizzas)
//        numPizzasTextView.setText(totalText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.action_menu -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_TOTAL_PIZZAS, totalPizzas)
                intent.putExtra(KEY_PIZZA_PARTY_SIZE, pizzaPartySize)
                intent.putExtra(KEY_PIZZA_HUNGER_LEVEL, pizzaHungerLevel)
                intent.putExtra(KEY_LIGHTS_OUT_WINS, totalLightsOutWins)
                intent.putExtra(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
                startActivity(intent)
                true
            }
            R.id.action_lights_out -> {
                val intent = Intent(this, LightsOutActivity::class.java)
                intent.putExtra(KEY_TOTAL_PIZZAS, totalPizzas)
                intent.putExtra(KEY_PIZZA_PARTY_SIZE, pizzaPartySize)
                intent.putExtra(KEY_PIZZA_HUNGER_LEVEL, pizzaHungerLevel)
                intent.putExtra(KEY_LIGHTS_OUT_WINS, totalLightsOutWins)
                intent.putExtra(KEY_TOGGLE_LIGHT_MODE, toggleLightMode)
                startActivity(intent)
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

        // hide pizza party
        menu.findItem(R.id.action_pizza_party).isVisible = false

        return super.onPrepareOptionsMenu(menu)
    }
}