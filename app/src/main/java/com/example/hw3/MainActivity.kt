package com.example.hw3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hw3.lightsout.LightsOutActivity
import com.example.hw3.pizzaparty.PizzaPartyActivity

//val SLICES_PER_PIZZA = 8 //getInteger(R.integer.slices_per_pizza)
val TAG = "MainActivity" //getString(R.string.tag)

class MainActivity : AppCompatActivity() {

    private lateinit var lightsOutGameButton: Button
    private lateinit var pizzaPartyGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightsOutGameButton = findViewById(R.id.lights_out_game_button)
        lightsOutGameButton.setOnClickListener{
            val intent = Intent(this, LightsOutActivity::class.java)
            startActivity(intent)
        }

        pizzaPartyGameButton = findViewById(R.id.pizza_party_game_button)
        pizzaPartyGameButton.setOnClickListener{
            val intent = Intent(this, PizzaPartyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.action_menu -> {
                true
            }
            R.id.action_lights_out -> {
                lightsOutGameButton.performClick()
                true
            }
            R.id.action_pizza_party -> {
                pizzaPartyGameButton.performClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}