package edu.put.boardgamescollectorinf151797

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHandler = DBUtil(this,null, null, 1)

        val buttonGames: Button = findViewById(R.id.buttonGames)
        val buttonExtensions: Button = findViewById(R.id.buttonExtensions)
        val buttonSync: Button = findViewById(R.id.buttonSync)
        val buttonWipeData: Button = findViewById(R.id.buttonWipeData)

        buttonGames.setOnClickListener { view ->
            val gamesActivityIntent = Intent(applicationContext, GamesActivity::class.java)
            startActivity(gamesActivityIntent)
            finish()
        }
        buttonExtensions.setOnClickListener { view ->
            val extensionsActivityIntent = Intent(applicationContext, ExtensionsActivity::class.java)
            startActivity(extensionsActivityIntent)
            finish()
        }
        buttonSync.setOnClickListener { view ->
            val syncActivityIntent = Intent(applicationContext, SyncActivity::class.java)
            startActivity(syncActivityIntent)
            finish()
        }
        buttonWipeData.setOnClickListener { view ->
            File("/data/data/edu.put.boardgamescollectorinf151797/databases/bgc.db").delete()
            finish()
        }

        if (!checkIfDatabaseExists()) {
            val initialConfigActivityIntent = Intent(applicationContext, InitialConfigActivity::class.java)
            startActivity(initialConfigActivityIntent)
            finish()
        } else {
            val textProfileName: TextView = findViewById(R.id.textProfileName)
            textProfileName.text = dbHandler.getUsername()

            val textGamesCounter: TextView = findViewById(R.id.textGamesCounter)
            val numberGames = dbHandler.getNumberOfGames()
            textGamesCounter.text = "Liczba posiadanych gier: $numberGames"

            val textExtensionsCounter: TextView = findViewById(R.id.textExtensionsCounter)
            val numberExpansions = dbHandler.getNumberOfExpansions()
            textExtensionsCounter.text = "Liczba posiadanych dodatków: $numberExpansions"

            val textLastSyncDate: TextView = findViewById(R.id.textLastSyncDate)
            textLastSyncDate.text = "Ostatnia synchronizacja: " + dbHandler.getLastSyncDate()
        }


        
    }

    fun checkIfDatabaseExists(): Boolean {
        val dbFile = applicationContext.getDatabasePath("bgc.db")
        return dbFile.exists()
    }
}