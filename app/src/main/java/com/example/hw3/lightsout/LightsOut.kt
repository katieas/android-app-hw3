package com.example.hw3.lightsout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.size
import com.example.hw3.MainActivity
import com.example.hw3.R
import com.example.hw3.pizzaparty.PizzaPartyActivity
import kotlin.random.Random

const val TAG = "LifeCycle"
const val KEY_TOTAL_WINS = "totalWins"
const val GAME_STATE = "gameState"

class LightsOutActivity : AppCompatActivity() {

    private lateinit var game: LightsOutGame
    private lateinit var lightGridLayout: GridLayout
    private lateinit var newGameButton: Button
    private lateinit var randomMoveButton: Button
    private var lightOnColor = 0
    private var lightOffColor = 0
    private var totalWins = 0

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

        newGameButton = findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener(this::onNewGameClick)

        randomMoveButton = findViewById(R.id.random_move_button)
        randomMoveButton.setOnClickListener(this::onRandomMoveButtonClick)


        Log.d(TAG, "onCreate")

        if (savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getString(GAME_STATE)!!
            setButtonColors()
            totalWins = savedInstanceState.getInt(KEY_TOTAL_WINS)
            //displayTotal()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TOTAL_WINS, totalWins)
        outState.putString(GAME_STATE, game.state)
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        totalWins = savedInstanceState.getInt(KEY_TOTAL_WINS)
//    }

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
            totalWins++
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRandomMoveButtonClick(view: View) {
        // Randomize the button's row and col
        val row = Random.nextInt(0, lightGridLayout.childCount) / GRID_SIZE
        val col = Random.nextInt(0, lightGridLayout.childCount) % GRID_SIZE

        Log.d("ROW", row.toString())
        Log.d("COL", col.toString())

        game.selectLight(row, col)
        setButtonColors()

        // Congratulate the user if the game is over
        if (game.isGameOver) {
            totalWins++
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

    private fun onNewGameClick(view: View) {
        startGame()
    }


    // APP BAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.action_menu -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_TOTAL_WINS, totalWins)
                startActivity(intent)
                true
            }
            R.id.action_pizza_party -> {
                val intent = Intent(this, PizzaPartyActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_light_theme -> {
                true
            }
            R.id.action_dark_theme -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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