package edu.put.boardgamescollectorinf151797

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import android.content.Context

class InitialConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_config)

        val buttonInitialConfirm: Button = findViewById(R.id.buttonInitialConfirm)
        buttonInitialConfirm.setOnClickListener {

            val apiClient = MyApiClient()
            val apiUrl = "https://boardgamegeek.com/xmlapi2/collection?username=koboto15"
            apiClient.makeApiRequest(apiUrl)
            print("API request sent")

            val MyApiClient = MyApiClient()
            MyApiClient.loadUserData(this)
            MyApiClient.loadGamesTable(this)

            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }



    }


}

class Item {

    var title:String? = null
    var releaseYear: Int? = null
    var bggId: Long = 0
    var thumbnail: String? = null
    var image: String? = null
    var description: String? = null

    constructor(title:String, releaseYear: Int,  bggId: Long, thumbnail: String, image: String, description: String){
        this.title = title
        this.releaseYear = releaseYear
        this.bggId = bggId
        this.thumbnail = thumbnail
        this.image = image
        this.description = description
    }

}


