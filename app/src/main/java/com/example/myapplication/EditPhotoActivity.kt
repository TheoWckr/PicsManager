package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.helpers.auth
import com.google.firebase.storage.StorageReference

class EditPhotoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo)

        val photoId = intent.getStringExtra("photoId")
        val gsReference: StorageReference =
            com.example.myapplication.helpers.storage.getReferenceFromUrl("gs://picts-manager.appspot.com"+ photoId)
// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
            .load(gsReference)
            .into(findViewById(R.id.imageView_edit_photo) )

        val rotatePhoto = findViewById<ImageView>(R.id.imageView_edit_photo)
        rotatePhoto.rotation = 90F
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val btnPhotoId= findViewById<TextView>(R.id.namePhoto)
        btnPhotoId.text = gsReference.name.removeSuffix(".jpg")
    }



    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

}