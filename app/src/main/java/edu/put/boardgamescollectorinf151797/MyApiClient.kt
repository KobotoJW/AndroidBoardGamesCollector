package edu.put.boardgamescollectorinf151797

import android.content.Context
import kotlinx.coroutines.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileWriter
import java.net.MalformedURLException
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


class MyApiClient (context: Context) {
    fun downloadFile(context: Context, username: String) {
        val urlString = "https://boardgamegeek.com/xmlapi2/collection?username=$username"
        val xmlDirectory = "/data/data/edu.put.boardgamescollectorinf151797/databases"
        val fileName = "$xmlDirectory/games.xml"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(urlString)
                val reader = url.openStream().bufferedReader()
                val downloadFile = File(fileName).also { it.createNewFile() }
                val writer = FileWriter(downloadFile).buffered()
                var line: String?
                while (reader.readLine().also { line = it?.toString() ?: "" } != null) {
                    writer.write(line)
                }
                reader.close()
                writer.close()
                withContext(Dispatchers.Main) {
                    loadData(context, xmlDirectory)
                    println("Loaded")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    when (e) {
                        is MalformedURLException -> {
                            println("MalformedURLException")
                        }
                        else -> {
                            println("Exception $e")
                        }
                    }
                    val incompleteFile = File(fileName)
                    if (incompleteFile.exists()) {
                        incompleteFile.delete()
                        println("Deleted incomplete file")
                    }
                }
            }
        }
    }

    fun loadData (context: Context, xmlDirectory: String = "/data/data/edu.put.boardgamescollectorinf151797/databases") {
        val fileName = "games.xml"
        val inFile = File(xmlDirectory, fileName)

        if  (inFile.exists()) {
            val xmlDocBuil: DocumentBuilder? = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val xmlDoc: Document = xmlDocBuil!!.parse(inFile)
            xmlDoc.documentElement.normalize()
            val items: NodeList = xmlDoc.getElementsByTagName("item")

            for (i in 0..items.length - 1) {
                val itemNode: Node = items.item(i)
                if (itemNode.nodeType == Node.ELEMENT_NODE) {
                    val elem = itemNode as Element
                    val children = elem.childNodes

                    var currentCategory: String? = elem.getAttribute("subtype")
                    var currentTitle: String? = null
                    var currentYear: String? = null
                    var currentBGGid: Int? = elem.getAttribute("objectid").toInt()
                    var currentCollid: Int? = elem.getAttribute("collid").toInt()
                    var currentThumbnail: String? = null

                    for (j in 0..children.length-1) {
                        val node = children.item(j)
                        if (node is Element) {
                            when (node.nodeName) {
                                "name" -> {
                                    currentTitle = node.textContent
                                }
                                "yearpublished" -> {
                                    currentYear = node.textContent
                                }
                                "thumbnail" -> {
                                    currentThumbnail = node.textContent
                                }
                            }
                        }
                    }
                    if (currentCategory != null && currentTitle != null && currentYear != null && currentBGGid != null && currentCollid != null && currentThumbnail != null) {
                        val dbHandler = DBUtil(context, null, null, 1)
                        dbHandler.addGame(currentCategory, currentTitle, currentYear, currentBGGid, currentCollid, currentThumbnail)
                    }
                }
            }
        }
    }
}