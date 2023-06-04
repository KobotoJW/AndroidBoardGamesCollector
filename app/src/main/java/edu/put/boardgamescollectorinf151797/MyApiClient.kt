package edu.put.boardgamescollectorinf151797

import android.content.Context
import okhttp3.*
import java.io.IOException

class MyApiClient {
    private val client = OkHttpClient()

    fun makeApiRequest(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                // Handle response data
                print(responseData)
            }
        })
    }

    fun loadUserData(context: Context) {
        val dbHandler = DBUtil(context, null, null, 1)
        val userData = dbHandler.addAccount("koboto15")
        print(userData)
    }

    fun loadGamesTable(context: Context) {
        val dbHandler = DBUtil(context, null, null, 1)
        val gamesData = dbHandler.createTableGames()
        print(gamesData)
    }

    public fun resetDatabase(context: Context) {
        val dbHandler = DBUtil(context, null, null, 1)
        dbHandler.resetDatabase()
    }

}