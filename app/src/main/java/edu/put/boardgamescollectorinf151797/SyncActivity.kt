package edu.put.boardgamescollectorinf151797

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SyncActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        val dbHandler = DBUtil(this,null, null, 1)
        val apiClient = MyApiClient(this)

        val editTextSyncProfile = findViewById<EditText>(R.id.editTextSyncProfile)


        val buttonBackFromSync: Button = findViewById(R.id.buttonBackFromSync)
        buttonBackFromSync.setOnClickListener {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }

        val buttonSyncSync: Button = findViewById(R.id.buttonSyncSync)
        buttonSyncSync.setOnClickListener {
            val username = editTextSyncProfile.text.toString()
            dbHandler.dropTables()
            dbHandler.addAccount(username)
            dbHandler.createTableGames()
            apiClient.downloadFile(this, username)
        }

        val textSyncSyncDate: TextView = findViewById(R.id.textSyncSyncDate)
        textSyncSyncDate.text = "Ostatnia synchronizacja: " + dbHandler.getLastSyncDate()
    }

}