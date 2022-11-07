package com.example.hw3.lightsout

import android.content.Intent
import com.example.hw3.lightsout.LightsOutGame
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.hw3.MainActivity
import com.example.hw3.R
import com.example.hw3.pizzaparty.PizzaPartyActivity

class LightsOutActivity : AppCompatActivity() {

    private lateinit var game: LightsOutGame
    private lateinit var lightGridLayout: GridLayout
    private var lightOnColor = 0
    private var lightOffColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights_out)

        lightGridLayout = findViewById(R.id.light_grid)

        // Add the same click handler to all grid buttons
        for (gridButton in lightGridLayout.children) {
            gridButton.setOnClickListener(this::onLightButtonClick)
        }

        lightOnColor = ContextCompat.getColor(this, R.color.yellow)
        lightOffColor = ContextCompat.getColor(this, R.color.black)

        game = LightsOutGame()
        startGame()
    }

    private fun startGame() {
        game.newGame()
        setButtonColors()
    }

    private fun onLightButtonClick(view: View) {

        // Find the button's row and col
        val buttonIndex = lightGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        game.selectLight(row, col)
        setButtonColors()

        // Congratulate the user if the game is over
        if (game.isGameOver) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonColors() {

        // Set all buttons' background color
        for (buttonIndex in 0 until lightGridLayout.childCount) {
            val gridButton = lightGridLayout.getChildAt(buttonIndex)

            // Find the button's row and col
            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isLightOn(row, col)) {
                gridButton.setBackgroundColor(lightOnColor)
            } else {
                gridButton.setBackgroundColor(lightOffColor)
            }
        }
    }

    fun onNewGameClick(view: View) {
        startGame()
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
//                val intent = Intent(this, LightsOutActivity::class.java)
//                startActivity(intent)
                true
            }
            R.id.action_pizza_party -> {
                val intent = Intent(this, PizzaPartyActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}