package edu.put.boardgamescollectorinf151797

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SyncActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        val buttonBackFromSync: Button = findViewById(R.id.buttonBackFromSync)
        buttonBackFromSync.setOnClickListener {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }
}