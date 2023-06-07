package edu.put.boardgamescollectorinf151797

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleCursorAdapter

class ExtensionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extensions)

        val ListViewGames: ListView = findViewById(R.id.ListViewExtensions)
        val dbUtil = DBUtil(this, null, null, 1)

        val cursor = dbUtil.getGames("boardgameexpansion")
        val from = arrayOf(DBUtil.COLUMN_TITLE, DBUtil.COLUMN_YEARPUBLISHED, DBUtil.COLUMN_RANK, DBUtil.COLUMN_THUMBNAIL)
        val to = intArrayOf(R.id.textViewItemName, R.id.textViewItemYear, R.id.textViewItemRank, R.id.imageViewItemThumb)
        val adapter = SimpleCursorAdapter(this, R.layout.single_item, cursor, from, to, 0)
        adapter.viewBinder = SimpleCursorAdapter.ViewBinder { view, cursor, columnIndex ->
            if (view.id == R.id.imageViewItemThumb) {
                val imageView = view as ImageView
                val thumbnail = cursor.getString(cursor.getColumnIndex(DBUtil.COLUMN_THUMBNAIL))
                val myThread = Thread(Runnable {
                    val bitmap = dbUtil.getBitmapFromURL(thumbnail)
                    runOnUiThread {
                        imageView.setImageBitmap(bitmap)
                    }
                })
                myThread.start()
                //imageView.setImageBitmap(bitmap)
                return@ViewBinder true
            }
            false
        }
        ListViewGames.adapter = adapter

        val buttonBackFromExtensions: Button = findViewById(R.id.buttonBackFromExtensions)
        buttonBackFromExtensions.setOnClickListener {
            val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
    }
}