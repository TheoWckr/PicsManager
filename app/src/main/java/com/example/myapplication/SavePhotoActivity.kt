package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.compressor.Compressor
import com.example.myapplication.helpers.MetaI18n
import com.example.myapplication.helpers.allI18n
import com.example.myapplication.service.AlbumService
import com.example.myapplication.uploader.Uploader
import com.google.android.material.textfield.TextInputEditText


class SavePhotoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var photoPreview : Bitmap
    var choosenAlbum = ""
    val i18n = allI18n.savePhoto

    class I18n(
   override val activityTitle :String ,
    val pictureName  :String ,
    val album  :String ,
    val save  :String ,
    val discard  :String,
   val errorAlbum :String,
   val errorNameEmpty :String,
   val errorNameAlreadyTaken :String
    ): MetaI18n

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_photo)
        //Getting image and populate view
        val path = intent.getStringExtra("imagePath")
        photoPreview = BitmapFactory.decodeFile(path)
        val imageView = findViewById<ImageView>(R.id.pictureResolve).apply {this.setImageBitmap(photoPreview)}
        imageView.rotation = 90F

        //Discard button
        var discardButton = findViewById<Button>(R.id.discard_button)
        discardButton.setOnClickListener {
            discard()
        }

        //Putting albums in the spinner
        val spinner: Spinner = findViewById(R.id.album_spinner)
        val albumStringList = ArrayList<String>()
             for (album in AlbumService.albumList) {
                 albumStringList.add(album.name)
             }
            val  catAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albumStringList)
            spinner.adapter = catAdapter
        spinner.onItemSelectedListener = this


    }

    fun discard (){
        val anotherIntent = Intent(this, MainActivity::class.java)
        startActivity(anotherIntent)
    }

    fun save (v: View){
        val nameField = findViewById<TextInputEditText>(R.id.name_field).text.toString()

        if(choosenAlbum.length === 0) {
            Toast.makeText(this, i18n.errorAlbum, Toast.LENGTH_SHORT).show()
            return
        }
        if(nameField.length === 0){
            Toast.makeText(this, i18n.errorNameEmpty, Toast.LENGTH_SHORT  ).show()
            return
        }
        if (photoPreview !== null )
          Uploader.upload(Compressor.divideSize(photoPreview,5), nameField, ::validation, AlbumService.getIdFromAlbumName(choosenAlbum) )
        }


    private fun validation(){
        println("Validation")
        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
        val anotherIntent = Intent(this, MainActivity::class.java)
        startActivity(anotherIntent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       choosenAlbum=  parent?.getItemAtPosition(position).toString()
    }
}