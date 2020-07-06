package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.compressor.Compressor
import com.example.myapplication.service.AlbumService
import com.example.myapplication.service.AlbumService.getUserAlbums
import com.example.myapplication.uploader.Uploader
import com.google.android.material.textfield.TextInputEditText


class SavePhotoActivity : AppCompatActivity() {
    lateinit var photoPreview : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_photo)
        val path = intent.getStringExtra("imagePath")
        photoPreview = BitmapFactory.decodeFile(path)
        val imageView = findViewById<ImageView>(R.id.pictureResolve).apply {this.setImageBitmap(photoPreview)}

        var discardButton = findViewById<Button>(R.id.discard_button)

        discardButton.setOnClickListener {
            val anotherIntent = Intent(this, MainActivity::class.java)
            startActivity(anotherIntent)
        }
        // Get the Intent that started this activity and extract the string
        //val photo_save = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        //val imageView = findViewById<ImageView>(R.id.pictureResolve).apply {
//            text = message
//        }
        val spinner: Spinner = findViewById(R.id.album_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        val usersList = ArrayList<String>()

       // var receiver = HashMap<String,Any>()
        //getUserAlbums(receiver, usersList)

            for (album in AlbumService.albumList) {
            usersList.add(album.name )

            val  catAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList)
            spinner.adapter = catAdapter
        }




    }

    fun discard (){
        val anotherIntent = Intent(this, MainActivity::class.java)
        startActivity(anotherIntent)
    }

    fun save (v: View){
        val nameField = findViewById<TextInputEditText>(R.id.name_field)
        if (photoPreview !== null )
           Uploader.upload(Compressor.divideSize(photoPreview, 4), nameField.text.toString(), ::validation )
    }

    fun validation(){
        println("Validation")
        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
        val anotherIntent = Intent(this, MainActivity::class.java)
        startActivity(anotherIntent)
    }
}