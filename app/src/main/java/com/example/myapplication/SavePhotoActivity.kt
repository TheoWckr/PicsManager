package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.compressor.Compressor


class SavePhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_photo)
        val photoPreview: Bitmap

        val path = intent.getStringExtra("imagePath")
        photoPreview = BitmapFactory.decodeFile(path)
        val imageView = findViewById<ImageView>(R.id.pictureResolve).apply {this.setImageBitmap(Compressor.divideSize(photoPreview))}



        // Get the Intent that started this activity and extract the string
        //val photo_save = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        //val imageView = findViewById<ImageView>(R.id.pictureResolve).apply {
//            text = message
//        }
    }
}