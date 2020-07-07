package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with

import com.example.myapplication.adapters.ImageAdapter
import com.example.myapplication.helpers.MyAppGlideModule
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
    }
}