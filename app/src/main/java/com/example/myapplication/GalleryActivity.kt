package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.adapters.ImageAdapter
import com.example.myapplication.service.AlbumService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class GalleryActivity: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val albumName = intent.getStringExtra("albumName")



        viewManager = LinearLayoutManager(this)
        viewAdapter = ImageAdapter(AlbumService.getAlbumFromAlbumName(albumName).photos)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_album).apply {
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

/*
// Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference
           val pictureRef=  storageReference.child("9iWK6LwOOmOYU3bKC4DQeU8LPCH3/Johnny")
// ImageView in your Activity
        val imageView = findViewById<ImageView>(R.id.imageView)

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
            .load("https://firebasestorage.googleapis.com/v0/b/picts-manager.appspot.com/o/9iWK6LwOOmOYU3bKC4DQeU8LPCH3%20%2FJohnny?alt=media&token=3ea74873-f04e-46fd-8b0e-6e1a8f76384a")
            .into(imageView)*/
    }

}