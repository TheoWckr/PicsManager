package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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

    private lateinit var idAlbum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val button = findViewById<Button>(R.id.deleteAlbum)
        button.setOnClickListener(){
            deleteAlbum()
        }

        val albumName = intent.getStringExtra("albumName")
        idAlbum= AlbumService.getIdFromAlbumName(albumName)

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

    fun deleteAlbum(){
        AlbumService.albumDelete(idAlbum)
        Toast.makeText(this, "Album successfully deleted", Toast.LENGTH_SHORT).show()
        finish()
    }
}