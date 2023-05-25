package edu.put.boardgamescollectorinf151797

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGames: Button = findViewById(R.id.buttonGames)
        val buttonExtensions: Button = findViewById(R.id.buttonExtensions)
        val buttonSync: Button = findViewById(R.id.buttonSync)
        val buttonWipeData: Button = findViewById(R.id.buttonWipeData)

        buttonGames.setOnClickListener { view ->
            val gamesActivityIntent = Intent(applicationContext, GamesActivity::class.java)
            startActivity(gamesActivityIntent)
        }
        buttonExtensions.setOnClickListener { view ->
            val extensionsActivityIntent = Intent(applicationContext, ExtensionsActivity::class.java)
            startActivity(extensionsActivityIntent)
        }
        buttonSync.setOnClickListener { view ->
            val syncActivityIntent = Intent(applicationContext, SyncActivity::class.java)
            startActivity(syncActivityIntent)
        }
    }
}