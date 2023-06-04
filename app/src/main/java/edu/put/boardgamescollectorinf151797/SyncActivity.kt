package edu.put.boardgamescollectorinf151797

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class SyncActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        val dbHandler = DBUtil(this,null, null, 1)

        val buttonBackFromSync: Button = findViewById(R.id.buttonBackFromSync)
        buttonBackFromSync.setOnClickListener {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }

        val textSyncSyncDate: TextView = findViewById(R.id.textSyncSyncDate)
        textSyncSyncDate.text = "Ostatnia synchronizacja: " + dbHandler.getLastSyncDate()
    }
    fun updateSyncDate() {
        val dbHandler = DBUtil(this,null, null, 1)
        val textSyncSyncDate: TextView = findViewById(R.id.textSyncSyncDate)
        textSyncSyncDate.text = "Ostatnia synchronizacja: " + dbHandler.getLastSyncDate()
    }

}