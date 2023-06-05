package edu.put.boardgamescollectorinf151797

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView

class InitialConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_config)

        val dbHandler = DBUtil(this,null, null, 1)

        val editTextInitProfile = findViewById<EditText>(R.id.editTextInitProfile)

        val buttonInitialConfirm: Button = findViewById(R.id.buttonInitialConfirm)
        buttonInitialConfirm.setOnClickListener {
            dbHandler.dropTables()
            val username = editTextInitProfile.text.toString()
            dbHandler.addAccount(username)
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)

        }

    }

}


