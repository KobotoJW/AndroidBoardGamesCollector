package edu.put.boardgamescollectorinf151797

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleCursorAdapter

class GamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        val ListViewGames: ListView = findViewById(R.id.ListViewGames)

        val buttonBackFromGames: Button = findViewById(R.id.buttonBackFromGames)
        buttonBackFromGames.setOnClickListener {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }
}