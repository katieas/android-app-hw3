package com.example.hw3.pizzaparty

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3.MainActivity
import com.example.hw3.R
import com.example.hw3.lightsout.LightsOutActivity

private const val TAG = "Lifecycle"
private const val KEY_TOTAL_PIZZAS = "totalPizzas"

class PizzaPartyActivity : AppCompatActivity() {

    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungryRadioGroup: RadioGroup
    private var totalPizzas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_party)
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)

        //sets initial value to 0 rather than %d
        numPizzasTextView.text = resources.getString(R.string.total_pizzas, 0)

        howHungryRadioGroup = findViewById(R.id.hungry_radio_group)

        Log.d(TAG, "onCreate")

        //Do we need to restore saveInstanceState
        if (savedInstanceState != null) {
            // we have a saeInstanceState object --> saved data!
            totalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS)
            displayTotal()
        }
    }

    private fun displayTotal() {
        val totalText = getString(R.string.total_pizzas, totalPizzas)
        numPizzasTextView.text = totalText
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(KEY_TOTAL_PIZZAS, totalPizzas)
        //outState.putInt("totalPizzas", 10)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        totalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS)
        displayTotal()
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

    fun calculateClick(view: View) {

        // Get the text that was typed into the EditText
        val numAttendStr = numAttendEditText.text.toString()

        // Convert the text into an integer
        val numAttend = numAttendStr.toIntOrNull() ?: 0

        // Get hunger level selection
        val hungerLevel = when (howHungryRadioGroup.getCheckedRadioButtonId()) {
            R.id.light_radio_button -> PizzaCalculator.HungerLevel.LIGHT
            R.id.medium_radio_button -> PizzaCalculator.HungerLevel.MEDIUM
            else -> PizzaCalculator.HungerLevel.RAVENOUS
        }

        // Get the number of pizzas needed
        val calc = PizzaCalculator(numAttend, hungerLevel)
        totalPizzas = calc.totalPizzas
        displayTotal()

        // Place totalPizzas into the string resource and display
        val totalText = getString(R.string.total_pizzas, totalPizzas)
        numPizzasTextView.setText(totalText)
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
                startActivity(intent)
                true
            }
            R.id.action_lights_out -> {
                val intent = Intent(this, LightsOutActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_pizza_party -> {
//                val intent = Intent(this, PizzaPartyActivity::class.java)
//                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}